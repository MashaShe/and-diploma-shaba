package com.example.and_diploma_shaba.dao;

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.and_diploma_shaba.entity.PostKeyEntry

@Dao
interface PostPaginationKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(listOf: List<PostKeyEntry>)

    @Query ("SELECT MIN(id) FROM PostKeyEntry")
    suspend fun min(): Long?

    @Query ("SELECT MAX(id) FROM PostKeyEntry")
    suspend fun max(): Long?
}