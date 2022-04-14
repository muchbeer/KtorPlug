package com.muchbeer.ktorplug.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GrievanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCgriev(cGriev: List<CgrievanceEntity>)

    @Query("SELECT * FROM cgrieventity WHERE title LIKE '%' || :searchQuery || '%' ORDER BY title ASC")
    fun retrieveAllSearchedPost(searchQuery: String) : Flow<List<CgrievanceEntity>>

    @Query("SELECT * FROM cgrieventity")
    fun retrieveAll() : Flow<List<CgrievanceEntity>>

    @Query("DELETE FROM cgrieventity")
    suspend fun deleteAllPosts()
}