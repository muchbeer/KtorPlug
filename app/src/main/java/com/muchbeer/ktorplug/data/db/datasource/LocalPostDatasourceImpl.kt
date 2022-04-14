package com.muchbeer.ktorplug.data.db.datasource

import com.muchbeer.ktorplug.data.db.*
import kotlinx.coroutines.flow.Flow

class LocalPostDatasourceImpl(private val grievDao : GrievanceDao) : LocalPostDatasource {
    override suspend fun insertPost(posts: List<CgrievanceEntity>) {
        grievDao.insertCgriev(posts)      }

    override fun retrieveSearchPosts(search: String): Flow<List<CgrievanceEntity>> {
        return grievDao.retrieveAllSearchedPost(search)     }

    override fun retrieveAll(): Flow<List<CgrievanceEntity>> {
        return grievDao.retrieveAll()
    }

    override suspend fun insertAgrievEntry(agrienceModel: AgrievanceEntity): Long {
     return  grievDao.insertAGrievance(agrienceModel)     }

    override suspend fun insertBpapDetail(bpapsDetail: BpapDetailEntity): Long {
       return grievDao.insertBpapDetailEntry(bpapsDetail)     }

    override suspend fun insertCgrievDetail(cgriev: CgrievTotalEntity): Long {
        return grievDao.insertCGrievanceEntry(cgriev)    }

    override suspend fun insertDattach(dattach: DpapAttachEntity): Long {
        return grievDao.insertDAttachEntry(dattach)     }

    override suspend fun updateCgrievance(cgriev: CgrievTotalEntity) {
        grievDao.updateCgrievance(cgriev)     }

    override suspend fun updateDattachment(dattach: DpapAttachEntity) {
        grievDao.updateDAttachment(dattach)
    }

    override fun retrieveAllAgrievanceEntry(): Flow<List<AgrievanceEntity>> {
        return grievDao.retrieveAgrievanceEntry()
    }

    override fun retrieveAllBpapsEntry(): Flow<List<BpapDetailEntity>> {
        return grievDao.retrieveAllBPaps()
    }

    override fun retrieveAllCGrievanceEntry(): Flow<List<CgrievTotalEntity>> {
        return grievDao.retrieveCGrievance()     }

    override fun retrieveAllDpapsEntry(): Flow<List<DpapAttachEntity>> {
        return grievDao.retrieveAllDAttachment()
    }

    override fun retrieveAllBGrievWithUsername(username: String): Flow<List<BpapDetailEntity>> {
       return grievDao.getAllBpapsWithEachUsername(username)     }

    override fun retrieveAllCGrievanceWithUsername(username: String): Flow<List<CgrievTotalEntity>> {
        return grievDao.getAllGrieveWithPaps(username)     }

    override fun retrieveDAddAttachWithfullName(fullName: String): Flow<List<DpapAttachEntity>> {
       return grievDao.getAllDpapsAttachmentInCGrievence(fullName)     }

    override fun retrieveAllDAttachByStatus(uploadStatus: IMAGESTATUS): Flow<List<DpapAttachEntity>> {
       return grievDao.getAllDAttachByStatus(uploadStatus)
    }


}