package com.example.and_diploma_shaba.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.example.and_diploma_shaba.auth.AppAuth
import com.example.and_diploma_shaba.repository.AppEntities
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
//import com.example.and_diploma_shaba.activity.utils.prepareFileName
import com.example.and_diploma_shaba.dto.Event
import java.io.File
import javax.inject.Inject


@HiltViewModel
@ExperimentalCoroutinesApi
class MediaWorkEventViewModel @Inject constructor(
    var repository: AppEntities,
    var workManager: WorkManager,
    var auth: AppAuth,
    var context: Application
) : AndroidViewModel(context) {

    private val files = HashMap<Long, File>()


    fun downloadMedia(event: Event) {
//        if (event.attachment == null) {
//            return
//        }
//
//        val outputFile = File(context.cacheDir, event.attachment.prepareFileName())
//        outputFile.setReadable(true, false)
//
//        files[event.id] = outputFile
//
//        if (outputFile.exists()) {
//            viewModelScope.launch {
//                repository.updateEvent(event.copy(downloadingProgress = 100))
//            }
//            return
//        }
//
//
//        repository.download(event.attachment.url, outputFile,
//            object : CallbackR<Byte> {
//                override fun onSuccess(progress: Byte) {
//                    viewModelScope.launch {
//                        repository.updateEvent(event.copy(downloadingProgress = progress))
//                    }
//                }
//
//                override fun onError(e: Exception) {
//                    Log.e("exc", "error")
//                }
//            }
//        )

    }

    fun openMedia(id: Long, play: (file: File) -> Unit) {
        files[id]?.let { play(it) }
    }


    fun deleteFile(event: Event) {
//        files.remove(event.id)
//        if (event.attachment == null)
//            return
//
//        val outputFile = File(context.cacheDir, event.attachment.prepareFileName())
//        outputFile.setReadable(true, false)
//
//        if (outputFile.exists()) {
//            outputFile.delete()
//        }
    }
}





