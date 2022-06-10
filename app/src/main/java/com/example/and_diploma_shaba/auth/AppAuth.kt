package com.example.and_diploma_shaba.auth

import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.and_diploma_shaba.api.ApiError
import com.example.and_diploma_shaba.api.ApiService
import com.example.and_diploma_shaba.dto.AuthState
import kotlinx.coroutines.flow.*
import com.example.and_diploma_shaba.repository.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import javax.inject.Inject

class AppAuth @Inject constructor(
    val context: Context,
    val apiService: ApiService,
    var repository: AuthMethods,
    var prefs: SharedPreferences
) {

    companion object {
        const val idKey = "id"
        const val tokenKey = "token"
        var sessionKey : String? = null
    }

    private val _authStateFlow: MutableStateFlow<AuthState> = MutableStateFlow(AuthState())

    fun checkAmLogined() {
        val (id, token) = getAuthInfo()
        if (id == 0L || token == null) {  //ничего нет- чистим
            removeAuth()
            // токена нет - ничего не делать

        } else {
            // проверить что ключ валидный
            checkTheToken({
                //оказался валидный? - присваиваем
                sessionKey = token
                _authStateFlow.value = AuthState(id, token)
            }, {
                removeAuth()
            })

        }
    }



    private fun checkTheToken(success: () -> Unit, failure: () -> Unit) =
        CoroutineScope(Dispatchers.Default).launch {
            if (repository.checkToken()) {
                success()
            } else {
                failure()
            }
        }


    val authStateFlow: StateFlow<AuthState> = _authStateFlow.asStateFlow()

    val myId: Long
        get() = authStateFlow.value.id

    //----------------------


    fun authUser(login: String, pass: String, callBack: (AppNetState) -> Unit) =
        CoroutineScope(Dispatchers.Default).launch {
            val result : (state: AppNetState)-> Unit = { state ->
                Handler(Looper.getMainLooper()).post {
                    callBack(state)
                }
            }
            when (repository.checkConnection()) {
                AppNetState.CONNECTION_ESTABLISHED -> {
                    try {
                        repository.authUser(login, pass) { id, token ->
                            setAuth(id, token)
                        }
                        result(AppNetState.CONNECTION_ESTABLISHED)
                    } catch (e: ApiError) {
                        if (e.status == 404) {
                            result(AppNetState.THIS_USER_NOT_REGISTERED)
                        }
                        if (e.status == 400) {
                            result(AppNetState.INCORRECT_PASSWORD)
                        }
                        if (e.status == 500) {
                            result(AppNetState.SERVER_ERROR_500)
                        }

                    } catch (e: IllegalStateException) {
                        Log.e("exc", "  error ${e.javaClass.simpleName}  ${e.printStackTrace()} ${e.message}")
                    } catch (e: Exception) {
                        Log.e("exc", "  error ${e.javaClass.simpleName}")
                    }
                }
                AppNetState.NO_INTERNET -> {
                    result(AppNetState.NO_INTERNET)
                }

                AppNetState.NO_SERVER_CONNECTION -> {
                    result(AppNetState.NO_SERVER_CONNECTION)
                }
            }
        }


    fun newUserRegistration(
        login: String,
        pass: String,
        name: String,
       // uri: String? = null,
        callBack: (AppNetState) -> Unit
    ) =
        CoroutineScope(Dispatchers.Default).launch {
            when (repository.checkConnection()) {
                AppNetState.CONNECTION_ESTABLISHED -> {
                    try {
                        repository.regNewUser(login, pass, name//, uri
                        ) { id, token ->
                            removeAuth()
                            setAuth(id, token)
                            Handler(Looper.getMainLooper()).post {
                                callBack(AppNetState.CONNECTION_ESTABLISHED)
                            }
                        }
                    } catch (e: ApiError) {
                        if (e.status == 404) {
                            Handler(Looper.getMainLooper()).post {
                                callBack(AppNetState.THIS_USER_NOT_REGISTERED)
                            }
                        }
                        if (e.status == 400) {
                            Handler(Looper.getMainLooper()).post {
                                callBack(AppNetState.INCORRECT_PASSWORD)
                            }
                        }
                        if (e.status == 500) {
                            Handler(Looper.getMainLooper()).post {
                                callBack(AppNetState.SERVER_ERROR_500)
                            }
                        }
                    } catch (e: Exception) {
                        Log.e("exc", " error ${e.javaClass.simpleName}")
                    }
                }

                AppNetState.NO_INTERNET -> {
                    Handler(Looper.getMainLooper()).post {
                        callBack(AppNetState.NO_INTERNET)
                    }
                }

                AppNetState.NO_SERVER_CONNECTION -> {
                    Handler(Looper.getMainLooper()).post {
                        callBack(AppNetState.NO_SERVER_CONNECTION)
                    }
                }

            }
        }

    //-------


    @Synchronized
    private fun setAuth(id: Long, token: String) {
        val charset: Charset = StandardCharsets.US_ASCII
        val byteArrray: ByteArray = charset.encode(token).array()
        val result = byteArrray.joinToString()

        sessionKey = token

        _authStateFlow.value = AuthState(id, token)
        with(prefs.edit()) {
            putLong(idKey, id)
            putString(tokenKey, result)
            apply()
        }
    }

    fun getAuthInfo(): Pair<Long, String?> {
        val charset: Charset = StandardCharsets.US_ASCII
        val s = prefs.getString(tokenKey, null)?.split(", ")?.map {it.toByte()}?.toByteArray()
        val result3 = String(s ?: byteArrayOf(), charset)

        sessionKey = result3

        return prefs.getLong(idKey, 0) to result3

    }

    @Synchronized
    fun removeAuth() {
        _authStateFlow.value = AuthState()
        sessionKey = null
        with(prefs.edit()) {
            clear()
            apply()
        }
    }

}


//
//class AppAuth @Inject constructor(
//    val context: Context,
//    val apiService: ApiService,
//    var repository: AppEntities,
//    var prefs: SharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
//    ) {
//    //private val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
//    private val idKey = "id"
//    private val tokenKey = "token"
//    private val _authStateFlow: MutableStateFlow<AuthState>
//
//    init {
//        val id = prefs.getLong(idKey, 0)
//        val token = prefs.getString(tokenKey, null)
//
//        if (id == 0L || token == null) {
//            _authStateFlow = MutableStateFlow(AuthState())
//            with(prefs.edit()) {
//                clear()
//                apply()
//            }
//        } else {
//            _authStateFlow = MutableStateFlow(AuthState(id, token))
//        }
//    }
//
//    val authStateFlow: StateFlow<AuthState> = _authStateFlow.asStateFlow()
//
//    @Synchronized
//    fun setAuth(id: Long, token: String) {
//        _authStateFlow.value = AuthState(id, token)
//        with(prefs.edit()) {
//            putLong(idKey, id)
//            putString(tokenKey, token)
//            apply()
//        }
//    }
//
//    @Synchronized
//    fun removeAuth() {
//        _authStateFlow.value = AuthState()
//        with(prefs.edit()) {
//            clear()
//            commit()
//        }
//    }
//
////    companion object {
////        @Volatile
////        private var instance: AppAuth? = null
////
////        fun getInstance(): AppAuth = synchronized(this) {
////            instance ?: throw IllegalStateException(
////                "AppAuth is not initialized, you must call AppAuth.initializeApp(Context context) first."
////            )
////        }
////
////        fun initApp(context: Context): AppAuth = instance ?: synchronized(this) {
////            instance ?: buildAuth(context).also { instance = it }
////        }
////
////        private fun buildAuth(context: Context): AppAuth = AppAuth(context)
////    }
//
//    companion object {
//        const val idKey = "id"
//        const val tokenKey = "token"
//        var sessionKey : String? = null
//    }
//
//
//
//     fun authUser(login: String, pass: String) { //suspend
//            repository.authUser(login, pass) { id, token ->
//                setAuth(id, token)
//            }
//    }
//
//
//}
//
//data class AuthState(val id: Long = 0, val token: String? = null)
//
//
//
//

