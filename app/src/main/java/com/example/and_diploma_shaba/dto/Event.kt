package com.example.and_diploma_shaba.dto

import com.google.gson.annotations.SerializedName

data class Event(
    //val attachment: Attachment?,
    val id: Long,
    val author: String?,
    val authorAvatar: String?,
    val authorId: Long,
    val content: String?,
    val link: String?,
    val published: String?,
    val datetime : String?,
    val speakerIds: List<Long>?,
    val type: String?,
    val likedByMe : Boolean,
    val participatedByMe : Boolean,
    val downloadingProgress : Byte?,
    val speakerNames: List<String>? = null,
    val logined : Boolean = false,
    val belongsToMe : Boolean? = null,

    )


val emptyEvent = Event (
  //  attachment = null,
    id = 0,
    author = "",
    authorAvatar = "",
    authorId = 0,
    content = null,
    link = null,
    published = null,
    datetime = null,
    speakerIds = null,
    type = null,
    likedByMe = false,
    participatedByMe = false,
    downloadingProgress = null
)