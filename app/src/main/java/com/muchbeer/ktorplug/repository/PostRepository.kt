package com.muchbeer.ktorplug.repository

import com.muchbeer.ktorplug.data.DataState
import com.muchbeer.ktorplug.data.db.*
import com.muchbeer.ktorplug.data.remote.sampledto.ImageResponseDto
import com.muchbeer.ktorplug.data.remote.sampledto.PostRequestDto
import com.muchbeer.ktorplug.data.remote.sampledto.PostResponseDto
import kotlinx.coroutines.flow.Flow
import java.io.File

interface PostRepository {

      fun getPostFromGeneric() : Flow<DataState<List<PostResponseDto>>>
     fun createPost(postRequest : PostRequestDto) : Flow<DataState<PostResponseDto?>>
      fun createPostFromGeneric(postRequest : PostRequestDto) : Flow<DataState<PostResponseDto?>>
      fun uploadImage(filePath : File) : Flow<DataState<ImageResponseDto>>
      fun workManagerValues()
      suspend fun insertToDb(posts : List<CgrievanceEntity>)
      fun retrieveFromDb(search : String) : Flow<DataState<List<CgrievanceEntity>>>

    fun retrieveAllAgrievanceEntry() : Flow<List<AgrievanceEntity>>

    fun retrieveAllBpapsEntry() : Flow<List<BpapDetailEntity>>

    fun retrieveAllCGrievanceEntry() : Flow<List<CgrievTotalEntity>>

    fun retrieveAllDpapsEntry() : Flow<List<DpapAttachEntity>>

    fun retrieveAllBGrievWithUsername(username: String) : Flow<List<BpapDetailEntity>>

    fun retrieveAllCGrievanceWithUsername(username : String) : Flow<List<CgrievTotalEntity>>

    fun retrieveDAddAttachWithfullName(fullName: String) : Flow<List<DpapAttachEntity>>

    fun retrieveAllDAttachByStatus(uploadStatus : IMAGESTATUS) : Flow<List<DpapAttachEntity>>

  //  fun retrieveAllDAttachmentUploads() : Flow<List<DpapAttachEntity>>

    suspend fun insertAgrievEntry(agrienceModel: AgrievanceEntity) : Long

    suspend fun insertBpapDetail(bpapsDetail : BpapDetailEntity) : Long

    suspend fun insertCgrievDetail(cgriev : CgrievTotalEntity) : Long

    suspend fun insertDattach(dattach : DpapAttachEntity) : Long

    suspend fun updateCgrievance(cgriev: CgrievTotalEntity)

    suspend fun updateDattachment(dattach: DpapAttachEntity)
}