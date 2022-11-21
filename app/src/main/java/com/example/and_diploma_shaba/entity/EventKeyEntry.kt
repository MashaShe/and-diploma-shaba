package com.example.and_diploma_shaba.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EventKeyEntry(
    @PrimaryKey
    val type: Type,
    val id: Long
)
{
    enum class Type {
        PREPEND,
        APPEND
    }

}