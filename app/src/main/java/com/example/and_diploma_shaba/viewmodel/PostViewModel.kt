package com.example.and_diploma_shaba.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import androidx.work.WorkManager
import com.example.and_diploma_shaba.auth.AppAuth
import com.example.and_diploma_shaba.db.AppDb
import com.example.and_diploma_shaba.dto.Post
import com.example.and_diploma_shaba.model.FeedModel
import com.example.and_diploma_shaba.model.FeedModelState
import com.example.and_diploma_shaba.model.PostModel
import com.example.and_diploma_shaba.model.SingleLiveEvent
import com.example.and_diploma_shaba.repository.AppEntities
import com.example.and_diploma_shaba.repository.PostRepository
//import com.example.and_diploma_shaba.repository.PostRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
@ExperimentalCoroutinesApi
class PostViewModel @Inject constructor(var repository: AppEntities,
                                        var workManager: WorkManager,
                                        var auth: AppAuth
) : ViewModel() {

    val cachedposts = repository.pdata.cachedIn(viewModelScope)

    private val userId = MutableStateFlow<Long>(0)

    val feedModels = userId.flatMapLatest { id ->
        cachedposts.map { pagingData ->
            if (userId.value == 0L) {
                pagingData.map { post ->
                    PostModel(post = post.copy(logined = false)) as FeedModel
                }
            } else {
                pagingData.filter { userId.value == it.authorId }
                    .map { post ->
                        if (userId.value == auth.authStateFlow.value.id) {
                            PostModel(post = post.copy(logined = true)) as FeedModel
                        } else {
                            PostModel(post = post) as FeedModel
                        }
                    }

            }
        }
    }



    private val _dataState = SingleLiveEvent<FeedModelState>()
    val dataState: SingleLiveEvent<FeedModelState>
        get() = _dataState


    fun getWallById(id: Long) = viewModelScope.launch {
        userId.value = id
        try {
            _dataState.value = FeedModelState(loading = true)
            repository.getPostsById(id)
            _dataState.value = FeedModelState()

        } catch (e: Exception) {
            _dataState.value = FeedModelState(error = true)
        }
    }


    fun refreshPosts() = viewModelScope.launch {
        getWallById(userId.value)
    }
}

//
//val emptyPost = Post(
//    postId = 0L,
//    authorId = 0L,
//    postAuthor = "",
//    postContent = "",
//    postPublishedDate = "",
//    postLikes = 0,
//    postLikedByMe = false,
//    rePosts = 0
//)
//class PostViewModel (application: Application) : AndroidViewModel(application) {
//    //private val repository: PostRepository = PostRepositoryInMemoryImpl()
//    private val repository: PostRepository = PostRepositoryImpl(
//        AppDb.getInstance(context = application).postDao()
//    )
//
//    val data = repository.getAll()
//    val edited = MutableLiveData(emptyPost)
//
//    fun likeById(id: Long) = repository.likeById(id)
//    fun repostById(id:Long) = repository.repostById(id)
//    fun removeById(id:Long) = repository.removeById(id)
//    fun getAllUserPostsById(authorId: Long) = repository.getAllUserPostsById(authorId)
//
//
//    fun save() {
//        edited.value?.let {
//            repository.save(it)
//        }
//        edited.value = emptyPost
//    }
//
//    fun cancel() {
//        edited.value = emptyPost
//    }
//
//    fun edit(post: Post) {
//        edited.value = post
//    }
//
//    fun changeContent(content: String) {
//        val text = content.trim()
//        if (edited.value?.postContent == text) {
//            return
//        }
//        edited.value = edited.value?.copy(postContent = text)
//    }
//
////    fun setVideoVisibility(post:Post): Int {
////        var returning =  View.GONE
////        if (!post.video.isNullOrEmpty()){
////          var returning = View.VISIBLE
////        }
////        return returning
////    }
//
//}

fun kiloLogic(num: Int): String {
    var returning = num.toString()

    when (true){
        num in 1000..1099 -> {returning = "1K"}
        num in 1100..9999 -> {returning =  "${(num.toDouble()/1000).toString().take(3)}K"}
        num in 10000..999999 -> {returning =  "${(num.toDouble()/1000).toString().take(2)}K"}
        num > 999999 -> {returning =  "${(num.toDouble()/1000000).toString().take(3)}M"}
    }

    return returning
}


