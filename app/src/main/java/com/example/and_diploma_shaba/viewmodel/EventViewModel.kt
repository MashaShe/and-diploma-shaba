package com.example.and_diploma_shaba.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import com.example.and_diploma_shaba.adapter.EventsRemoteMediator
import com.example.and_diploma_shaba.api.ApiService
import com.example.and_diploma_shaba.auth.AppAuth
import com.example.and_diploma_shaba.db.AppDb
import com.example.and_diploma_shaba.dto.Event
import com.example.and_diploma_shaba.entity.UserEntity
import com.example.and_diploma_shaba.model.FeedModelState
import com.example.and_diploma_shaba.model.SingleLiveEvent
import com.example.and_diploma_shaba.repository.AppEntities
import com.example.and_diploma_shaba.repository.AuthMethods
import javax.inject.Inject


@HiltViewModel
@ExperimentalCoroutinesApi
class EventViewModel @Inject constructor(var repository: AppEntities,
                                         var workManager: WorkManager,
                                         var auth: AppAuth,
                                         var api: ApiService,
                                         var repoNetwork: AuthMethods,
                                         var base: AppDb
) : ViewModel() {


    private val _dataState = SingleLiveEvent<FeedModelState>()
    val dataState: SingleLiveEvent<FeedModelState>
        get() = _dataState

    private val userId = MutableStateFlow<Long>(0)

    @ExperimentalPagingApi
    suspend fun events(): Flow<PagingData<Event>> {
        val allUsers : List<UserEntity> = repository.getAllUsersFromDB()

        return userId.flatMapLatest { value ->
            Pager(
                remoteMediator = EventsRemoteMediator(api, base, repoNetwork),
                config = PagingConfig(pageSize = 5, enablePlaceholders = false),
                pagingSourceFactory = { base.eventDao().getAll() }
            ).flow.map {
                it.filter{
                    it.authorId == auth.authStateFlow.value.id
                }.map { event ->
                    val spekersID = event.speakerIds ?: mutableListOf()

                    val eventDTO = event.toDto().copy(belongsToMe = true,
                        logined = true,
                        speakerNames = allUsers.filter { user ->
                            spekersID.contains(user.userId)
                        }
                            .map { user -> user.userFirstName })
                    eventDTO
                }

            }.cachedIn(viewModelScope).flowOn(Dispatchers.Default)
        }
    }


    fun loadEventsForUser(it: Long) {
        userId.value = it
    }

}




