package com.example.and_diploma_shaba.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.and_diploma_shaba.dao.PostDao
import com.example.and_diploma_shaba.dto.Post
import com.example.and_diploma_shaba.entity.PostEntity

//class PostRepositoryImpl(
//    private val dao: PostDao,
//) : PostRepository {
//    override fun getAllPosts() = Transformations.map(dao.getAll()) { list ->
//        list.map {
//            Post(
//                it.postId,
//                it.authorId,
//                it.postAuthor,
//                it.authorAvatar,
//                it.postContent,
//                it.postPublishedDate,
//                it.postLikes,
//                it.postLikedByMe,
//                it.rePosts,
//                it.postSeen,
//                it.video,
//                it.postAttachmentURL,
//                it.postAttachmentType)
//        }
//    }
//
//    override fun likeById(id: Long) {
//        dao.likeById(id)
//    }
//
//    override fun repostById(id: Long) {
//        dao.repostById(id)
//    }
//
//    override fun save(post: Post) {
//        dao.save(PostEntity.fromDto(post))
//    }
//
//    override fun removeById(id: Long) {
//        dao.removeById(id)
//    }
//
//    override fun getAllUserPostsById(id: Long) = Transformations.map(dao.getAllUserPostsById(id)) { list ->
//        list.map {
//            Post(
//                it.postId,
//                it.authorId,
//                it.postAuthor,
//                it.authorAvatar,
//                it.postContent,
//                it.postPublishedDate,
//                it.postLikes,
//                it.postLikedByMe,
//                it.rePosts,
//                it.postSeen,
//                it.video,
//                it.postAttachmentURL,
//                it.postAttachmentType)
//        }
//    }
//}