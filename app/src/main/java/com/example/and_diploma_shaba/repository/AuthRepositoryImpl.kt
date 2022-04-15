package com.example.and_diploma_shaba.repository

import android.content.Context
import androidx.lifecycle.Transformations
import androidx.paging.ExperimentalPagingApi
import androidx.paging.*
import com.example.and_diploma_shaba.adapter.UserRemoteMediator
import com.example.and_diploma_shaba.api.ApiError
import com.example.and_diploma_shaba.api.ApiService
import com.example.and_diploma_shaba.dao.AuthDao
import com.example.and_diploma_shaba.dao.UserDao
import com.example.and_diploma_shaba.db.AppDb
import com.example.and_diploma_shaba.dto.Event
import com.example.and_diploma_shaba.dto.Post
import com.example.and_diploma_shaba.dto.User
import com.example.and_diploma_shaba.entity.EventEntity
import com.example.and_diploma_shaba.entity.PostEntity
import com.example.and_diploma_shaba.entity.UserEntity
import com.example.and_diploma_shaba.entity.toEntity
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import org.w3c.dom.Entity
import android.net.Uri
import android.util.Log
import androidx.core.net.toFile
import androidx.lifecycle.LiveData
import androidx.paging.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.RequestBody.Companion.asRequestBody
import okio.Buffer
import okio.BufferedSink
import okio.buffer
import okio.sink
import java.io.IOException
import java.io.File
import java.lang.IllegalStateException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.*


class AuthRepositoryImpl(
    private val base: AppDb,
   // private val dao: AuthDao,
    private val api: ApiService,
    private val context: Context,
    private val client : OkHttpClient
    ): AuthRepository {

    override fun authUser( //suspend
        login: String,
        pass: String,
        successCallBack: (id: Long, token: String) -> Unit
    ) {
          val response = api.authMe(login, pass)

            if (!response.isSuccessful) {
                throw Exception("Api Auth Exception")
            }
            val body = response.body()
            if (body?.token != null) {
                successCallBack(body.id, body.token)
            }
    }

//    override fun addUser(user: User) {
//        dao.insert(UserEntity.fromDto(user))
//    }
//
//    override fun findMe(login: String): User {
//        return  dao.findMe(login).toDto()
//    }
//
//    override fun userPassById(id: Long): String {
//        return dao.userPassById(id)
//    }
//    override fun userLoginById(id: Long): String {
//        return dao.userLoginById(id)
//    }
//
//    override fun userIdByLogin(login: String): Long {
//        return dao.userIdByLogin(login)
//    }

    override fun getAllEvents() { //suspend

            val response = api.getAllEvents()
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body = response.body() ?: throw ApiError(response.code(), response.message())

            base.eventDao().insert(body.toEntity())

    }


    override  fun getAllUsers() { //suspend
            val response = api.getAllUsers()
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body = response.body() ?: throw ApiError(response.code(), response.message())
            base.userDao().insert(body.toEntity())

    }


    override fun getAllPosts() { //suspend
            val response = api.getAllPosts()
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body = response.body() ?: throw ApiError(response.code(), response.message())
            base.postDao().insert(body.toEntity())
    }

//    @ExperimentalPagingApi
//    override val pdata: Flow<PagingData<Post>> = Pager(
//        remoteMediator = PostRemoteMediator(api, base, this),
//        config = PagingConfig(pageSize = 5, enablePlaceholders = false),
//        pagingSourceFactory = base.postDao()::getAllPosts
//    ).flow.map {
//        it.map(PostEntity::toDto)
//    }

    @ExperimentalPagingApi
    override val udata: Flow<PagingData<User>> = Pager(
        remoteMediator = UserRemoteMediator(api, base, this),
        config = PagingConfig(pageSize = 5, enablePlaceholders = false),
        pagingSourceFactory = base.userDao()::getAll
    ).flow.map {
        it.map(UserEntity::toDto)
    }

//    @ExperimentalPagingApi
//    override val edata: Flow<PagingData<Event>> = Pager(
//        remoteMediator = EventsRemoteMediator(api, base, this),
//        config = PagingConfig(pageSize = 5, enablePlaceholders = false),
//        pagingSourceFactory = base.eventDao()::getAll
//    ).flow.map {
//        it.map(EventEntity::toDto)
//    }

    ///---------------------------- Viewpager's shared prefs -------------------------------------

    private val prefs = context.getSharedPreferences("MainViewPager", Context.MODE_PRIVATE)

     fun saveViewPagerPageToPrefs(position: Int) {
        with(prefs.edit()) {
            putInt("last", position)
            apply()
        }
    }

     fun getSavedViewPagerPage(): Int {
        return prefs.getInt("last", 0)
    }



}



//    override fun findMe(login: String, enteredPass: String): User {
//        dao.findMe(login)
//
//        var ifAuthorized = false
//        val pass = userPassById(id)
//        if (pass == enteredPass) {
//            ifAuthorized = true
//        }
//        return ifAuthorized
//    }

//    override fun findMe(login: String) = Transformations.map(dao.findMe(login)){ list ->
//        list.map{
//            User(
//                it.userId,
//                it.userLogin,
//                it.userPass,
//                it.userAvatar,
//                it.userFirstName,
//                it.userLastName//,
//                //  it.userBirthDate,
//                // it.userAuthorities
//            )
//        }
//
//    }
