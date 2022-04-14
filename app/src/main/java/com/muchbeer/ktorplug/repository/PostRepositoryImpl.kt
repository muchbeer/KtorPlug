package com.muchbeer.ktorplug.repository

import com.muchbeer.ktorplug.data.db.datasource.LocalPostDatasource
import com.muchbeer.ktorplug.data.DataState
import com.muchbeer.ktorplug.data.db.CgrievanceEntity
import com.muchbeer.ktorplug.data.remote.datasource.RemoteDataSource
import com.muchbeer.ktorplug.data.remote.sampledto.ImageResponseDto
import com.muchbeer.ktorplug.data.remote.sampledto.PostRequestDto
import com.muchbeer.ktorplug.data.remote.sampledto.PostResponseDto
import com.muchbeer.ktorplug.utility.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.IOException

class PostRepositoryImpl(
    private val remoteDS : RemoteDataSource,
    private val postDS : LocalPostDatasource
    ) : PostRepository {

    override fun getPostFromGeneric(): Flow<DataState<List<PostResponseDto>>> {
/*       networkBoundResource(
            fetch = {
                remoteDS.getPostFromGeneric()      },
            saveFetchResult = { dataState->
                dataState.collect { collectState ->
                    when (collectState) {
                        is DataState.Error -> logs(TAG, "error is : ${collectState.error}")
                        is DataState.ErrorException -> logs(TAG, "error is : ${collectState.exception.message}")
                        DataState.Loading -> logs(TAG, "loading")
                        is DataState.Success -> {
                            val dataEntity = Mapper().toCgrievEntityList(collectState.data)
                            postDS.insertPost(dataEntity)
                            logs(TAG, "The values are entered corrected")
                        }
                    }.exhaustive
                }
            }

        )*/
        return remoteDS.getPostFromGeneric()
    }

    override fun createPost(postRequest: PostRequestDto): Flow<DataState<PostResponseDto?>> {
        return remoteDS.createPost(postRequest)
    }

    override fun createPostFromGeneric(postRequest: PostRequestDto): Flow<DataState<PostResponseDto?>> {
        return remoteDS.createPostFromGeneric(postRequest)
    }

    override fun uploadImage(filePath: File): Flow<DataState<ImageResponseDto>> {
     return   remoteDS.uploadImage(filePath)    }

    override fun workManagerValues() {
     val randomNumber = (10..100).random()
    logs(TAG, "The random number is : $randomNumber")}

    override suspend fun insertToDb(posts: List<CgrievanceEntity>) {
        postDS.insertPost(posts)
    }

    override fun retrieveFromDb(search: String): Flow<DataState<List<CgrievanceEntity>>> = flow{

        emit(DataState.Loading)
        try {

            postDS.retrieveSearchPosts(search).collect { posts ->
                emit(DataState.Success(posts))  }

        } catch (e : Exception) {
            emit(DataState.Error(e.message.toString()))
        }
    }

    companion object {
        private val TAG = PostRepositoryImpl::class.simpleName.toString()
    }
}

