package com.example.and_diploma_shaba.dto

import java.util.*

data class User(
    val userId: Long = 0,
    val userLogin: String,
    val userPass: String, //TODO add firebase authentication
    val userAvatar: String? = null,
    val userFirstName: String = "Иван",
    val userLastName:String = "Иванов",
    val userBirthDate: String? = null,
   // val userAuthorities: List<String>? = null

)
