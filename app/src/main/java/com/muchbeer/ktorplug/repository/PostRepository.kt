package com.muchbeer.ktorplug.repository

import com.muchbeer.ktorplug.data.BackState
import com.muchbeer.ktorplug.data.DataState
import com.muchbeer.ktorplug.data.db.*
import com.muchbeer.ktorplug.data.remote.sampledto.ImageResponseDto
import com.muchbeer.ktorplug.data.remote.sampledto.PostRequestDto
import com.muchbeer.ktorplug.data.remote.sampledto.PostResponseDto
import kotlinx.coroutines.flow.Flow
import java.io.File

interface PostRepository {

      fun getPostFromGeneric() : Flow<BackState<List<PostResponseDto>>>

     fun createPost(postRequest : PostRequestDto) : Flow<BackState<PostResponseDto?>>

      fun createPostFromGeneric(postRequest : PostRequestDto) : Flow<BackState<PostResponseDto?>>

     suspend fun workManagerUploads()

      suspend fun insertToDb(posts : List<CgrievanceEntity>)

      fun retrieveFromDb(search : String) : Flow<DataState<List<CgrievanceEntity>>>

    fun retrieveAllAgrievanceEntry() : Flow<List<AgrievanceEntity>>

    fun retrieveAllBpapsEntry() : Flow<List<BpapDetailEntity>>

    fun retrieveAllCGrievanceEntry() : Flow<List<CgrievTotalEntity>>

    fun retrieveAllDpapsEntry() : Flow<List<DpapAttachEntity>>

    fun retrieveAllBGrievWithUsername(username: String) : Flow<List<BpapDetailEntity>>

    fun retrieveAllCGrievanceWithUsername(username : String) : Flow<List<CgrievTotalEntity>>

    fun retrieveDAddAttachWithfullName(fullName: String) : Flow<List<DpapAttachEntity>>

  //  fun retrieveAllDAttachmentUploads() : Flow<List<DpapAttachEntity>>

    suspend fun insertAgrievEntry(agrienceModel: AgrievanceEntity) : Long

    suspend fun insertBpapDetail(bpapsDetail : BpapDetailEntity) : Long

    suspend fun insertCgrievDetail(cgriev : CgrievTotalEntity) : Long

    suspend fun insertDattach(dattach : DpapAttachEntity) : Long

    suspend fun updateCgrievance(cgriev: CgrievTotalEntity)

    suspend fun updateDattachment(dattach: DpapAttachEntity)

    //**********************ALTERNATIVE*****************************
    suspend fun insertAgrievEntryAlt(agrienceModel: AgrievanceAltEntity) : Long

    suspend fun insertCgrievDetailAlt(cgriev : CgrievTotalAltEntity) : Long

    suspend fun insertDattachAlt(dattach : DpapAttachAltEntity) : Long

    suspend fun updateCgrievanceAlt(cgriev: CgrievTotalAltEntity)

    suspend fun updateDattachmentAlt(dattach: DpapAttachAltEntity)

    fun retrieveDattachByStatusAlt(uploadStatus : IMAGESTATUS) : Flow<List<DpapAttachAltEntity>>

    fun retrieveCgrievanceAlt() : Flow<List<CgrievTotalAltEntity>>

    fun retrieveAgrieveWithCgrievAndDattach() : Flow<List<AgrievWithCgrieAndDattachList>>

}