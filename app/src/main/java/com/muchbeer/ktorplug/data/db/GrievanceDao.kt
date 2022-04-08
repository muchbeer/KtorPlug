package com.muchbeer.ktorplug.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface GrievanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCgriev(cGriev: CgrievanceEntity) : Long
}