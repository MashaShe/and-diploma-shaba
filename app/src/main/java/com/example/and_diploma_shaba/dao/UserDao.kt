package com.example.and_diploma_shaba.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.and_diploma_shaba.entity.UserEntity

@Dao
interface UserDao {
    //TODO: в DAO описать работу со всеми атрибутами поста и дргиух классов

    //@Query("SELECT * FROM UserEntity WHERE userLogin = :login ORDER BY userId DESC")
    @Query("SELECT * FROM UserEntity WHERE userLogin = :login")
    fun findMe(login: String): UserEntity


//    @Query("SELECT userPass FROM UserEntity WHERE userId = :id")
//    fun userPassById(id: Long): String

//    @Query("SELECT userLogin FROM UserEntity WHERE userId = :id")
//    fun userLoginById(id: Long): String
//
//    @Query("SELECT userLogin FROM UserEntity WHERE userId = :login")
//    fun userIdByLogin(login: String): Long

//    @Query("SELECT * FROM UserEntity ORDER BY userId DESC")
//    fun getAllUsers(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM UserEntity")
    suspend fun getAllUsers(): List<UserEntity>

    @Query("SELECT * FROM UserEntity")
    fun getAll(): PagingSource<Int, UserEntity>

    @Query("SELECT * FROM UserEntity WHERE userLogin = :login")
    fun findByLogin(login: String?): UserEntity

//    @Insert
//    fun insert(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: List<UserEntity>) //suspend
//    fun save(user: UserEntity) =
//        if (user.userId == 0L) insert(user) else updateContentById(user.usertId, post.postContent)

    @Query("DELETE FROM UserEntity")
    suspend fun deleteAll()

    @Query("SELECT * FROM UserEntity WHERE userid = :id")
    fun getUser(id: Long): LiveData<List<UserEntity>>

}
