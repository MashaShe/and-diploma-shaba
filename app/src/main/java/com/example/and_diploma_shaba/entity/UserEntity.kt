package com.example.and_diploma_shaba.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.and_diploma_shaba.dto.User
import java.util.*

@Entity
data class UserEntity(

    @PrimaryKey(autoGenerate = true)
    val userId: Long,
    val userLogin: String,
    //val userPass: String,
    val userAvatar: String? = null,
    val userFirstName: String,
   // val userLastName:String//,
    //val userBirthDate: Date? = null,
    //val userAuthorities: List<String>? = null

    //TODO: в DAO описать работу со всеми атрибутами поста и дргиух классов
) {
    fun toDto() = User(
        userId,
        userLogin,
       // userPass,
        userAvatar,
        userFirstName,
      //  userLastName//,
       // userBirthDate,
       // userAuthorities
    )

    companion object {
        fun fromDto(dto: User) =
            UserEntity(
                dto.userId,
                dto.userLogin,
            //    dto.userPass,
                dto.userAvatar,
                dto.userFirstName,
              //  dto.userLastName//,
                //dto.userBirthDate,
               // dto.userAuthorities
    )

    }
}

fun List<UserEntity>.toDto(): List<User> = map(UserEntity::toDto)
fun List<User>.toEntity(): List<UserEntity> = map(UserEntity::fromDto)