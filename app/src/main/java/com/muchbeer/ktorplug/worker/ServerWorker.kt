package com.muchbeer.ktorplug.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.muchbeer.ktorplug.data.BackState
import com.muchbeer.ktorplug.data.DataState
import com.muchbeer.ktorplug.data.db.IMAGESTATUS
import com.muchbeer.ktorplug.data.db.Mapper
import com.muchbeer.ktorplug.repository.PostRepository
import com.muchbeer.ktorplug.utility.exhaustive
import com.muchbeer.ktorplug.utility.logs
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collect
import java.io.File

@HiltWorker
class ServerWorker  @AssistedInject constructor (@Assisted val appContext: Context,
                                                 @Assisted workerParams: WorkerParameters,
                                                 private val repository: PostRepository) :
            CoroutineWorker(appContext, workerParams){

    override suspend fun doWork(): Result {
        return try {

                repository.getPostFromGeneric().collect {dataState ->
                when(dataState) {
                    is BackState.Error ->logs(TAG, "errorOne")
                    is BackState.Success ->  {
                        val dataEntity = Mapper().toCgrievEntityList(dataState.data)
                        repository.insertToDb(dataEntity)
                        logs(TAG,"tHE entered value is successful")
                    }
                }.exhaustive
            }

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