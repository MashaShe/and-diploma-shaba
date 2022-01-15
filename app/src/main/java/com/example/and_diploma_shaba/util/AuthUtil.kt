package com.example.and_diploma_shaba.util

//import android.content.Context
//import android.content.SharedPreferences
//import android.os.Handler
//import android.os.Looper
//import android.util.Log
//import androidx.security.crypto.EncryptedSharedPreferences
//import androidx.security.crypto.MasterKey
//import com.example.and_diploma_shaba.api.ApiService
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//import ru.kot1.demo.api.ApiService
//import ru.kot1.demo.dto.AuthState
//import ru.kot1.demo.error.ApiError
//import ru.kot1.demo.repository.AppNetState
//import ru.kot1.demo.repository.AuthMethods
//import java.nio.charset.Charset
//import java.nio.charset.StandardCharsets
//import javax.inject.Inject


class AuthUtil {}
//(
//    val context: Context,
//    val apiService: ApiService,
//    var repository: AuthMethods,
//    var prefs: SharedPreferences
//) {
//
//    companion object {
//        const val idKey = "id"
//        const val tokenKey = "token"
//        var sessionKey : String? = null
//    }
//
//
//
//    fun authUser(login: String, pass: String) =
//
//          repository.authUser(login, pass) { id, token ->
//                            setAuth(id, token)
//                        }
//
//
//
//
//
//    fun newUserRegistration(
//        login: String,
//        pass: String,
//        name: String,
//        uri: String? = null,
//        callBack: (AppNetState) -> Unit
//    ) =
//        CoroutineScope(Dispatchers.Default).launch {
//            when (repository.checkConnection()) {
//                AppNetState.CONNECTION_ESTABLISHED -> {
//                    try {
//                        repository.regNewUser(login, pass, name, uri) { id, token ->
//                            removeAuth()
//                            setAuth(id, token)
//                            Handler(Looper.getMainLooper()).post {
//                                callBack(AppNetState.CONNECTION_ESTABLISHED)
//                            }
//                        }
//                    } catch (e: ApiError) {
//                        if (e.status == 404) {
//                            Handler(Looper.getMainLooper()).post {
//                                callBack(AppNetState.THIS_USER_NOT_REGISTERED)
//                            }
//                        }
//                        if (e.status == 400) {
//                            Handler(Looper.getMainLooper()).post {
//                                callBack(AppNetState.INCORRECT_PASSWORD)
//                            }
//                        }
//                        if (e.status == 500) {
//                            Handler(Looper.getMainLooper()).post {
//                                callBack(AppNetState.SERVER_ERROR_500)
//                            }
//                        }
//                    } catch (e: Exception) {
//                        Log.e("exc", " error ${e.javaClass.simpleName}")
//                    }
//                }
//
//                AppNetState.NO_INTERNET -> {
//                    Handler(Looper.getMainLooper()).post {
//                        callBack(AppNetState.NO_INTERNET)
//                    }
//                }
//
//                AppNetState.NO_SERVER_CONNECTION -> {
//                    Handler(Looper.getMainLooper()).post {
//                        callBack(AppNetState.NO_SERVER_CONNECTION)
//                    }
//                }
//
//            }
//        }
//
//    //-------
//
//
//    @Synchronized
//    private fun setAuth(id: Long, token: String) {
//        val charset: Charset = StandardCharsets.US_ASCII
//        val byteArrray: ByteArray = charset.encode(token).array()
//        val result = byteArrray.joinToString()
//
//        sessionKey = token
//
//        _authStateFlow.value = AuthState(id, token)
//        with(prefs.edit()) {
//            putLong(idKey, id)
//            putString(tokenKey, result)
//            apply()
//        }
//    }
//
//    fun getAuthInfo(): Pair<Long, String?> {
//        val charset: Charset = StandardCharsets.US_ASCII
//        val s = prefs.getString(tokenKey, null)?.split(", ")?.map {it.toByte()}?.toByteArray()
//        val result3 = String(s ?: byteArrayOf(), charset)
//
//        sessionKey = result3
//
//        return prefs.getLong(idKey, 0) to result3
//
//    }
//
//    @Synchronized
//    fun removeAuth() {
//        _authStateFlow.value = AuthState()
//        sessionKey = null
//        with(prefs.edit()) {
//            clear()
//            apply()
//        }
//    }
//
//}
//
