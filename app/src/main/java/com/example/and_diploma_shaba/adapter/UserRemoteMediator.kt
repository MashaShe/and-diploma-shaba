package com.example.and_diploma_shaba.adapter

import android.util.Log
import androidx.paging.*
import androidx.room.withTransaction
import com.example.and_diploma_shaba.api.ApiService
import com.example.and_diploma_shaba.db.AppDb
import com.example.and_diploma_shaba.entity.UserEntity
import com.example.and_diploma_shaba.entity.UserKeyEntry
import com.example.and_diploma_shaba.api.ApiError
import com.example.and_diploma_shaba.repository.AppNetState
import com.example.and_diploma_shaba.repository.AuthMethods

@ExperimentalPagingApi
class UserRemoteMediator(private val api: ApiService,
                         private val base: AppDb,
                         private val repoNetwork: AuthMethods
)
    : RemoteMediator<Int, UserEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
            ): MediatorResult {

        try {
            val connected = repoNetwork.checkConnection() == AppNetState.CONNECTION_ESTABLISHED

            if (connected) {
                val response = when (loadType) {
                    else -> {
                        base.userDao().deleteAll()
                        api.getAllUsers()
                    }
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
                            base.keyUserPaginationDao().insert(
                                listOf(
                                    UserKeyEntry(
                                        UserKeyEntry.Type.PREPEND,
                                        body.first().userId
                                    ),
                                    UserKeyEntry(
                                        UserKeyEntry.Type.APPEND,
                                        body.last().userId
                                    )
                                )
                            )
                            base.userDao().deleteAll()
                        }
                        LoadType.PREPEND -> {
                            base.keyUserPaginationDao().insert(
                                listOf(
                                    UserKeyEntry(
                                        UserKeyEntry.Type.PREPEND,
                                        body.first().userId
                                    ),
                                )
                            )

                        }
                        LoadType.APPEND -> {
                            base.keyUserPaginationDao().insert(
                                listOf(
                                    UserKeyEntry(
                                        UserKeyEntry.Type.APPEND,
                                        body.last().userId
                                    )
                                )
                            )

                        }
                    }

                    base.userDao().insert(body.map(UserEntity.Companion::fromDto))
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