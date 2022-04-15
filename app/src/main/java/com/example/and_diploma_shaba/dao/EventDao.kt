package com.example.and_diploma_shaba.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.and_diploma_shaba.dto.Event
import com.example.and_diploma_shaba.dto.Post
import com.example.and_diploma_shaba.entity.EventEntity
import com.example.and_diploma_shaba.entity.PostEntity

@Dao
interface EventDao {

    @Query("SELECT * FROM EventEntity ORDER BY eventId DESC")
    fun getAll(): LiveData<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: EventEntity) //suspend

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<EventEntity>) //suspend

    @Query("UPDATE EventEntity SET eventContent = :content WHERE eventId = :id")
    fun updateContentById(id: Long, content: String)

    fun save(event: EventEntity) =
        if (event.eventId == 0L) insert(event) else updateContentById(event.eventId, event.eventContent)


    @Query("DELETE FROM EventEntity WHERE eventId = :id")
    fun removeById(id: Long)


    @Query("SELECT * FROM EventEntity WHERE authorId = :id ORDER BY eventDateTime DESC")
    fun getAllUserAuthorEventsById(id: Long):LiveData<List<Event>>

}
