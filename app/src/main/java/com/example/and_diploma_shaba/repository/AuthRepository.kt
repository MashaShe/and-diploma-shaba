package com.example.and_diploma_shaba.repository
import androidx.lifecycle.LiveData
import androidx.room.Query
import com.example.and_diploma_shaba.dto.Events
import com.example.and_diploma_shaba.dto.Post
import com.example.and_diploma_shaba.dto.User
import com.example.and_diploma_shaba.entity.UserEntity

interface AuthRepository {
  //  fun isAuthorized(login: String, enteredPass: String): Boolean
    fun findMe(login: String): User
    fun addUser(user: User)
    fun userPassById(id: Long): String
    fun userIdByLogin(login: String): Long
   fun userLoginById(id: Long): String



}