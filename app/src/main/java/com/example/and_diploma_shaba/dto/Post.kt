package com.example.and_diploma_shaba.dto

data class Post (
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
    val postAttachmentURL: String? ="netology.jpg",
    val postAttachmentType: String? = "IMAGE",
    val logined : Boolean = false


    )

