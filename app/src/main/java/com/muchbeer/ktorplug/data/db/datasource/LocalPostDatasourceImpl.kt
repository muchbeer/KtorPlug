package com.muchbeer.ktorplug.data.db.datasource

import com.muchbeer.ktorplug.data.db.CgrievanceEntity
import com.muchbeer.ktorplug.data.db.GrievanceDao
import kotlinx.coroutines.flow.Flow

class LocalPostDatasourceImpl(private val grievDao : GrievanceDao) : LocalPostDatasource {
    override suspend fun insertPost(posts: List<CgrievanceEntity>) {
        grievDao.insertCgriev(posts)      }

    override fun retrieveSearchPosts(search: String): Flow<List<CgrievanceEntity>> {
        return grievDao.retrieveAllSearchedPost(search)     }

    override fun retrieveAll(): Flow<List<CgrievanceEntity>> {
        return grievDao.retrieveAll()
    }
}