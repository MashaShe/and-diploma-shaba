package com.example.and_diploma_shaba.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
//import com.example.and_diploma_shaba.dto.Attachment
//import com.example.and_diploma_shaba.dto.Coords
import com.example.and_diploma_shaba.dto.Post
@Entity
data class PostWorkEntity(
    @PrimaryKey(autoGenerate = true)
    val postId: Long,
    val authorId: Long? = null,
    val postAuthor: String,
    val authorAvatar: String? = null,
    val postContent: String,
    val postPublishedDate: String,
    val postLikes: Int = 0,
    val postLikedByMe: Boolean = false,
    val rePosts: Int = 0,
    val postSeen: Int = postLikes + rePosts,
    val video: String? = null,
    val postAttachmentURL: String? = null,
    val postAttachmentType: String? = null,
    val logined: Boolean = false,
    val ownedByMe: Boolean = false

    //TODO: в DAO описать работу со всеми атрибутами поста и дргиух классов
) {
    fun toDto() = Post(
        postId,
        authorId,
        postAuthor,
        authorAvatar,
        postContent,
        postPublishedDate,
        postLikes,
        postLikedByMe,
        rePosts,
        postSeen,
        video,
        postAttachmentURL,
        postAttachmentType,
        logined,
        ownedByMe

    )

    companion object {
        fun fromDto(dto: Post) =
            PostWorkEntity(
                dto.postId,
                dto.authorId,
                dto.postAuthor,
                dto.authorAvatar,
                dto.postContent,
                dto.postPublishedDate,
                dto.postLikes,
                dto.postLikedByMe,
                dto.rePosts,
                dto.postSeen,
                dto.video,
                dto.postAttachmentURL,
                dto.postAttachmentType,
                dto.logined,
                dto.ownedByMe)

    }
}