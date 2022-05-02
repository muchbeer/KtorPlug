package com.muchbeer.ktorplug.di.remote

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.muchbeer.ktorplug.data.db.datasource.LocalPostDatasource
import com.muchbeer.ktorplug.data.db.datasource.LocalPostDatasourceAlt
import com.muchbeer.ktorplug.data.remote.datasource.RemoteDataSource
import com.muchbeer.ktorplug.data.remote.datasource.RemoteDataSourceImpl
import com.muchbeer.ktorplug.repository.DataStorePref
import com.muchbeer.ktorplug.repository.DataStoreprefImpl
import com.muchbeer.ktorplug.repository.PostRepository
import com.muchbeer.ktorplug.repository.PostRepositoryImpl
import com.muchbeer.ktorplug.utility.PostConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideKtorHttpClient(): HttpClient {
        return HttpClient() {
            install(Logging) {
                level = LogLevel.BODY
            }
            install(JsonFeature) {
               // serializer = KotlinxSerializer()
               // serializer = KotlinxSerializer(json)
               serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
            // Timeout
            install(HttpTimeout) {
                requestTimeoutMillis = 15000L
                connectTimeoutMillis = 90000L
                socketTimeoutMillis = 15000L
            }

            // Apply to all requests
            defaultRequest {
                // Parameter("api_key", "some_api_key")
                // Content Type
              //  if (method != HttpMethod.Get) contentType(ContentType.Application.Json)
              //  accept(ContentType.Application.Json)
            }
        }
    }

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext appContext: Context) :
            DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(PostConstant.GRIEVANCE_PREFERENCE_NAME)}
        )
    }

    @Singleton
    @Provides
    fun providesPrefStore(dataStore: DataStore<Preferences>) : DataStorePref {
        return DataStoreprefImpl(dataStore)
    }

    @Singleton
    @Provides
    fun providesRemoteDataSource(httpClient: HttpClient) : RemoteDataSource {
        return RemoteDataSourceImpl(httpClient)
    }



    @Singleton
    @Provides
    fun providePostRepository(remoteDS: RemoteDataSource,
                                localDS : LocalPostDatasource,
                                localDSAlt: LocalPostDatasourceAlt,
                                @ApplicationContext context: Context) : PostRepository {
        return PostRepositoryImpl(remoteDS, localDS,  localDSAlt, context)
    }

    private val json = kotlinx.serialization.json.Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = false
    }
}