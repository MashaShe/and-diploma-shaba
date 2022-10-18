package com.example.and_diploma_shaba.repository

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.net.toFile
import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData

import androidx.paging.*
import com.example.and_diploma_shaba.adapter.PostRemoteMediator
import com.example.and_diploma_shaba.adapter.UserRemoteMediator
import com.example.and_diploma_shaba.api.ApiError
import com.example.and_diploma_shaba.api.ApiService
import com.example.and_diploma_shaba.api.NetworkError
import com.example.and_diploma_shaba.api.checkInternetConnection
import com.example.and_diploma_shaba.db.AppDb
import com.example.and_diploma_shaba.dto.MediaUpload
import com.example.and_diploma_shaba.dto.Post
import com.example.and_diploma_shaba.dto.User
import com.example.and_diploma_shaba.entity.PostEntity
import com.example.and_diploma_shaba.entity.PostWorkEntity
import com.example.and_diploma_shaba.entity.UserEntity
import com.example.and_diploma_shaba.entity.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okio.Buffer
import okio.BufferedSink
import okio.buffer
import okio.sink
import java.io.File
import java.io.IOException
import java.lang.IllegalStateException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.*
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody


class AppRepositoryImpl(
    private val base: AppDb,
    private val api: ApiService,
    private val context: Context,
    private val client : OkHttpClient
) : AppEntities {

    // --------------  Fragments  All Posts, All Users, All Events ------------------------

    @ExperimentalPagingApi
    override val pdata: Flow<PagingData<Post>> = Pager( //проблема с пейджером обычно в дао и в методе гет алл, который возвращает что-то не то
        remoteMediator = PostRemoteMediator(api, base, this),
        config = PagingConfig(pageSize = 5, enablePlaceholders = false),
        pagingSourceFactory = base.postDao()::getAllPosts
    ).flow.map {
        it.map(PostEntity::toDto)
    }

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

    override fun saveViewPagerPageToPrefs(position: Int) {
        with(prefs.edit()) {
            putInt("last", position)
            apply()
        }
    }

    override fun getSavedViewPagerPage(): Int {
        return prefs.getInt("last", 0)
    }

    //-----------------------------------------------------------------------------------------


    override fun getUserById(id: Long): LiveData<List<UserEntity>> {
        return base.userDao().getUser(id)
    }

    override suspend fun getAllUsersFromDB(): List<UserEntity> {
        return base.userDao().getAllUsers()
    }

    override suspend fun getPostById(id: Long): PostEntity {
        return base.postDao().getPost(id)
    }

//    override suspend fun getJobById(id: Long): JobEntity {
//        return base.jobDao().getJobById(id)
//    }

//    override suspend fun getEventByIdFromDB(id: Long): EventEntity? {
//        return base.eventDao().getEvent(id)
//    }


    //---------------------------- REST requests ----------------------------------------------

    private suspend fun tryCatchWrapper(logInfo: String, callBackBody: suspend () -> Unit) {
        val wrapperId = "${object {}.javaClass.enclosingMethod.name} exc"
        try {
            callBackBody()
        } catch (e: ConnectException) {
            Log.e(wrapperId, "$logInfo ${e.javaClass.simpleName}   ${e.message}  ${e.cause}")
            throw NetworkError
        } catch (e: SocketTimeoutException) {
            Log.e(wrapperId, "$logInfo ${e.javaClass.simpleName}  ${e.message}  ${e.cause}")
            throw NetworkError
        } catch (e: ApiError) {
            Log.e(
                wrapperId,
                "$logInfo ${e.javaClass.simpleName} ${e.status} | ${e.code} | ${e.cause}"
            )
            throw e
        } catch (e: IOException) {
            Log.e(
                wrapperId,
                "$logInfo ${e.javaClass.simpleName} Unknown NetworkError ${e.message}  ${e.cause}"
            )
            throw NetworkError
        } catch (e: Exception) {
            Log.e(wrapperId, "$logInfo ${e.javaClass.simpleName} ${e.message}  ${e.cause}")
            throw NetworkError //UnknownError
        }
    }


    private suspend fun checkServerAvalable(): Boolean {
        return try {
            val response = api.checkToken()
            response.code() != 503
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun checkConnection(): AppNetState = withContext(Dispatchers.IO) {
        if (checkInternetConnection(context)) {
            if (checkServerAvalable()) {
                return@withContext AppNetState.CONNECTION_ESTABLISHED
            } else {
                return@withContext AppNetState.NO_SERVER_CONNECTION
            }
        } else {
            return@withContext AppNetState.NO_INTERNET
        }
    }


    override suspend fun authUser(
        login: String,
        pass: String,
        successCallBack: (id: Long, token: String) -> Unit
    ) {
        tryCatchWrapper(object {}.javaClass.enclosingMethod.name) {
            val response = api.authMe(login, pass)

            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body = response.body()
            if (body?.token != null) {
                successCallBack(body.id, body.token)
            }
        }
    }

    override suspend fun checkToken(): Boolean {
        return try {
            val response = api.checkToken()
            response.code() != 405 && response.code() < 500
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun regNewUser(
        login: String,
        pass: String,
        name: String,
        uri : String?,
        successCase: (id: Long, token: String) -> Unit) {

        tryCatchWrapper(object {}.javaClass.enclosingMethod.name) {
            var media : MultipartBody.Part = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("", "")
                .build().part(0)

            if (uri != null) {
                val uploadMedia = MediaUpload(Uri.parse(uri).toFile())
                media = MultipartBody.Part.createFormData(
                    "file", uploadMedia.file.name, uploadMedia.file.asRequestBody())
            }

            val response = api.regMeAvatar(login, pass, name, media)

            val body = response.body()
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            if (body?.token != null) {
                successCase(body.id, body.token)
            }
        }
    }

//    override suspend fun regNewUser(
//        login: String,
//        pass: String,
//        name: String,
//        uri : String?,
//        successCase: (id: Long, token: String) -> Unit) {
//
//        tryCatchWrapper(object {}.javaClass.enclosingMethod.name) {
//            var media : MultipartBody.Part = MultipartBody.Builder().setType(MultipartBody.FORM)
//                .addFormDataPart("", "")
//                .build().part(0)
//
//            if (uri != null) {
//                val uploadMedia = (Uri.parse(uri).toFile())
//                media = MultipartBody.Part.createFormData(
//                    "file", uploadMedia.name, uploadMedia.asRequestBody())
//            }
//
//            val response = api.regMeAvatar(login, pass, name, media
//                )
////            123456
////            val response = api.regMeAvatar(
////                login.toRequestBody("text/plain".toMediaType()),
////                pass.toRequestBody("text/plain".toMediaType()),
////                name.toRequestBody("text/plain".toMediaType()),
////                media
////            )
//
//            val body = response.body()
//            if (!response.isSuccessful) {
//                throw ApiError(response.code(), response.message())
//            }
//            if (body?.token != null) {
//                successCase(body.id, body.token)
//                //////
//            }
//        }
//    }


    //------------ fragments API calls -------------------------------------------------

//    override suspend fun getJobsById(id: Long) {
//        tryCatchWrapper(object {}.javaClass.enclosingMethod.name) {
//            val response = api.getJobs(id)
//            if (!response.isSuccessful) {
//                throw ApiError(response.code(), response.message())
//            }
//
//            val body = response.body() ?: throw ApiError(response.code(), response.message())
//            base.jobDao().insert(body.toEntity(id))
//        }
//    }

//
//    override suspend fun getEventById(id: Long) {
//        tryCatchWrapper(object {}.javaClass.enclosingMethod.name) {
//            val response = api.getEventById(id)
//            if (!response.isSuccessful && response.code() != 404) {
//                throw ApiError(response.code(), response.message())
//            }
//
//            if (response.code() == 404) { //server FIX
//                return@tryCatchWrapper
//            }
//
//            val body = response.body() ?: throw ApiError(response.code(), response.message())
//            base.eventDao().insert(EventEntity.fromDto(body))
//        }
//    }


    override suspend fun getPostsById(id: Long) {
        tryCatchWrapper(object {}.javaClass.enclosingMethod.name) {
            val response = api.getWall(id)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body = response.body() ?: throw ApiError(response.code(), response.message())


            base.postDao().insert(body.toEntity())
        }
    }


//    override suspend fun getAllEvents() {
//        tryCatchWrapper(object {}.javaClass.enclosingMethod.name) {
//            val response = api.getAllEvents()
//            if (!response.isSuccessful) {
//                throw ApiError(response.code(), response.message())
//            }
//            val body = response.body() ?: throw ApiError(response.code(), response.message())
//
//            base.eventDao().insert(body.toEntity())
//        }
//    }


    override suspend fun getAllUsers() {
        tryCatchWrapper(object {}.javaClass.enclosingMethod.name) {
            val response = api.getAllUsers()
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body = response.body() ?: throw ApiError(response.code(), response.message())
            base.userDao().insert(body.toEntity())
        }
    }


    override suspend fun getAllPosts() {
        tryCatchWrapper(object {}.javaClass.enclosingMethod.name) {
            val response = api.getAllPosts()
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body = response.body() ?: throw ApiError(response.code(), response.message())
            base.postDao().insert(body.toEntity())
        }
    }


    // ------------------posts like-dislike ------------------------------------

    override suspend fun disLikeById(id: Long) {
        tryCatchWrapper(object {}.javaClass.enclosingMethod.name) {
            val response = api.setDislikeToPost(id)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body = response.body() ?: throw ApiError(response.code(), response.message())

            base.postDao().insert(PostEntity.fromDto(body))

        }
    }

    override suspend fun likeById(id: Long) {
        tryCatchWrapper(object {}.javaClass.enclosingMethod.name) {
            val response = api.setLikeToPost(id)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body = response.body() ?: throw ApiError(response.code(), response.message())

            base.postDao().insert(PostEntity.fromDto(body))
        }
    }

    //-------------------------- Posts / Media upload-------------------------------------


    override suspend fun savePostForWorker(post: Post, uri: String?, type: String?): Long {
        val entity = PostWorkEntity.fromDto(post)
        return base.postWorkDao().insert(entity.copy(postAttachmentURL = uri, postAttachmentType = type))
    }
    //РАЗОБРАТЬСЯ С МЕДИА В ПОСТАХ И АВАТАРКЕ СМ ЕНТИТИ И ДАО, НЕ ЗАВЕДЕНЫ медиа параметры поста и аватара


    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun processPostWork(task: Array<String>) {
        tryCatchWrapper(object {}.javaClass.enclosingMethod.name) {
            val id = task[1].toLong()
            val operation = RecordOperation.valueOf(task[0])

            if (operation == RecordOperation.DELETE_RECORD) {
                sendDeletePost(id)
                return@tryCatchWrapper
            }

            //NEW or EDIT post
            val entity = base.postWorkDao().getById(id)
            val post = preparePostFromEntity(entity, operation)





            //upload new attach and post to server
//            if (entity.mediaUri != null && entity.mediaType != null) {
//                val mediaFileId = uploadMfileToServer(entity.mediaUri)
//
//                val postWithAttachment =
//                    post.copy(attachment = Attachment(entity.mediaType, mediaFileId))
//                sendWholePostToServer(postWithAttachment)

                //user decided to delete attachment of post
//            } else if (entity.mediaUri == null && entity.mediaType == null) {
//                sendWholePostToServer(post.copy(attachment = null))
//                //do not update attachment, just send it like it was
//            } else if (entity.mediaUri == null && entity.mediaType != null) {
//                sendWholePostToServer(post)
//            }
            base.postWorkDao().removeById(id)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun preparePostFromEntity(entity: PostWorkEntity, task: RecordOperation): Post {
        return Post(
            postId = if (task == RecordOperation.NEW_RECORD) {
                0
            } else {
                if (task == RecordOperation.CHANGE_RECORD) {
                    entity.postId
                } else {
                    throw  IllegalStateException("Wrong post operation")
                }
            }, // ПРОВЕРИТЬ ВСЕ ПОЛЯ
            authorId = entity.authorId,
            postAuthor = entity.postAuthor,
            authorAvatar = entity.authorAvatar,
            postContent = entity.postContent,
            postPublishedDate = Date().toInstant().toString(),
            postLikes = entity.postLikes,
            postLikedByMe = entity.postLikedByMe,
            rePosts = entity.rePosts,
            postSeen = entity.postSeen,
            video = entity.video,
            postAttachmentURL = entity.postAttachmentURL,
            postAttachmentType = entity.postAttachmentType,
            logined = entity.logined,
            ownedByMe = entity.ownedByMe
//            attachment = entity.attachment,
//            coords = entity.coords,
//            likeOwnerIds = entity.likeOwnerIds,
//            link = entity.link,
//            mentionedMe = entity.mentionedMe,
//            mentionIds = entity.mentionIds,
//            downloadingProgress = null
        )
    }

//todo разобраться с загрузкой медиа
//    override suspend fun uploadMfileToServer(uri: String): String {
//        val uploadMedia = MediaUpload(Uri.parse(uri).toFile())
//        val media = MultipartBody.Part.createFormData(
//            "file", uploadMedia.file.name, uploadMedia.file.asRequestBody()
//        )
//
//        val response = api.upload(media)
//        if (!response.isSuccessful) {
//            throw ApiError(response.code(), response.message())
//        }
//
//        val container = response.body() ?: throw ApiError(response.code(), response.message())
//        return container.url
//    }

    override suspend fun sendWholePostToServer(post: Post) {
        tryCatchWrapper(object {}.javaClass.enclosingMethod.name) {

            val response = api.savePost(post)

            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body = response.body() ?: throw ApiError(response.code(), response.message())
            base.postDao().insert(PostEntity.fromDto(body))
        }
    }


    override suspend fun sendDeletePost(id: Long) {
        tryCatchWrapper(object {}.javaClass.enclosingMethod.name) {
            val response = api.deletePost(id)

            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            base.postDao().removeById(id)
        }
    }


    //------------------------- JOB ------------------------------------------
//    override suspend fun saveJobForWorker(job: JobReq): Long {
//        val entity = JobWorkEntity.fromDto(job)
//        return base.jobWorkDao().insert(entity)
//    }
//
//    override suspend fun postJob(jobReq: JobReq) {
//        tryCatchWrapper(object {}.javaClass.enclosingMethod.name) {
//            val response = api.postJob(jobReq)
//
//            if (!response.isSuccessful) {
//                throw ApiError(response.code(), response.message())
//            }
//            base.jobDao().removeById(jobReq.id)
//        }
//    }
//
//    override suspend fun deleteJob(id: Long) {
//        tryCatchWrapper(object {}.javaClass.enclosingMethod.name) {
//            val response = api.deleteJob(id)
//            if (!response.isSuccessful) {
//                throw ApiError(response.code(), response.message())
//            }
//            base.jobDao().removeById(id)
//        }
//    }
//
//    override suspend fun processJobWork(task: Array<String>) {
//        tryCatchWrapper(object {}.javaClass.enclosingMethod.name) {
//            val id = task[1].toLong()
//            val operation = RecordOperation.valueOf(task[0])
//
//            if (operation == RecordOperation.DELETE_RECORD) {
//                deleteJob(id)
//            } else {
//                postJob(base.jobWorkDao().getById(id).toDto())
//            }
//
//        }
//    }

    //---------------------------  EVENTS  ----------------
//    override suspend fun processEventWork(task: Array<String>) {
//        tryCatchWrapper(object {}.javaClass.enclosingMethod.name) {
//            val id = task[1].toLong()
//            val operation = RecordOperation.valueOf(task[0])
//
//            if (operation == RecordOperation.DELETE_RECORD) {
//                sendDeleteEvent(id)
//                return@tryCatchWrapper
//            }
//
//            //NEW or EDIT event
//            val entity = base.eventWorkDao().getById(id)
//
//            val event = prepareEventFromEntity(entity.toDto(), operation)
//
//            //upload new attach and post to server
//            if (entity.mediaUri != null && entity.mediaType != null) {
//                val mediaFileId = uploadMfileToServer(entity.mediaUri)
//
//                val eventWithAttachment =
//                    event.copy(attachment = Attachment(entity.mediaType, mediaFileId))
//                sendWholeEventToServer(eventWithAttachment)
//
//                //user decided to delete attachment of post
//            } else if (entity.mediaUri == null && entity.mediaType == null) {
//                sendWholeEventToServer(event.copy(attachment = null))
//                //do not update attachment, just send it like it was
//            } else if (entity.mediaUri == null && entity.mediaType != null) {
//                sendWholeEventToServer(event)
//            }
//            base.eventWorkDao().removeById(id)
//        }
//    }
//
//    override suspend fun saveEventForWorker(event: Event, uri: String?, type: String?): Long {
//        val entity = EventWorkEntity.fromDto(event)
//        return base.eventWorkDao().insert(entity.copy(mediaUri = uri, mediaType = type))
//    }
//
//
//    override suspend fun sendDeleteEvent(id: Long) {
//        tryCatchWrapper(object {}.javaClass.enclosingMethod.name){
//            val response = api.deleteEvent(id)
//
//            if (!response.isSuccessful) {
//                throw ApiError(response.code(), response.message())
//            }
//            base.eventDao().removeById(id)
//        }
//    }
//
//    override suspend fun prepareEventFromEntity(event: Event, task: RecordOperation): Event {
//        return  event.copy(id = if (task == RecordOperation.NEW_RECORD) {
//            0
//        } else {
//            if (task == RecordOperation.CHANGE_RECORD) {
//                event.id
//            } else {
//                throw  IllegalStateException("Wrong event operation")
//            }
//
//        })
//
//    }
//
//    override suspend fun sendWholeEventToServer(event: Event) {
//        tryCatchWrapper(object {}.javaClass.enclosingMethod.name) {
//            val response = api.saveEvent(event)
//
//            if (!response.isSuccessful) {
//                throw ApiError(response.code(), response.message())
//            }
//            val body = response.body() ?: throw ApiError(response.code(), response.message())
//            base.eventDao().insert(EventEntity.fromDto(body))
//        }
//    }
//
//
//    override suspend fun likeEventById(id: Long) {
//        tryCatchWrapper(object {}.javaClass.enclosingMethod.name) {
//            val response = api.setLikeToEvent(id)
//            if (!response.isSuccessful) {
//                throw ApiError(response.code(), response.message())
//            }
//            val body = response.body() ?: throw ApiError(response.code(), response.message())
//
//            base.eventDao().insert(EventEntity.fromDto(body))
//        }
//    }
//
//
//    override suspend fun setDislikeToEventById(id: Long) {
//        tryCatchWrapper(object {}.javaClass.enclosingMethod.name) {
//            val response = api.setDislikeToEvent(id)
//            if (!response.isSuccessful) {
//                throw ApiError(response.code(), response.message())
//            }
//            val body = response.body() ?: throw ApiError(response.code(), response.message())
//
//            base.eventDao().insert(EventEntity.fromDto(body))
//        }
//    }
//
//    override suspend fun participateToEvent(id: Long) {
//        tryCatchWrapper(object {}.javaClass.enclosingMethod.name) {
//            val response = api.participateToEvent(id)
//            if (!response.isSuccessful) {
//                throw ApiError(response.code(), response.message())
//            }
//            val body = response.body() ?: throw ApiError(response.code(), response.message())
//
//            base.eventDao().insert(EventEntity.fromDto(body))
//        }
//    }
//
//    override suspend fun doNotParticipateToEvent(id: Long) {
//        tryCatchWrapper(object {}.javaClass.enclosingMethod.name) {
//            val response = api.doNotParticipateToEvent(id)
//            if (!response.isSuccessful) {
//                throw ApiError(response.code(), response.message())
//            }
//            val body = response.body() ?: throw ApiError(response.code(), response.message())
//
//            base.eventDao().insert(EventEntity.fromDto(body))
//        }
//    }



    //--------------------------------------------------------------------------------
    //TODO это во вью модели надо разобраться насчет даунлоада
//    override fun download(url: String, destFile: File, answer : CallbackR<Byte>) {
//        val request: Request = Request.Builder().url(url).build()
//
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                answer.onError(e)
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                val body = response.body
//                val contentLength = body!!.contentLength()
//                val source = body.source()
//                val sink: BufferedSink = destFile.sink().buffer()
//                val sinkBuffer: Buffer = sink.buffer
//                var totalBytesRead: Long = 0
//                val bufferSize = 8 * 1024
//                var bytesRead: Long
//                while (source.read(sinkBuffer, bufferSize.toLong()).also { bytesRead = it } != -1L) {
//                    sink.emit()
//                    totalBytesRead += bytesRead
//                    val progress = (totalBytesRead * 100 / contentLength).toInt()
//                    if (progress % 5 == 0){
//                        answer.onSuccess(progress.toByte()) }
//                }
//                sink.flush()
//                sink.close()
//                source.close()
//            }
//        })
//    }

    override suspend fun updatePost(post: Post) {
        tryCatchWrapper(object {}.javaClass.enclosingMethod.name) {
            base.postDao().insert(PostEntity.fromDto(post))
        }
    }


//    override suspend fun updateEvent(event: Event) {
//        tryCatchWrapper(object {}.javaClass.enclosingMethod.name) {
//            base.eventDao().insert(EventEntity.fromDto(event))
//        }
//    }
}



//
//
//class AuthRepositoryImpl(
//    private val base: AppDb,
//   // private val dao: AuthDao,
//    private val api: ApiService,
//    private val context: Context,
//    private val client : OkHttpClient,
//    private val authMethods: AuthMethods
//    ): AuthRepository {
//
//    override fun authUser( //suspend
//        login: String,
//        pass: String,
//        successCallBack: (id: Long, token: String) -> Unit
//    ) {
//          val response = api.authMe(login, pass)
//
//            if (!response.isSuccessful) {
//                throw Exception("Api Auth Exception")
//            }
//            val body = response.body()
//            if (body?.token != null) {
//                successCallBack(body.id, body.token)
//            }
//    }
//
////    override fun addUser(user: User) {
////        dao.insert(UserEntity.fromDto(user))
////    }
////
////    override fun findMe(login: String): User {
////        return  dao.findMe(login).toDto()
////    }
////
////    override fun userPassById(id: Long): String {
////        return dao.userPassById(id)
////    }
////    override fun userLoginById(id: Long): String {
////        return dao.userLoginById(id)
////    }
////
////    override fun userIdByLogin(login: String): Long {
////        return dao.userIdByLogin(login)
////    }
//
//    override fun getAllEvents() { //suspend
//
//            val response = api.getAllEvents()
//            if (!response.isSuccessful) {
//                throw ApiError(response.code(), response.message())
//            }
//            val body = response.body() ?: throw ApiError(response.code(), response.message())
//
//            base.eventDao().insert(body.toEntity())
//
//    }
//
//
//    override  fun getAllUsers() { //suspend
//            val response = api.getAllUsers()
//            if (!response.isSuccessful) {
//                throw ApiError(response.code(), response.message())
//            }
//            val body = response.body() ?: throw ApiError(response.code(), response.message())
//            base.userDao().insert(body.toEntity())
//
//    }
//
//
//    override fun getAllPosts() { //suspend
//            val response = api.getAllPosts()
//            if (!response.isSuccessful) {
//                throw ApiError(response.code(), response.message())
//            }
//            val body = response.body() ?: throw ApiError(response.code(), response.message())
//            base.postDao().insert(body.toEntity())
//    }
//
////    @ExperimentalPagingApi
////    override val pdata: Flow<PagingData<Post>> = Pager(
////        remoteMediator = PostRemoteMediator(api, base, this),
////        config = PagingConfig(pageSize = 5, enablePlaceholders = false),
////        pagingSourceFactory = base.postDao()::getAllPosts
////    ).flow.map {
////        it.map(PostEntity::toDto)
////    }
//
//    @ExperimentalPagingApi
//     val udata: Flow<PagingData<User>> = Pager(
//        remoteMediator = UserRemoteMediator(api, base, authMethods),
//        config = PagingConfig(pageSize = 5, enablePlaceholders = false),
//        pagingSourceFactory = base.userDao().getAll()
//    ).flow.map {
//        it.map(UserEntity::toDto)
//    }
//
////    @ExperimentalPagingApi
////    override val edata: Flow<PagingData<Event>> = Pager(
////        remoteMediator = EventsRemoteMediator(api, base, this),
////        config = PagingConfig(pageSize = 5, enablePlaceholders = false),
////        pagingSourceFactory = base.eventDao()::getAll
////    ).flow.map {
////        it.map(EventEntity::toDto)
////    }
//
//    ///---------------------------- Viewpager's shared prefs -------------------------------------
//
//    private val prefs = context.getSharedPreferences("MainViewPager", Context.MODE_PRIVATE)
//
//     fun saveViewPagerPageToPrefs(position: Int) {
//        with(prefs.edit()) {
//            putInt("last", position)
//            apply()
//        }
//    }
//
//     fun getSavedViewPagerPage(): Int {
//        return prefs.getInt("last", 0)
//    }
//
//
//
//
//
//
//
//
//}
//
//
//
////    override fun findMe(login: String, enteredPass: String): User {
////        dao.findMe(login)
////
////        var ifAuthorized = false
////        val pass = userPassById(id)
////        if (pass == enteredPass) {
////            ifAuthorized = true
////        }
////        return ifAuthorized
////    }
//
////    override fun findMe(login: String) = Transformations.map(dao.findMe(login)){ list ->
////        list.map{
////            User(
////                it.userId,
////                it.userLogin,
////                it.userPass,
////                it.userAvatar,
////                it.userFirstName,
////                it.userLastName//,
////                //  it.userBirthDate,
////                // it.userAuthorities
////            )
////        }
////
////    }
