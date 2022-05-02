package com.muchbeer.ktorplug.di.db

import android.content.Context
import com.muchbeer.ktorplug.data.db.GrievanceDao
import com.muchbeer.ktorplug.data.db.OilDatabase
import com.muchbeer.ktorplug.data.db.GrievanceAltDao
import com.muchbeer.ktorplug.data.db.datasource.LocalPostDatasource
import com.muchbeer.ktorplug.data.db.datasource.LocalPostDatasourceAlt
import com.muchbeer.ktorplug.data.db.datasource.LocalPostDatasourceAltImpl
import com.muchbeer.ktorplug.data.db.datasource.LocalPostDatasourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
    fun providePapDao(dataDB: OilDatabase) : GrievanceAltDao {
        return dataDB.papDao()
    }

 @Singleton
 @Provides
 fun providePostDataSource(cGriev : GrievanceDao) : LocalPostDatasource {
     return LocalPostDatasourceImpl(cGriev)
 }

    @Singleton
    @Provides
    fun providesPostDataSourceAlt(cGrievAlt : GrievanceAltDao) : LocalPostDatasourceAlt {
        return LocalPostDatasourceAltImpl(cGrievAlt)
    }
}