package com.example.and_diploma_shaba.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import com.example.and_diploma_shaba.auth.AppAuth
import com.example.and_diploma_shaba.dto.Coords
import com.example.and_diploma_shaba.dto.empty
import com.example.and_diploma_shaba.model.AttachmentType
import com.example.and_diploma_shaba.model.PreparedData
import com.example.and_diploma_shaba.repository.AppEntities
import com.example.and_diploma_shaba.repository.RecordOperation
import com.example.and_diploma_shaba.util.SingleLiveEvent
import com.example.and_diploma_shaba.util.SavePostWorker
import javax.inject.Inject


@HiltViewModel
@ExperimentalCoroutinesApi
class EditPostViewModel @Inject constructor(
    var repository: AppEntities,
    var workManager: WorkManager,
    var auth: AppAuth
) : ViewModel() {

    val edited = MutableLiveData(empty)

    private var _positionOfLoadingPost :Int = -1

    private val _loadingPost = SingleLiveEvent<Int>()
    val loadingPost: SingleLiveEvent<Int>
        get() = _loadingPost

    private val _attach = MutableLiveData<PreparedData?>(null)
    val attach: LiveData<PreparedData?>
        get() = _attach

    private var _coords = MutableLiveData<Coords?>(null)

    val coords: LiveData<Coords?>
        get() = _coords


    private val _postText = MutableLiveData<String?>()
    val postText: LiveData<String?>
        get() = _postText

    private var operation: RecordOperation = RecordOperation.NEW_RECORD


    fun save() {
        edited.value?.let { post ->
            viewModelScope.launch {
                try {
                    val type = _attach.value?.dataType?.let { it.toString() }
                    val uri = _attach.value?.uri?.let {
                       if (_positionOfLoadingPost!= -1) { loadingPost.value = _positionOfLoadingPost }
                        it.toString()
                    }

                    val id : Long =
                        repository.savePostForWorker(
                            post.copy()//,uri, type
                                    )


                    initWorkManager(id)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        edited.value = empty
        _attach.value = null
        _coords.value = null
        _postText.value = null
        _positionOfLoadingPost = -1
    }

    fun loadUser(id: Long, position: Int) {
        _positionOfLoadingPost = position
        viewModelScope.launch {
            operation = RecordOperation.CHANGE_RECORD
            val post = repository.getPostById(id).toDto()
            edited.value = post
            _coords.value = null // post.coords
            _postText.value = post.postContent

//            if (post.postAttachmentType != null) {
//                _attach.value = PreparedData(null,
//                    AttachmentType.valueOf(post.postAttachmentType))
//            } else {
                _attach.value = null
          //  }
        }
    }


    fun deletePost(id: Long) {
        operation = RecordOperation.DELETE_RECORD
        initWorkManager(id)
    }

    private fun initWorkManager(id: Long) {
        val data = workDataOf(
            SavePostWorker.postKey to arrayOf(operation.toString(),"$id")
        )

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = OneTimeWorkRequestBuilder<SavePostWorker>()
            .setInputData(data)
            .setConstraints(constraints)
            .build()
        workManager.enqueue(request)
    }


    fun preparePostCoords(newCoords: Coords?) {
        _coords.value = newCoords
    }

    fun removePostCoords() {
        _coords.value = null
    }

    fun getPostCoords(): Coords {
        return _coords.value ?: Coords(0F, 0F)
    }

    fun preparePostText(content: String) {
        edited.value = edited.value?.copy(postContent = content)
    }

    fun prepareTargetAttach(uri: Uri?, type: AttachmentType?) {
        _attach.value = PreparedData(uri, type)
    }

    fun prepareForNew() {
        operation = RecordOperation.NEW_RECORD
        edited.value = empty
        _attach.value = null
        _coords.value = null
        _postText.value = ""
    }

}




