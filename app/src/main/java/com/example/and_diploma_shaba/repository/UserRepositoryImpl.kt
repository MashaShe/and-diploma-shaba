package com.example.and_diploma_shaba.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.and_diploma_shaba.dao.PostDao
import com.example.and_diploma_shaba.dao.UserDao
import com.example.and_diploma_shaba.dto.Events
import com.example.and_diploma_shaba.dto.Post
import com.example.and_diploma_shaba.dto.User
import com.example.and_diploma_shaba.entity.PostEntity
import com.example.and_diploma_shaba.entity.UserEntity

//TODO НЕ ОБНОВЛЕН под юзера

class UserRepositoryImpl(
    private val dao: UserDao,
) : UserRepository {

    override fun userPassById(id:Long): String {
        TODO("Not yet implemented")
    }

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
        dao.addUser(UserEntity.fromDto(user))
    }

}