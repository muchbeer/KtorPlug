package com.muchbeer.ktorplug.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GrievanceAltDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCgrievAlt(cGriev: List<CgrievTotalAltEntity>)

    @Query("SELECT * FROM cgrievtotalentityalt")
    fun retrieveAllCGrievAlt() : Flow<List<CgrievTotalAltEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAGrievanceAlt(agrievance : AgrievanceAltEntity) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCGrievanceEntryAlt(cgrienvance: CgrievTotalAltEntity) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCGrievanceListAll(cGriev: List<CgrievTotalAltEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDAttachEntryAlt(dAttach: DpapAttachAltEntity) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDAttachEntryListAlt(dAttachs: List<DpapAttachAltEntity>)



    @Query("SELECT * FROM cgrievtotalentityalt WHERE a_username = :username")
    fun getAllCGrieveWithAgrievsUsernameAlt(username : String) : Flow<List<CgrievTotalAltEntity>>

    @Query("SELECT * FROM dattachentityalt WHERE c_fullname = :fullName")
    fun retrieveAllDpapsAttachmentInCGrievenceWithFullNameAlt(fullName: String) : Flow<List<DpapAttachAltEntity>>

    @Query("SELECT * FROM dattachentityalt WHERE image_status = :imageStatus")
    fun retrieveAllDAttachByStatusAlt(imageStatus: IMAGESTATUS) : Flow<List<DpapAttachAltEntity>>

    //*****************Retreive all the data
    @Query("SELECT * FROM agrieventityalt")
    fun retrieveAgrievanceEntryAlt() : Flow<List<AgrievanceAltEntity>>

    @Query("SELECT * FROM cgrievtotalentityalt")
    fun retrieveCGrievanceAlt() : Flow<List<CgrievTotalAltEntity>>

    @Query("SELECT * FROM dattachentityalt")
    fun retrieveAllDAttachmentAlt() : Flow<List<DpapAttachAltEntity>>

    @Query("SELECT * FROM dattachentityalt")
    fun retrieveAllDAttachmentUploadsAlt() : Flow<List<DpapAttachAltEntity>>

    @Update
    suspend fun updateCgrievanceAlt(cgrienvance: CgrievTotalAltEntity)

    @Update
    suspend fun updateDAttachmentAlt(dAttach: DpapAttachAltEntity)

    @Transaction
    @Query("SELECT * FROM AgrievEntityalt")
    fun retrieveAgrievWithCgrievAndDattach(): Flow<List<AgrievWithCgrieAndDattachList>>

}