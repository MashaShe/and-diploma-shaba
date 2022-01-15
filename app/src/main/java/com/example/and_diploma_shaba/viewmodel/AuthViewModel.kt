package com.example.and_diploma_shaba.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.and_diploma_shaba.db.AppDb
import com.example.and_diploma_shaba.dto.User
import com.example.and_diploma_shaba.repository.UserRepository
import com.example.and_diploma_shaba.repository.UserRepositoryImpl



class AuthViewModel (application: Application) : AndroidViewModel(application) {
    val anonymousUser = User(
        userId = 0L,
        "anonymous",
        "nopass",
        null,
        "Anonymous",
        "Anonymous"//,
        // null,
        // userAuthorities = listOf("ROLE_ANONYMOUS")
    )
    private val repository: UserRepository = UserRepositoryImpl(
        AppDb.getInstance(context = application).userDao()
    )
    val edited = MutableLiveData(anonymousUser)
    val data = repository.getAllUsers()



    fun authorization(login: String): User {
     return  repository.findMe(login)
    }
    fun addUser(user: User) {
        edited.value?.let {
            repository.addUser(it)
        }
        edited.value = anonymousUser
    }
    fun userPassById(id: Long) = repository.userPassById(id)
    fun userLoginById(id: Long) = repository.userLoginById(id)
    fun userIdByLogin(login: String) = repository.userIdByLogin(login)


    fun isAuthorized(id: Long, enteredPass: String): Boolean {
        var ifAuthorized = false
        val pass = userPassById(id)
        if (pass == enteredPass) {
            ifAuthorized = true
        }
        return ifAuthorized
    }
}