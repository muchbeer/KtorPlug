package com.muchbeer.ktorplug.repository

import com.muchbeer.ktorplug.data.db.datasource.LocalPostDatasource
import com.muchbeer.ktorplug.data.DataState
import com.muchbeer.ktorplug.data.db.*
import com.muchbeer.ktorplug.data.remote.datasource.RemoteDataSource
import com.muchbeer.ktorplug.data.remote.sampledto.ImageResponseDto
import com.muchbeer.ktorplug.data.remote.sampledto.PostRequestDto
import com.muchbeer.ktorplug.data.remote.sampledto.PostResponseDto
import com.muchbeer.ktorplug.utility.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File

class PostRepositoryImpl(
    private val remoteDS : RemoteDataSource,
    private val postDS : LocalPostDatasource
    ) : PostRepository {

    override fun getPostFromGeneric(): Flow<DataState<List<PostResponseDto>>> {
        return remoteDS.getPostFromGeneric()     }

    override fun createPost(postRequest: PostRequestDto): Flow<DataState<PostResponseDto?>> {
        return remoteDS.createPost(postRequest)     }

    override fun createPostFromGeneric(postRequest: PostRequestDto): Flow<DataState<PostResponseDto?>> {
        return remoteDS.createPostFromGeneric(postRequest)     }

    override fun uploadImage(filePath: File): Flow<DataState<ImageResponseDto>> {
     return   remoteDS.uploadImage(filePath)    }

    override fun workManagerValues() {
     val randomNumber = (10..100).random()
    logs(TAG, "The random number is : $randomNumber")}

    override suspend fun insertToDb(posts: List<CgrievanceEntity>) {
        postDS.insertPost(posts)     }

    override fun retrieveFromDb(search: String): Flow<DataState<List<CgrievanceEntity>>> = flow{

        emit(DataState.Loading)
        try {
            postDS.retrieveSearchPosts(search).collect { posts ->
                emit(DataState.Success(posts))  }
        } catch (e : Exception) {
            emit(DataState.Error(e.message.toString()))
        }
    }

    override fun retrieveAllAgrievanceEntry(): Flow<List<AgrievanceEntity>> {
      return postDS.retrieveAllAgrievanceEntry()     }

    override fun retrieveAllBpapsEntry(): Flow<List<BpapDetailEntity>> {
       return postDS.retrieveAllBpapsEntry()    }

    override fun retrieveAllCGrievanceEntry(): Flow<List<CgrievTotalEntity>> {
        return postDS.retrieveAllCGrievanceEntry()     }

    override fun retrieveAllDpapsEntry(): Flow<List<DpapAttachEntity>> {
       return postDS.retrieveAllDpapsEntry()     }

    override fun retrieveAllBGrievWithUsername(username: String): Flow<List<BpapDetailEntity>> {
       return postDS.retrieveAllBGrievWithUsername(username)     }

    override fun retrieveAllCGrievanceWithUsername(username: String): Flow<List<CgrievTotalEntity>> {
       return postDS.retrieveAllCGrievanceWithUsername(username)     }

    override fun retrieveDAddAttachWithfullName(fullName: String): Flow<List<DpapAttachEntity>> {
        return postDS.retrieveDAddAttachWithfullName(fullName)     }

    override fun retrieveAllDAttachByStatus(uploadStatus: IMAGESTATUS): Flow<List<DpapAttachEntity>> {
       return postDS.retrieveAllDAttachByStatus(uploadStatus)     }

    override suspend fun insertAgrievEntry(agrienceModel: AgrievanceEntity): Long {
       return postDS.insertAgrievEntry(agrienceModel)     }

    override suspend fun insertBpapDetail(bpapsDetail: BpapDetailEntity): Long {
        return postDS.insertBpapDetail(bpapsDetail)     }

    override suspend fun insertCgrievDetail(cgriev: CgrievTotalEntity): Long {
       return postDS.insertCgrievDetail(cgriev)     }

    override suspend fun insertDattach(dattach: DpapAttachEntity): Long {
        return postDS.insertDattach(dattach)     }

    override suspend fun updateCgrievance(cgriev: CgrievTotalEntity) {
        postDS.updateCgrievance(cgriev)
    }

    override suspend fun updateDattachment(dattach: DpapAttachEntity) {
      postDS.updateDattachment(dattach)     }

    companion object {
        private val TAG = PostRepositoryImpl::class.simpleName.toString()
    }
}

