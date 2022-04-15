package com.example.and_diploma_shaba.repository
import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.and_diploma_shaba.dto.Post
import com.example.and_diploma_shaba.dto.User
import com.example.and_diploma_shaba.entity.PostEntity
import com.example.and_diploma_shaba.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
  //  fun isAuthorized(login: String, enteredPass: String): Boolean
   // fun findMe(login: String): User
   // fun addUser(user: User)
    //fun userPassById(id: Long): String
    //fun userIdByLogin(login: String): Long
    //fun userLoginById(id: Long): String
    fun authUser(login: String, pass: String, function: (id: Long, token: String) -> Unit) //suspend
    fun getAllEvents()
    fun getAllUsers()
    fun getAllPosts()
    //suspend fun checkToken(): Boolean

}

enum class AppNetState {
  NO_INTERNET, NO_SERVER_CONNECTION, CONNECTION_ESTABLISHED, THIS_USER_NOT_REGISTERED,
  INCORRECT_PASSWORD, SERVER_ERROR_500
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









//interface AuthMethods {
//  //suspend fun checkConnection(): AppNetState
//   fun authUser(login: String, pass: String, function: (id: Long, token: String) -> Unit) //suspend
//  suspend fun checkToken(): Boolean
//  suspend fun regNewUser(
//    login: String,
//    pass: String,
//    name: String,
//    uri : String? = null,
//    success: (id: Long, token: String) -> Unit
//  )
//}