package com.example.and_diploma_shaba.adapter

import android.util.Log
import androidx.paging.*
import androidx.room.withTransaction
import com.example.and_diploma_shaba.api.ApiError
import com.example.and_diploma_shaba.api.ApiService
import com.example.and_diploma_shaba.db.AppDb
import com.example.and_diploma_shaba.entity.PostEntity
import com.example.and_diploma_shaba.entity.PostKeyEntry
import com.example.and_diploma_shaba.repository.AppNetState
import com.example.and_diploma_shaba.repository.AuthMethods


@ExperimentalPagingApi
class PostRemoteMediator(private val api: ApiService,
                         private val base: AppDb,
                         private val repoNetwork: AuthMethods,

)
    : RemoteMediator<Int, PostEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PostEntity>
            ): MediatorResult {
        try {
            val connected = repoNetwork.checkConnection() == AppNetState.CONNECTION_ESTABLISHED

            if (connected) {
                val response = when (loadType) {
                    else -> {
                        base.postDao().deleteAll()
                        //api.getLatestPosts(state.config.pageSize)
                        api.getAllPosts()
                    }
                    /*   LoadType.REFRESH -> {
                      base.postDao().deleteAll()
                      api.getLatestPosts(state.config.pageSize)
                  }
                  LoadType.APPEND -> {
                      val id = base.keyWorkDao().min() ?: return MediatorResult.Success(false)
                     // api.getBeforePosts(id, state.config.pageSize)
                  }
                  LoadType.PREPEND -> {
                      val id = base.keyWorkDao().max() ?: return MediatorResult.Success(false)
                    //  api.getAfterPosts(id, state.config.pageSize)
                  }*/
                }

                if (!response.isSuccessful) {
                    throw ApiError(response.code(), response.message())
                }

                val body = response.body() ?: throw  ApiError(
                    response.code(),
                    response.message()
                )

                if (body.isEmpty()) {
                    return MediatorResult.Success(true)

                }

                base.withTransaction {
                    when (loadType) {
                        LoadType.REFRESH -> {
                            base.keyPostPaginationDao().insert(
                                listOf(
                                    PostKeyEntry(
                                        PostKeyEntry.Type.PREPEND,
                                        body.first().postId
                                    ),
                                    PostKeyEntry(
                                        PostKeyEntry.Type.APPEND,
                                        body.last().postId
                                    )
                                )
                            )
                            base.postDao().deleteAll()
                        }
                        LoadType.PREPEND -> {
                            base.keyPostPaginationDao().insert(
                                listOf(
                                    PostKeyEntry(
                                        PostKeyEntry.Type.PREPEND,
                                        body.first().postId
                                    ),
                                )
                            )

                        }
                        LoadType.APPEND -> {
                            base.keyPostPaginationDao().insert(
                                listOf(
                                    PostKeyEntry(
                                        PostKeyEntry.Type.APPEND,
                                        body.last().postId
                                    )
                                )
                            )

                        }
                    }

                    base.postDao().insert(body.map(PostEntity.Companion::fromDto))
                }



                return MediatorResult.Success(true)
            } else {
                return MediatorResult.Success(true)
            }
        } catch (e: Exception){
           return MediatorResult.Error(e)
        }
    }
}