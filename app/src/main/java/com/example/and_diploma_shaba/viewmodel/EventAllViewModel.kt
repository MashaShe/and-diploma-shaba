package com.example.and_diploma_shaba.viewmodel

import androidx.lifecycle.*
import androidx.paging.*
import androidx.work.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import com.example.and_diploma_shaba.adapter.EventsRemoteMediator
import com.example.and_diploma_shaba.api.ApiService
import com.example.and_diploma_shaba.auth.AppAuth
import com.example.and_diploma_shaba.db.AppDb
import com.example.and_diploma_shaba.dto.Event
import com.example.and_diploma_shaba.model.*
import com.example.and_diploma_shaba.repository.AppEntities
import com.example.and_diploma_shaba.repository.AuthMethods
import javax.inject.Inject


@HiltViewModel
@ExperimentalCoroutinesApi
class EventAllViewModel @Inject constructor(
    var repository: AppEntities,
    var workManager: WorkManager,
    var auth: AppAuth,
    var api: ApiService,
    var repoNetwork: AuthMethods,
    var base: AppDb
) : ViewModel() {


    @ExperimentalPagingApi
    suspend fun events(): Flow<PagingData<Event>> {
        val allUsers = repository.getAllUsersFromDB()

        return auth.authStateFlow.flatMapLatest { value ->
            Pager(
                remoteMediator = EventsRemoteMediator(api, base, repoNetwork),
                config = PagingConfig(pageSize = 5, enablePlaceholders = false),
                pagingSourceFactory = { base.eventDao().getAll() }
            ).flow.map {
                it.map { event ->
                    val spekersID = event.speakerIds ?: mutableListOf() //maybe spekersID -> author? я же не работаю со спикерами

                    val eventDTO = event.toDto().copy(logined = value.id != 0L,
                    speakerNames = allUsers.filter { user ->
                        spekersID.contains(user.userId) }
                        .map { user -> user.userFirstName })
                    eventDTO
                    
                }

            }.cachedIn(viewModelScope).flowOn(Dispatchers.Default)
        }
    }





    private val _dataState = SingleLiveEvent<FeedModelState>()
    val dataState: SingleLiveEvent<FeedModelState>
        get() = _dataState

    fun loadEvents() = viewModelScope.launch {
        try {
            _dataState.value = FeedModelState(loading = true)
            repository.getAllEvents()
            _dataState.value = FeedModelState()
        } catch (e: Exception) {
            _dataState.value = FeedModelState(error = true)
        }
    }


}




