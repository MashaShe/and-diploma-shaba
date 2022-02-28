package com.example.and_diploma_shaba.repository

import androidx.lifecycle.LiveData
import com.example.and_diploma_shaba.dto.Event


interface EventRepository {
    fun getAll(): LiveData<List<Event>>
    fun removeById(id: Long)
    fun save(event: Event)
    fun getAllUserAuthorEventsById(id: Long):LiveData<List<Event>>


}