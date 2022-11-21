package com.example.and_diploma_shaba.dto

import com.google.gson.annotations.SerializedName

data class Post (
    @SerializedName("id")
    val postId: Long,
    @SerializedName("authorId")
    val authorId: Long? = null,
    @SerializedName("author")
    val postAuthor: String,
    @SerializedName("authorAvatar")
    val authorAvatar: String? = null,
    @SerializedName("content")
    val postContent: String,
    @SerializedName("published")
    val postPublishedDate: String,
    val postLikes: Int = 0,
    @SerializedName("likedByMe")
    val postLikedByMe: Boolean = false,
    val rePosts: Int = 0,
    val postSeen: Int = postLikes + rePosts,
    val video: String? = null,
    val postAttachmentURL: String? ="netology.jpg",
    val postAttachmentType: String? = "IMAGE",
    val logined : Boolean = false,
    @SerializedName("ownedByMe")
    val ownedByMe: Boolean = false,
    var isLoading : Boolean = false




    )

val empty = Post(
    postId = 0,
    authorId = null,
    postAuthor = "",
    authorAvatar = null,
    postContent = "",
    postPublishedDate = "",
    postLikes = 0,
    postLikedByMe = false,
    rePosts = 0,
    //postSeen = postLikes + rePosts,
    video = null,
    postAttachmentURL = "netology.jpg",
    postAttachmentType = "IMAGE",
    logined = false,
    ownedByMe = false,
    isLoading  = false

)
