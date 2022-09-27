package com.example.and_diploma_shaba.dto


import com.google.gson.annotations.SerializedName

data class Coords(
    @SerializedName("lat")
    val latitude: Float?,
    @SerializedName("long")
    val longitude : Float?
)