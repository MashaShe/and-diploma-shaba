package com.example.and_diploma_shaba.repository



import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import com.example.and_diploma_shaba.dto.*
import com.example.and_diploma_shaba.entity.EventEntity
//import com.example.and_diploma_shaba.entity.JobEntity
import com.example.and_diploma_shaba.entity.PostEntity
import com.example.and_diploma_shaba.entity.UserEntity
//import com.example.and_diploma_shaba.entity.forWorker.EventWorkEntity
import com.example.and_diploma_shaba.viewmodel.CallbackR
import java.io.File


interface AppEntities : UserRepository, PostRepository, EventRepository, //JobsRepository,
  AuthMethods, AppWork


enum class AppNetState {
  NO_INTERNET, NO_SERVER_CONNECTION, CONNECTION_ESTABLISHED, THIS_USER_NOT_REGISTERED,
  INCORRECT_PASSWORD, SERVER_ERROR_500
}

enum class RecordOperation {
  NEW_RECORD, CHANGE_RECORD, DELETE_RECORD
}


interface AppWork {
  fun saveViewPagerPageToPrefs(position: Int)
  fun getSavedViewPagerPage(): Int
 // fun download(url: String, destFile: File, answer: CallbackR<Byte>)
}



interface AuthMethods {
  suspend fun checkConnection(): AppNetState
  suspend fun authUser(login: String, pass: String, function: (id: Long, token: String) -> Unit)
  suspend fun checkToken(): Boolean
  suspend fun regNewUser(
    login: String,
    pass: String,
    name: String,
    uri : String? = null,
    success: (id: Long, token: String) -> Unit
  )
}

//interface JobsRepository {
//  suspend fun getJobsById(id: Long)
//  suspend fun processJobWork(task: Array<String>)
//  suspend fun deleteJob(id: Long)
//  //suspend fun postJob(jobReq: JobReq)
// // suspend fun getJobById(id: Long): JobEntity
//  //suspend fun saveJobForWorker(job: JobReq): Long
//}

interface EventRepository {
  val edata: Flow<PagingData<Event>>
  suspend fun getAllEvents()
  suspend fun processEventWork(task: Array<String>)
  suspend fun saveEventForWorker(event: Event, uri: String?, type: String?): Long
  suspend fun getEventByIdFromDB(id: Long): EventEntity?
  suspend fun sendDeleteEvent(id: Long)
  suspend fun prepareEventFromEntity(event: Event, operation: RecordOperation): Event
  suspend fun sendWholeEventToServer(event: Event)
  suspend fun likeEventById(id: Long)
  suspend fun setDislikeToEventById(id: Long)
  suspend fun participateToEvent(id: Long)
  suspend fun doNotParticipateToEvent(id: Long)
  suspend fun updateEvent(event: Event)
}

interface UserRepository {
  val udata: Flow<PagingData<User>>
  suspend fun getAllUsers()
  fun getUserById(id: Long): LiveData<List<UserEntity>>
  suspend fun getAllUsersFromDB(): List<UserEntity>
}


interface PostRepository {
  val pdata: Flow<PagingData<Post>>
  suspend fun getPostsById(id: Long)
  //suspend fun getEventById(id: Long)
  suspend fun getAllPosts()
  suspend fun sendWholePostToServer(post: Post)
  suspend fun disLikeById(id: Long)
  suspend fun likeById(id: Long)
  //suspend fun uploadMfileToServer(upload: String): String
  suspend fun savePostForWorker(post: Post//, uri: String?, type: String?
  ) : Long
  suspend fun processPostWork(task: Array<String>)
  suspend fun sendDeletePost(id: Long)
  suspend fun getPostById(id: Long): PostEntity
  suspend fun updatePost(post: Post)
}




//import androidx.lifecycle.LiveData
//import androidx.paging.PagingData
//import com.example.and_diploma_shaba.dto.Post
//import com.example.and_diploma_shaba.dto.User
//import com.example.and_diploma_shaba.entity.PostEntity
//import com.example.and_diploma_shaba.entity.UserEntity
//import kotlinx.coroutines.flow.Flow
//import java.io.File
////import com.example.and_diploma_shaba.viewmodel.CallbackR
//
//
//interface AuthRepository {
//  //  fun isAuthorized(login: String, enteredPass: String): Boolean
//   // fun findMe(login: String): User
//   // fun addUser(user: User)
//    //fun userPassById(id: Long): String
//    //fun userIdByLogin(login: String): Long
//    //fun userLoginById(id: Long): String
//    fun authUser(login: String, pass: String, function: (id: Long, token: String) -> Unit) //suspend
//    fun getAllEvents()
//    fun getAllUsers()
//    fun getAllPosts()
//    //suspend fun checkToken(): Boolean
//
//    //У МЕНЯ НЕКОРРЕКТНОЕ НАСЛЕДОВАНИЕ, НАДО ИСПРАВЛЯТЬ - никто не наследует методы юзера тут походу, но я уже запуталась
//}
//
//enum class AppNetState {
//  NO_INTERNET, NO_SERVER_CONNECTION, CONNECTION_ESTABLISHED, THIS_USER_NOT_REGISTERED,
//  INCORRECT_PASSWORD, SERVER_ERROR_500
//}
//
//interface AuthMethods {
//  suspend fun checkConnection(): AppNetState
//  suspend fun authUser(login: String, pass: String, function: (id: Long, token: String) -> Unit)
//  suspend fun checkToken(): Boolean
//  suspend fun regNewUser(
//    login: String,
//    pass: String,
//    name: String,
//    uri : String? = null,
//    success: (id: Long, token: String) -> Unit
//  )
//}
//interface AppWork {
//  fun saveViewPagerPageToPrefs(position: Int)
//  fun getSavedViewPagerPage(): Int
// // fun download(url: String, destFile: File, answer: CallbackR<Byte>)
//}
//
//
//interface AppEntities : UserRepository, PostRepository, EventRepository,// JobsRepository,
//  AuthMethods, AppWork
//
//
//
//
//
//
//
//
//
////interface AuthMethods {
////  //suspend fun checkConnection(): AppNetState
////   fun authUser(login: String, pass: String, function: (id: Long, token: String) -> Unit) //suspend
////  suspend fun checkToken(): Boolean
////  suspend fun regNewUser(
////    login: String,
////    pass: String,
////    name: String,
////    uri : String? = null,
////    success: (id: Long, token: String) -> Unit
////  )
////}