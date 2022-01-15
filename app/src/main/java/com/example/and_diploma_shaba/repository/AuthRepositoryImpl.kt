package com.example.and_diploma_shaba.repository

import androidx.lifecycle.Transformations
import com.example.and_diploma_shaba.dao.AuthDao
import com.example.and_diploma_shaba.dao.UserDao
import com.example.and_diploma_shaba.dto.User
import com.example.and_diploma_shaba.entity.UserEntity
import org.w3c.dom.Entity


class AuthRepositoryImpl(
    private val dao: AuthDao
    ): AuthRepository {


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

    override fun addUser(user: User) {
        dao.insert(UserEntity.fromDto(user))
    }

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
