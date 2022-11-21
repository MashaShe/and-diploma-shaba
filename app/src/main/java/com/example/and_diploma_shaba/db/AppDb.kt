package com.example.and_diploma_shaba.db

import android.content.Context
import androidx.room.*
import com.example.and_diploma_shaba.dao.*
import com.example.and_diploma_shaba.entity.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

@Database(entities = [UserEntity::class,UserKeyEntry::class,
    PostEntity::class,PostKeyEntry::class, PostWorkEntity::class,
    EventEntity::class, EventKeyEntry::class, EventWorkEntity::class
                     ], version = 24, exportSchema = false)

@TypeConverters(Converters::class)

abstract class AppDb : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun userDao(): UserDao
    abstract fun authDao(): AuthDao
    abstract fun eventDao(): EventDao

    abstract fun postWorkDao(): PostWorkDao
    abstract fun eventWorkDao() : EventWorkDao


    abstract fun keyUserPaginationDao(): UserPaginationKeyDao
    abstract fun keyPostPaginationDao(): PostPaginationKeyDao
    abstract fun keyEventPaginationDao(): EventPaginationKeyDao


    companion object {
        @Volatile
        private var instance: AppDb? = null

        fun getInstance(context: Context): AppDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDb::class.java, "app.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
    }
}

class Converters {

    @TypeConverter
    fun listToJson(value: List<String>?) = Gson().toJson(value)

    @TypeConverter
    fun fromString(value: String?): ArrayList<String?>? {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<String?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }


    @TypeConverter
    fun toLongList(value: String?): ArrayList<Long>? {
        val listType: Type = object : TypeToken<ArrayList<Long>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toStringFromLong(list: List<Long>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

}