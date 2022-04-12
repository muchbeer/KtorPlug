package com.muchbeer.ktorplug.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.muchbeer.ktorplug.repository.PostRepository
import com.muchbeer.ktorplug.utility.logs
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ServerWorker  @AssistedInject constructor (@Assisted val appContext: Context,
                                                 @Assisted workerParams: WorkerParameters,
                                                 private val repository: PostRepository) :
            CoroutineWorker(appContext, workerParams){

    override suspend fun doWork(): Result {
        return try {
                repository.workManagerValues()
            Result.success()
            }
            catch (e: Exception) {
                logs(TAG, "exception in doWork ${e.message}")
                Result.retry()
            }

     catch (e: Exception) {
        logs(TAG, "exception in doWork ${e.message}")
        Result.failure()
       }
    }

    companion object {
        private val TAG = ServerWorker::class.simpleName.toString()
    }
}