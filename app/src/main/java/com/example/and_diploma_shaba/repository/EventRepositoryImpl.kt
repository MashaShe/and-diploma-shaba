package com.example.and_diploma_shaba.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.and_diploma_shaba.dao.EventDao
import com.example.and_diploma_shaba.dto.Event
import com.example.and_diploma_shaba.entity.EventEntity

//class EventRepositoryImpl(
//    private val dao: EventDao,
//) : EventRepository {
//    override fun getAllEvents() = Transformations.map(dao.getAll()) { list ->
//        list.map {
//            Event(
//                it.eventId,
//                it.authorId,
//                it.authorName,
//                it.authorAvatar,
//                it.eventContent,
//                it.eventPublished,
//                it.eventDateTime,
//                it.eventType,
//                it.eventLink,
//               // it.eventSpeakerIds,
//                it.eventAttachmentURL,
//                it.eventAttachmentType)
//        }
//    }
//
//
//
//    override fun save(event: Event) {
//        dao.save(EventEntity.fromDto(event))
//    }
//
//    override fun removeById(id: Long) {
//        dao.removeById(id)
//    }
//
//    override fun getAllUserAuthorEventsById(id: Long) = Transformations.map(dao.getAllUserAuthorEventsById(id)) { list ->
//        list.map {
//            Event(
//                it.eventId,
//                it.authorId,
//                it.authorName,
//                it.authorAvatar,
//                it.eventContent,
//                it.eventPublished,
//                it.eventDateTime,
//                it.eventType,
//                it.eventLink,
//                //it.eventSpeakerIds,
//                it.eventAttachmentURL,
//                it.eventAttachmentType)
//        }
//    }
//}