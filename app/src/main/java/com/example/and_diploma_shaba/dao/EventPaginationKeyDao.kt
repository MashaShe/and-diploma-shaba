package com.example.and_diploma_shaba.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.and_diploma_shaba.entity.EventKeyEntry

@Dao
interface EventPaginationKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(listOf: List<EventKeyEntry>)

    @Query ("SELECT MIN(id) FROM EventKeyEntry")
    suspend fun min(): Long?

    @Query ("SELECT MAX(id) FROM EventKeyEntry")
    suspend fun max(): Long?
}