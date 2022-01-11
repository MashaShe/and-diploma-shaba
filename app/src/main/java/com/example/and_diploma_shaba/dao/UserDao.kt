package com.example.and_diploma_shaba.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.and_diploma_shaba.dto.Events
import com.example.and_diploma_shaba.dto.Post
import com.example.and_diploma_shaba.dto.User
import com.example.and_diploma_shaba.entity.PostEntity
import com.example.and_diploma_shaba.entity.UserEntity

@Dao
interface UserDao {
    //TODO: в DAO описать работу со всеми атрибутами поста и дргиух классов


    @Query("SELECT userPass FROM UserEntity WHERE userId = :id")
    fun userPassById(id: Long): String

    @Query("SELECT * FROM UserEntity ORDER BY userId DESC")
    fun getAllUsers(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM UserEntity WHERE userLogin = :login")
    fun findByLogin(login: String?): UserEntity

    @Insert
    fun addUser(user: UserEntity)


}
