package com.example.and_diploma_shaba.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.and_diploma_shaba.dto.User
import com.example.and_diploma_shaba.entity.UserEntity
import kotlinx.coroutines.flow.Flow


//interface UserRepository {
//    val udata: Flow<PagingData<User>>
//    fun userPassById(id: Long): String
//    fun getAllUsers(): LiveData<List<User>>
//    fun findByLogin(login: String?): UserEntity?
//    fun addUser(user: User)
//    fun findMe(login: String): User
//    fun userLoginById(id: Long): String
//    fun getUserById(id: Long): LiveData<List<UserEntity>>
//    suspend fun getAllUsersFromDB(): List<UserEntity>
//    //fun getAllUserEventsById(id: Long):LiveData<List<Events>>
//   // fun getAllUserJobsById(id: Long):LiveData<List<Post>>
//
//}