package com.example.and_diploma_shaba.dto

import com.google.gson.annotations.SerializedName
import java.util.*

data class User(
    @SerializedName("id")
    val userId: Long, //=0
    @SerializedName("login")
    val userLogin: String ="Тест Тестович",
  //  @SerializedName("password")
   // val userPass: String = "123",
    @SerializedName("avatar")
    val userAvatar: String? = null,
    @SerializedName("name")
    val userFirstName: String = "Иван",
    //val userLastName:String = "Иванов",
    val userBirthDate: String? = null,
   // val userAuthorities: List<String>? = null

)
