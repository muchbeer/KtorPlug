package com.muchbeer.ktorplug.di.db

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.muchbeer.ktorplug.data.db.GrievanceDao
import com.muchbeer.ktorplug.data.db.OilDatabase
import com.muchbeer.ktorplug.data.db.PapDao
import com.muchbeer.ktorplug.utility.PostConstant.GRIEVANCE_PREFERENCE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) : OilDatabase {
        return OilDatabase.getDatabaseInstance(context)
    }


    @Singleton
    @Provides
    fun provideGrievanceDao(dataDB: OilDatabase) : GrievanceDao {
        return dataDB.grievDao()
    }

    @Singleton
    @Provides
    fun providePapDao(dataDB: OilDatabase) : PapDao {
        return dataDB.papDao()
    }


}