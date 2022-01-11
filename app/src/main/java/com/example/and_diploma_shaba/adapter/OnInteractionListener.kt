package com.example.and_diploma_shaba.adapter

import com.example.and_diploma_shaba.dto.Post
import com.example.and_diploma_shaba.dto.User

interface OnInteractionListener {
    fun onLike(post: Post) {}
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
    fun onRepost(post: Post) {}
    fun onShare(post: Post) {}
    fun onVideo(post: Post) {}
    fun onLogin(user: User) {}
    fun onRegister(user: User) {}

}