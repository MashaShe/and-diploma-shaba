package com.example.and_diploma_shaba.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.and_diploma_shaba.entity.UserEntity;

@Dao
interface AuthDao {

    //@Query("SELECT * FROM UserEntity WHERE userLogin = :login ORDER BY userId DESC")
    @Query("SELECT * FROM UserEntity WHERE userLogin = :login")
    fun findMe(login: String): UserEntity

    @Insert
    fun insert(user: UserEntity)

//    @Query("SELECT userPass FROM UserEntity WHERE userId = :id")
//    fun userPassById(id: Long): String

    @Query("SELECT userLogin FROM UserEntity WHERE userId = :id")
    fun userLoginById(id: Long): String

    @Query("SELECT userLogin FROM UserEntity WHERE userId = :login")
    fun userIdByLogin(login: String): Long
//
//    @Query("SELECT userPass FROM UserEntity WHERE userId = :id")
//    fun userPassById(id: Long): String
//
//    @Query("SELECT * FROM UserEntity ORDER BY userId DESC")
//    fun getAllUsers(): LiveData<List<UserEntity>>
//
//    @Query("SELECT * FROM UserEntity WHERE userLogin = :login")
//    fun findByLogin(login: String?): UserEntity
//
//    @Insert
//    fun addUser(user: UserEntity)


}