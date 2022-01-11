package com.example.and_diploma_shaba.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.and_diploma_shaba.dto.Post
import com.example.and_diploma_shaba.entity.PostEntity

@Dao
interface PostDao {
    //TODO: в DAO описать работу со всеми атрибутами поста и дргиух классов

    @Query("SELECT * FROM PostEntity ORDER BY postId DESC")
    fun getAll(): LiveData<List<PostEntity>>

    @Insert
    fun insert(post: PostEntity)

    @Query("UPDATE PostEntity SET postContent = :content WHERE postId = :id")
    fun updateContentById(id: Long, content: String)

    fun save(post: PostEntity) =
        if (post.postId == 0L) insert(post) else updateContentById(post.postId, post.postContent)

    @Query("""
        UPDATE PostEntity SET
        postLikes = postLikes + CASE WHEN postLikedByMe THEN -1 ELSE 1 END,
        postLikedByMe = CASE WHEN postLikedByMe THEN 0 ELSE 1 END,
        postSeen = postSeen + 1
        WHERE postId = :id
        """)
    fun likeById(id: Long)

    @Query("DELETE FROM PostEntity WHERE postId = :id")
    fun removeById(id: Long)

    @Query("""
        UPDATE PostEntity SET
        rePosts = postLikes + 1,
        postSeen = postSeen + 1         
        WHERE postId = :id
        """)
    fun repostById(id: Long)

    @Query("SELECT * FROM PostEntity WHERE authorId = :id ORDER BY postPublishedDate DESC")
    fun getAllUserPostsById(id: Long):LiveData<List<Post>>


}
