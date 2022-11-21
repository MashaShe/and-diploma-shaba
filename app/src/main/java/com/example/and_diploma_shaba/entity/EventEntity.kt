package com.example.and_diploma_shaba.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.and_diploma_shaba.dto.Event

@Entity
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
   // val attachment: Attachment?,
    val author: String?,
    val authorAvatar: String?,
    val authorId: Long,
    val content: String?,
    val link: String?,
    val published: String?,
    val datetime : String?,
    val speakerIds: List<Long>?,
    @ColumnInfo(name = "event_type")
    val type: String?,
    val likedByMe : Boolean,
    val participatedByMe : Boolean,
    val downloadingProgress : Byte?,
    val speakerNames: List<String>? = null,
    val logined : Boolean = false,
    val belongsToMe : Boolean? = null

){
    fun toDto() = Event(
        //attachment,
        id,
        author,
        authorAvatar,
        authorId,
        content,
        link,
        published,
        datetime,
        speakerIds,
        type,
        likedByMe,
        participatedByMe,
        downloadingProgress,
        speakerNames,
        logined,
        belongsToMe

    )

    companion object {
        fun fromDto(dto: Event) =
            EventEntity(dto.id,
             //   dto.attachment,
                dto.author ,
                dto.authorAvatar ,
                dto.authorId ,
                dto.content,
                dto.link,
                dto.published,
                dto.datetime,
                dto.speakerIds,
                dto.type,
                dto.likedByMe,
                dto.participatedByMe,
                dto.downloadingProgress,
                dto.speakerNames,
                dto.logined,
                dto.belongsToMe
            )
    }
}

fun List<EventEntity>.toDto(): List<Event> = map(EventEntity::toDto)
fun List<Event>.toEntity(): List<EventEntity> = map(EventEntity::fromDto)