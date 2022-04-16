package com.muchbeer.ktorplug.data.db

import android.content.Context
import androidx.room.*
import com.muchbeer.ktorplug.Converters
import com.muchbeer.ktorplug.data.db.OilDatabase.Companion.LATEST_VERSION

@Database(entities = [AgrievanceEntity::class, CgrievanceEntity::class, BpapDetailEntity::class,
                     CgrievTotalEntity::class, DpapAttachEntity::class],
   // autoMigrations = [AutoMigration (from = 3, to = 4) ],
    version = LATEST_VERSION,
    exportSchema = true)
@TypeConverters(Converters::class)
abstract class OilDatabase() : RoomDatabase() {

    abstract fun grievDao() : GrievanceDao
    abstract fun papDao() : PapDao

    companion object {
        const val LATEST_VERSION = 4
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
                        .fallbackToDestructiveMigration()
                         .build()
                }
                return instance
            }

        }
    }
}