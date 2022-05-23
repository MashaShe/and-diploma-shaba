package com.example.and_diploma_shaba.repository
//
//import android.content.Context
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.Transformations
//import androidx.paging.*
//import com.example.and_diploma_shaba.adapter.UserRemoteMediator
//import com.example.and_diploma_shaba.api.ApiError
//import com.example.and_diploma_shaba.api.ApiService
//import com.example.and_diploma_shaba.dao.UserDao
//import com.example.and_diploma_shaba.db.AppDb
//import com.example.and_diploma_shaba.dto.User
//import com.example.and_diploma_shaba.entity.UserEntity
//import com.example.and_diploma_shaba.entity.toEntity
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.map
//import okhttp3.OkHttpClient
//import androidx.paging.PagingData
//
//
////TODO НЕ ОБНОВЛЕН под юзера
//
//class UserRepositoryImpl(
//    private val dao: UserDao,
//    private val base: AppDb,
//    // private val dao: AuthDao,
//    private val api: ApiService,
//    private val context: Context,
//    private val client : OkHttpClient,
//    private val authMethods: AuthMethods
//) : UserRepository {
//
//
////    override fun getAllUsers() = Transformations.map(dao.getAllUsers()) { list ->
////        list.map {
////            User(
////                it.userId,
////                it.userLogin,
////                it.userPass,
////                it.userAvatar,
////                it.userFirstName,
////                it.userLastName//,
////              //  it.userBirthDate,
////               // it.userAuthorities
////
////            )
////        }
////    }
//
//
//    override fun findByLogin(login: String?) =  dao.findByLogin(login)
//
//    override fun addUser(user: User) {
//        dao.insert(UserEntity.fromDto(user))
//    }
//
//    @ExperimentalPagingApi
//    override val udata: Flow<PagingData<User>> = Pager(
//        remoteMediator = UserRemoteMediator(api, base, this),
//        config = PagingConfig(pageSize = 5, enablePlaceholders = false),
//        pagingSourceFactory = base.userDao().getAll()
//    ).flow.map {
//        it.map(UserEntity::toDto)
//    }
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
//
//    override fun findMe(login: String): User {
//        return  dao.findMe(login).toDto()
//    }
//    override fun userPassById(id: Long): String {
//        return dao.userPassById(id)
//    }
//    override fun userLoginById(id: Long): String {
//        return dao.userLoginById(id)
//    }
//
//    override fun getUserById(id: Long): LiveData<List<UserEntity>> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun getAllUsersFromDB(): List<UserEntity> {
//        return base.userDao().getAllUsers()
//    }
//
//    override fun getAllUsers(): LiveData<List<User>> {
//       // tryCatchWrapper(object {}.javaClass.enclosingMethod.name) {
//            val response = api.getAllUsers()
//            if (!response.isSuccessful) {
//                throw ApiError(response.code(), response.message())
//            }
//            val body = response.body() ?: throw ApiError(response.code(), response.message())
//            base.userDao().insert(body.toEntity())
//       // }
//    }
//}