package com.example.and_diploma_shaba.model

import com.example.and_diploma_shaba.dto.Post

interface FeedModel {
    val id: Long

  }

data class  PostModel(
    val post: Post
) : FeedModel {
    override  val id: Long = post.postId
}

data class  AdModel(override  val id: Long,
val picture : String
) : FeedModel
