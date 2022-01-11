package com.example.and_diploma_shaba.repository

import androidx.lifecycle.LiveData
import com.example.and_diploma_shaba.dto.Events
import com.example.and_diploma_shaba.dto.Post
import com.example.and_diploma_shaba.dto.User
import com.example.and_diploma_shaba.entity.UserEntity


interface UserRepository {
    fun userPassById(id: Long): String
    fun getAllUsers(): LiveData<List<User>>
    fun findByLogin(login: String?): UserEntity?
    fun addUser(user: User)

    //fun getAllUserEventsById(id: Long):LiveData<List<Events>>
   // fun getAllUserJobsById(id: Long):LiveData<List<Post>>

}