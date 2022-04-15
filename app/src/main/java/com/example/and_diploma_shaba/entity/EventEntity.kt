package com.example.and_diploma_shaba.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.and_diploma_shaba.dto.Event

@Entity
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    val eventId: Long = 0,
    val authorId: Long = 0,
    val authorName: String? = null,
    val authorAvatar: String? = null,
    val eventContent: String = "Выпускники Kotlin Developer (updated)",
    val eventPublished:String = "2021-08-17T16:46:58.887547Z",
    val eventDateTime: String = "2021-09-17T16:46:58.887547Z",
    val eventType: String = "ONLINE",
    val eventLink: String? = "https://netology.ru/programs/android-app",
   // val eventSpeakerIds: List <Long>? = null,
    val eventAttachmentURL: String? ="netology.jpg",
    val eventAttachmentType: String? = "IMAGE"

    //TODO: в DAO описать работу со всеми атрибутами поста и дргиух классов
) {
    fun toDto() = Event(
    eventId,
    authorId,
        authorName,
    authorAvatar,
    eventContent,
    eventPublished,
    eventDateTime,
    eventType,
    eventLink,
   // eventSpeakerIds,
    eventAttachmentURL,
    eventAttachmentType
    )

    companion object {
        fun fromDto(dto: Event) =
            EventEntity(
                dto.eventId,
                dto.authorId,
                dto.authorName,
                dto.authorAvatar,
                dto.eventContent,
                dto.eventPublished,
                dto.eventDateTime,
                dto.eventType,
                dto.eventLink,
                //dto.eventSpeakerIds,
                dto.eventAttachmentURL,
                dto.eventAttachmentType
            )

    }
}

fun List<EventEntity>.toDto(): List<Event> = map(EventEntity::toDto)
fun List<Event>.toEntity(): List<EventEntity> = map(EventEntity::fromDto)