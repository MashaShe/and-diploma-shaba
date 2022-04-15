package com.example.and_diploma_shaba.db

import android.content.Context
import androidx.room.*
import com.example.and_diploma_shaba.dao.*
import com.example.and_diploma_shaba.entity.EventEntity
import com.example.and_diploma_shaba.entity.PostEntity
import com.example.and_diploma_shaba.entity.UserEntity

@Database(entities = [PostEntity::class, UserEntity::class, EventEntity::class], version = 5, exportSchema = false)
abstract class AppDb : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun userDao(): UserDao
    abstract fun authDao(): AuthDao
    abstract fun eventDao(): EventDao
    abstract fun keyUserPaginationDao(): UserPaginationKeyDao

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