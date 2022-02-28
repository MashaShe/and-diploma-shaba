package com.example.and_diploma_shaba.repository
import com.example.and_diploma_shaba.dto.User

interface AuthRepository {
  //  fun isAuthorized(login: String, enteredPass: String): Boolean
    fun findMe(login: String): User
    fun addUser(user: User)
    fun userPassById(id: Long): String
    fun userIdByLogin(login: String): Long
   fun userLoginById(id: Long): String



}