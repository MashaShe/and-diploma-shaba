package com.example.and_diploma_shaba.viewmodel


import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import com.example.and_diploma_shaba.auth.AppAuth
import com.example.and_diploma_shaba.dto.AuthState
import com.example.and_diploma_shaba.model.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(var auth: AppAuth) : ViewModel() {

    val authData: LiveData<AuthState> = auth
        .authStateFlow
        .asLiveData(Dispatchers.Default)

    val authenticated: Boolean
        get() = auth.authStateFlow.value.id != 0L


    private val _mutableSelectedItem = SingleLiveEvent<Boolean>()
    val logined: SingleLiveEvent<Boolean> get() = _mutableSelectedItem

    fun selectMyPage() {
        _mutableSelectedItem.value = true
    }

    fun markMyPageAlreadyOpened(){
        _mutableSelectedItem.value = false
    }
}





//import android.app.Application
//import androidx.lifecycle.*
//import com.example.and_diploma_shaba.db.AppDb
//import com.example.and_diploma_shaba.dto.User
//import com.example.and_diploma_shaba.repository.UserRepository
//import com.example.and_diploma_shaba.repository.UserRepositoryImpl


//
//class AuthViewModel (application: Application) : AndroidViewModel(application) {
//    val anonymousUser = User(
//        userId = 0L,
//        "anonymous",
//        "nopass",
//        null,
//        "Anonymous",
//        "Anonymous"//,
//        // null,
//        // userAuthorities = listOf("ROLE_ANONYMOUS")
//    )
//    private val repository: UserRepository = UserRepositoryImpl(
//        AppDb.getInstance(context = application).userDao()
//    )
//    val edited = MutableLiveData(anonymousUser)
//    val data = repository.getAllUsers()
//
//
//
//    fun authorization(login: String): User {
//     return  repository.findMe(login)
//    }
//    fun addUser(user: User) {
//        edited.value?.let {
//            repository.addUser(it)
//        }
//        edited.value = anonymousUser
//    }
//    fun userPassById(id: Long) = repository.userPassById(id)
//    fun userLoginById(id: Long) = repository.userLoginById(id)
//    fun userIdByLogin(login: String) = repository.userIdByLogin(login)
//
//
//    fun isAuthorized(id: Long, enteredPass: String): Boolean {
//        var ifAuthorized = false
//        val pass = userPassById(id)
//        if (pass == enteredPass) {
//            ifAuthorized = true
//        }
//        return ifAuthorized
//    }
//}