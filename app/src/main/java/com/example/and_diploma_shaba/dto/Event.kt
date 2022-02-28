package com.example.and_diploma_shaba.dto

data class Event(
   // "postId": {{event_id}},
    val eventId: Long = 0,
    val authorId: Long = 0,
    val authorName: String? = null,
    val authorAvatar: String? = null,
    val eventContent: String = "Выпускники Kotlin Developer (updated)",
    val eventPublished:String = "2021-08-17T16:46:58.887547Z",
    val eventDateTime: String = "2021-09-17T16:46:58.887547Z",
    val eventType: String = "ONLINE",
    val eventLink: String? = "https://netology.ru/programs/android-app",
    //val eventSpeakerIds: List <Long>? = null,
    val eventAttachmentURL: String? ="netology.jpg",
    val eventAttachmentType: String? = "IMAGE"

)
