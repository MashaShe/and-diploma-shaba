package com.example.and_diploma_shaba.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
//import com.example.and_diploma_shaba.dto.Attachment
import com.example.and_diploma_shaba.dto.Coords
import com.example.and_diploma_shaba.dto.Event
import com.example.and_diploma_shaba.dto.Post

@Entity
data class EventWorkEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
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
   // val attachment: Attachment?,
   // val mediaUri: String?,
   // val mediaType: String?,
    val likedByMe : Boolean,
    val participatedByMe : Boolean,
    val downloadingProgress : Byte?,
    val speakerNames: List<String>? = null,
    val logined : Boolean = false,
    val belongsToMe : Boolean? = null,
)
{
    fun toDto() = Event(//attachment,
        id, author, authorAvatar,authorId, content, link,
        published, datetime, speakerIds, type,likedByMe, participatedByMe,downloadingProgress,speakerNames,
        logined,belongsToMe)

    companion object {
        fun fromDto(dto: Event) =
            EventWorkEntity(dto.id,dto.author, dto.authorAvatar, dto.authorId, dto.content, dto.link,
                dto.published, dto.datetime,dto.speakerIds,dto.type,// dto.attachment,  "", ""
               dto.likedByMe,dto.participatedByMe,dto.downloadingProgress,dto.speakerNames,
                dto.logined,dto.belongsToMe)
    }
}