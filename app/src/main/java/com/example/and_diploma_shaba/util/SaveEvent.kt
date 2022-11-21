package com.example.and_diploma_shaba.util

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.and_diploma_shaba.repository.AppEntities
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject



@HiltWorker
class SaveEventWorker  @AssistedInject constructor(
    @Assisted  applicationContext: Context,
    @Assisted  params: WorkerParameters,
    var repository: AppEntities
) : CoroutineWorker(applicationContext, params) {

    companion object {
        const val postKey = "event"
    }

    override suspend fun doWork(): Result {
        val task = inputData.getStringArray(postKey)
            ?: return Result.failure()

        return try {
            repository.processEventWork(task)
            Result.success()

        } catch (e: Exception) {
            Result.retry()
        } catch (e: UnknownError) {
            Result.failure()
        }


    }
}
