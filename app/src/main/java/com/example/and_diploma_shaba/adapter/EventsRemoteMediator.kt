package com.example.and_diploma_shaba.adapter

import android.util.Log
import androidx.paging.*
import com.example.and_diploma_shaba.api.ApiService
import com.example.and_diploma_shaba.db.AppDb
import com.example.and_diploma_shaba.entity.EventEntity
import com.example.and_diploma_shaba.api.ApiError
import com.example.and_diploma_shaba.repository.AppNetState
import com.example.and_diploma_shaba.repository.AuthMethods


@ExperimentalPagingApi
class EventsRemoteMediator(private val api: ApiService,
                           private val base: AppDb,
                           private val repoNetwork: AuthMethods
)
    : RemoteMediator<Int, EventEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EventEntity>
            ): MediatorResult {
        try {
            val connected = repoNetwork.checkConnection() == AppNetState.CONNECTION_ESTABLISHED

            if (connected) {
            val response = when (loadType){
                else -> {
                    api.getAllEvents()
                }
            }

           if (! response.isSuccessful){
               throw ApiError(response.code(), response.message())
           }

            val body = response.body() ?: throw  ApiError(
                response.code(),
                response.message()
            )

            if (body.isEmpty()){
                return MediatorResult.Success(true)
            }

            base.eventDao().insert(body.map(EventEntity.Companion::fromDto))

            return  MediatorResult.Success(true)
            } else {
                return MediatorResult.Success(true)
            }
        } catch (e: Exception){
            Log.e("exc", "events remote mediator Exception")
           return MediatorResult.Error(e)
        }
    }
}