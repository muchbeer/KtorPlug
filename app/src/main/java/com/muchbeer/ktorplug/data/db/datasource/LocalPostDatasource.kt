package com.muchbeer.ktorplug.data.db.datasource

import com.muchbeer.ktorplug.data.db.*
import kotlinx.coroutines.flow.Flow
import java.nio.channels.FileLock

interface LocalPostDatasource {
    suspend fun insertPost(posts : List<CgrievanceEntity>)
    fun retrieveSearchPosts(search : String) : Flow<List<CgrievanceEntity>>
    fun retrieveAll() : Flow<List<CgrievanceEntity>>

    suspend fun insertAgrievEntry(agrienceModel: AgrievanceEntity) : Long

    suspend fun insertBpapDetail(bpapsDetail : BpapDetailEntity) : Long

    suspend fun insertCgrievDetail(cgriev : CgrievTotalEntity) : Long

    suspend fun insertDattach(dattach : DpapAttachEntity) : Long

    suspend fun updateCgrievance(cgriev: CgrievTotalEntity)

    suspend fun updateDattachment(dattach: DpapAttachEntity)

    fun retrieveAllAgrievanceEntry() : Flow<List<AgrievanceEntity>>

    fun retrieveAllBpapsEntry() : Flow<List<BpapDetailEntity>>

    fun retrieveAllCGrievanceEntry() : Flow<List<CgrievTotalEntity>>

    fun retrieveAllDpapsEntry() : Flow<List<DpapAttachEntity>>

    fun retrieveAllBGrievWithUsername(username: String) : Flow<List<BpapDetailEntity>>

    fun retrieveAllCGrievanceWithUsername(username : String) : Flow<List<CgrievTotalEntity>>

    fun retrieveAllDAddAttachWithfullName(fullName: String) : Flow<List<DpapAttachEntity>>

    fun retrieveAllDAttachByStatus(uploadStatus : IMAGESTATUS) : Flow<List<DpapAttachEntity>>

}

interface LocalPostDatasourceAlt {
    suspend fun insertAgrievEntryAlt(agrienceModel: AgrievanceAltEntity) : Long

    suspend fun insertCgrievDetailAlt(cgriev : CgrievTotalAltEntity) : Long

    suspend fun insertDattachAlt(dattach : DpapAttachAltEntity) : Long

    suspend fun updateCgrievanceAlt(cgriev: CgrievTotalAltEntity)

    suspend fun updateDattachmentAlt(dattach: DpapAttachAltEntity)

    fun retrieveAllAgrievanceEntryAlt() : Flow<List<AgrievanceAltEntity>>

    fun retrieveAllCGrievanceEntryAlt() : Flow<List<CgrievTotalAltEntity>>

    fun retrieveAllDpapsEntryAlt() : Flow<List<DpapAttachAltEntity>>

    fun retrieveAllCGrievanceWithUsernameAlt(username : String) : Flow<List<CgrievTotalAltEntity>>

    fun retrieveAllDAddAttachWithfullNameAlt(fullName: String) : Flow<List<DpapAttachAltEntity>>

    fun retrieveAllDAttachByStatusAlt(uploadStatus : IMAGESTATUS) : Flow<List<DpapAttachAltEntity>>

    fun retrievAgrievWithCgrievAndDattach() : Flow<List<AgrievWithCgrieAndDattachList>>

}