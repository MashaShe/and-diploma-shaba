package com.example.and_diploma_shaba.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.work.WorkManager
import com.example.and_diploma_shaba.auth.AppAuth
import com.example.and_diploma_shaba.db.AppDb
import com.example.and_diploma_shaba.dto.User
import com.example.and_diploma_shaba.model.FeedModelState
import com.example.and_diploma_shaba.model.SingleLiveEvent
import com.example.and_diploma_shaba.repository.AppEntities
import com.example.and_diploma_shaba.repository.UserRepository
//import com.example.and_diploma_shaba.repository.UserRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
@ExperimentalCoroutinesApi
class UsersViewModel @Inject constructor(var repository: AppEntities,
                                         var workManager: WorkManager,
                                         var auth: AppAuth
) : ViewModel() {
    val cachedusers = repository.udata.cachedIn(viewModelScope)

    private val _dataState = SingleLiveEvent<FeedModelState>()
    val dataState: SingleLiveEvent<FeedModelState>
        get() = _dataState


    fun loadUsers() = viewModelScope.launch {
        try {
            _dataState.value = FeedModelState(loading = true)
            repository.getAllUsers()
            _dataState.value = FeedModelState()
        } catch (e: Exception) {
            _dataState.value = FeedModelState(error = true)
        }
    }


}


//class UserViewModel(application: Application) : AndroidViewModel(application) {
//    private val repository: UserRepository = UserRepositoryImpl(
//        AppDb.getInstance(context = application).userDao()
//    )
//
//    private fun findByLogin(login: String?) = repository.findByLogin(login)
//
//    fun isAuthorized(enteredLogin: String, enteredPass: String): Boolean {
//        var ifAuthorized = false
//        val pass = findByLogin(enteredLogin)?.userPass
//        if (pass == enteredPass) {
//            ifAuthorized = true
//        }
//        return ifAuthorized
//    }
//
//    fun addUser(user: User) {
//        repository.addUser(user)
//    }
//
//}



//package com.example.and_diploma_shaba.viewmodel
//
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import com.example.and_diploma_shaba.db.AppDb
//import com.example.and_diploma_shaba.dto.Post
//import com.example.and_diploma_shaba.dto.User
//import com.example.and_diploma_shaba.entity.UserEntity
//import com.example.and_diploma_shaba.repository.UserRepository
//import com.example.and_diploma_shaba.repository.UserRepositoryImpl
//
//
//val AnonymousUser = User(
//    userId = 0L,
//    "anonymous",
//    "nopass",
//    null,
//    "Anonymous",
//    "Anonymous"
//)
//
//class UserViewModel (application: Application) : AndroidViewModel(application) {
//    private val repository: UserRepository = UserRepositoryImpl(
//        AppDb.getInstance(context = application).userDao()
//    )
//
//    val data = repository.getAllUsers()
//    val edited = MutableLiveData(AnonymousUser)
//
//    fun userPassById(id: Long) = repository.userPassById(id)
//    fun findByLogin(login: String?) = repository.findByLogin(login)
//
//    fun isAuthorized(id: Long, enteredPass: String): Boolean {
//        var ifAuthorized = false
//        val pass = userPassById(id)
//        if (pass == enteredPass) {
//            ifAuthorized = true
//        }
//        return ifAuthorized
//    }
//
//    fun addUser(user: User) {
//        edited.value?.let {
//            repository.addUser(it)
//        }
//        edited.value = AnonymousUser
//    }
//
//    fun authorization(login: String) = repository.findMe(login)
//    fun userIdByLogin(login: String) = repository.userIdByLogin(login)
//}
//
//
//
//
