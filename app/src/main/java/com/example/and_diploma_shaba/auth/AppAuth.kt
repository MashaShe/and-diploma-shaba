package com.example.and_diploma_shaba.auth

import android.content.Context
import android.content.SharedPreferences
import com.example.and_diploma_shaba.api.ApiService
import kotlinx.coroutines.flow.*
import com.example.and_diploma_shaba.repository.*
import javax.inject.Inject

class AppAuth @Inject constructor(
    val context: Context,
    val apiService: ApiService,
    var repository: AuthRepository,
    var prefs: SharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
    ) {
    //private val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
    private val idKey = "id"
    private val tokenKey = "token"
    private val _authStateFlow: MutableStateFlow<AuthState>

    init {
        val id = prefs.getLong(idKey, 0)
        val token = prefs.getString(tokenKey, null)

        if (id == 0L || token == null) {
            _authStateFlow = MutableStateFlow(AuthState())
            with(prefs.edit()) {
                clear()
                apply()
            }
        } else {
            _authStateFlow = MutableStateFlow(AuthState(id, token))
        }
    }

    val authStateFlow: StateFlow<AuthState> = _authStateFlow.asStateFlow()

    @Synchronized
    fun setAuth(id: Long, token: String) {
        _authStateFlow.value = AuthState(id, token)
        with(prefs.edit()) {
            putLong(idKey, id)
            putString(tokenKey, token)
            apply()
        }
    }

    @Synchronized
    fun removeAuth() {
        _authStateFlow.value = AuthState()
        with(prefs.edit()) {
            clear()
            commit()
        }
    }

//    companion object {
//        @Volatile
//        private var instance: AppAuth? = null
//
//        fun getInstance(): AppAuth = synchronized(this) {
//            instance ?: throw IllegalStateException(
//                "AppAuth is not initialized, you must call AppAuth.initializeApp(Context context) first."
//            )
//        }
//
//        fun initApp(context: Context): AppAuth = instance ?: synchronized(this) {
//            instance ?: buildAuth(context).also { instance = it }
//        }
//
//        private fun buildAuth(context: Context): AppAuth = AppAuth(context)
//    }

    companion object {
        const val idKey = "id"
        const val tokenKey = "token"
        var sessionKey : String? = null
    }



     fun authUser(login: String, pass: String) { //suspend
            repository.authUser(login, pass) { id, token ->
                setAuth(id, token)
            }
    }


}

data class AuthState(val id: Long = 0, val token: String? = null)





