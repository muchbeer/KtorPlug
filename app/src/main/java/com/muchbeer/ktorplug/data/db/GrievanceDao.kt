package com.muchbeer.ktorplug.data.db

import androidx.room.*
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

    //************Insert into database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAGrievance(agrievance : AgrievanceEntity) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBpapDetailEntry(papDetail: BpapDetailEntity) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBpapDetailList(papDetail: List<BpapDetailEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCGrievanceEntry(cgrienvance: CgrievTotalEntity) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCGrievanceList(cGriev: List<CgrievanceEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDAttachEntry(dAttach: DpapAttachEntity) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDAttachEntryList(dAttachs: List<DpapAttachEntity>)

    //************Retrieve from a selection of field
    @Query("SELECT * FROM cgrievtotalentity WHERE a_username = :username")
    fun getAllGrieveWithPaps(username : String) : Flow<List<CgrievTotalEntity>>

    @Query("SELECT * FROM dattachentity WHERE c_fullname = :fullName")
    fun getAllDpapsAttachmentInCGrievence(fullName: String) : Flow<List<DpapAttachEntity>>

    @Query("SELECT * FROM dattachentity WHERE image_status = :imageStatus")
    fun getAllDAttachByStatus(imageStatus: IMAGESTATUS) : Flow<List<DpapAttachEntity>>

    @Query("SELECT * FROM bpapentity WHERE a_username =:username")
    fun getAllBpapsWithEachUsername(username: String) : Flow<List<BpapDetailEntity>>


    //*****************Retreive all the data
    @Query("SELECT * FROM agrieventity")
    fun retrieveAgrievanceEntry() : Flow<List<AgrievanceEntity>>

    @Query("SELECT * FROM cgrievtotalentity")
    fun retrieveCGrievance() : Flow<List<CgrievTotalEntity>>

    @Query("SELECT * FROM dattachentity")
    fun retrieveAllDAttachment() : Flow<List<DpapAttachEntity>>

    @Query("SELECT * FROM bpapentity")
    fun retrieveAllBPaps() : Flow<List<BpapDetailEntity>>

    @Query("SELECT * FROM dattachentity")
    fun retrieveAllDAttachmentUploads() : Flow<List<DpapAttachEntity>>

    @Update
    suspend fun updateCgrievance(cgrienvance: CgrievTotalEntity)

    @Update
    suspend fun updateDAttachment(dAttach: DpapAttachEntity)
}