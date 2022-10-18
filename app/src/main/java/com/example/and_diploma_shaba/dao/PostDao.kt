package com.example.and_diploma_shaba.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.and_diploma_shaba.dto.Post
import com.example.and_diploma_shaba.entity.PostEntity

@Dao
interface PostDao {
    //TODO: в DAO описать работу со всеми атрибутами поста и дргиух классов

    @Query("SELECT * FROM PostEntity ORDER BY postId DESC")
    fun getAll(): LiveData<List<PostEntity>>

    @Query("SELECT * FROM PostEntity WHERE authorId = :id ORDER BY postId DESC")
    fun getPosts(id : Long): PagingSource<Int,PostEntity>

    @Query("SELECT * FROM PostEntity ORDER BY postId DESC")
    fun getAllPosts(): PagingSource<Int, PostEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: PostEntity) //suspend

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<PostEntity>) //suspend

    @Query("UPDATE PostEntity SET postContent = :content WHERE postId = :id")
    fun updateContentById(id: Long, content: String)

//    fun save(post: PostEntity) =
//        if (post.postId == 0L) insert(post) else updateContentById(post.postId, post.postContent)

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

    @Query("DELETE FROM PostEntity")
    suspend fun deleteAll()

    @Query("SELECT * FROM PostEntity WHERE postid = :id LIMIT 1")
    suspend fun getPost(id: Long): PostEntity
}
