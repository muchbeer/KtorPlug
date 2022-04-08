package com.muchbeer.ktorplug.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AgrievanceEntity::class],
    autoMigrations = [], version = 1, exportSchema = false)
abstract class OilDatabase() : RoomDatabase() {

    abstract fun grievDao() : GrievanceDao
    abstract fun papDao() : PapDao

    companion object {
        @Volatile
        private var INSTANCE : OilDatabase? = null


        fun getDatabaseInstance(context: Context) : OilDatabase
        {
            synchronized(this) {
                var instance = INSTANCE
                if(instance ==null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        OilDatabase::class.java,
                        "oil_db")
                        .build()
                }
                return instance
            }

        }
    }
}