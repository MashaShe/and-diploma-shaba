package com.example.and_diploma_shaba.repository

import androidx.lifecycle.Transformations
import com.example.and_diploma_shaba.dao.UserDao
import com.example.and_diploma_shaba.dto.User
import com.example.and_diploma_shaba.entity.UserEntity

//TODO НЕ ОБНОВЛЕН под юзера

class UserRepositoryImpl(
    private val dao: UserDao,
) : UserRepository {


    override fun getAllUsers() = Transformations.map(dao.getAllUsers()) { list ->
        list.map {
            User(
                it.userId,
                it.userLogin,
                it.userPass,
                it.userAvatar,
                it.userFirstName,
                it.userLastName//,
              //  it.userBirthDate,
               // it.userAuthorities

            )
        }
    }

    override fun findByLogin(login: String?) =  dao.findByLogin(login)

    override fun addUser(user: User) {
        dao.insert(UserEntity.fromDto(user))
    }

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

    override fun findMe(login: String): User {
        return  dao.findMe(login).toDto()
    }
    override fun userPassById(id: Long): String {
        return dao.userPassById(id)
    }
    override fun userLoginById(id: Long): String {
        return dao.userLoginById(id)
    }

    override fun userIdByLogin(login: String): Long {
        return dao.userIdByLogin(login)
    }

}