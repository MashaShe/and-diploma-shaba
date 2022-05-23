package com.example.and_diploma_shaba.viewmodel

//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.MutableLiveData
//import com.example.and_diploma_shaba.db.AppDb
//import com.example.and_diploma_shaba.dto.Event
////import com.example.and_diploma_shaba.repository.EventRepository
////import com.example.and_diploma_shaba.repository.EventRepositoryImpl
//
//
//
//val emptyEvent = Event(
//    eventId = 0,
//    authorId = 0,
//    authorAvatar = null,
//    eventContent = "",
//    eventPublished = "2021-08-17T16:46:58.887547Z",
//    eventDateTime = "2021-09-17T16:46:58.887547Z",
//
//    )
//class EventViewModel (application: Application) : AndroidViewModel(application) {
//    private val repository: EventRepository = EventRepositoryImpl(
//        AppDb.getInstance(context = application).eventDao()
//    )
//
//    val data = repository.getAll()
//    val edited = MutableLiveData(emptyEvent)
//    fun removeById(id:Long) = repository.removeById(id)
//    fun getAllUserAuthorEventsById(authorId: Long) = repository.getAllUserAuthorEventsById(authorId)
//
//
//    fun save() {
//        edited.value?.let {
//            repository.save(it)
//        }
//        edited.value = emptyEvent
//    }
//
//    fun cancel() {
//        edited.value = emptyEvent
//    }
//
//    fun edit(event: Event) {
//        edited.value = event
//    }
//
//    fun changeContent(content: String) {
//        val text = content.trim()
//        if (edited.value?.eventContent == text) {
//            return
//        }
//        edited.value = edited.value?.copy(eventContent = text)
//    }
//}
//


