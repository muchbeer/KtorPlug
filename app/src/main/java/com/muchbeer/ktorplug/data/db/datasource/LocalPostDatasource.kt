package com.muchbeer.ktorplug.data.db.datasource

import com.muchbeer.ktorplug.data.db.CgrievanceEntity
import kotlinx.coroutines.flow.Flow

interface LocalPostDatasource {
    suspend fun insertPost(posts : List<CgrievanceEntity>)
    fun retrieveSearchPosts(search : String) : Flow<List<CgrievanceEntity>>
    fun retrieveAll() : Flow<List<CgrievanceEntity>>
}