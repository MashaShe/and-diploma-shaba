package com.example.and_diploma_shaba.util

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.and_diploma_shaba.repository.AuthRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@HiltWorker
class RefreshPostsWorker @AssistedInject constructor(
    @Assisted  applicationContext: Context,
    @Assisted  params: WorkerParameters,
    var repository: AuthRepository
    ) : CoroutineWorker(applicationContext, params) {
    companion object {
        const val name = "RefreshPostsWorker"
    }


    override suspend fun doWork(): Result = withContext(Dispatchers.Default) {
        try {
            repository.getAllPosts()
            repository.getAllUsers()
            repository.getAllEvents()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}