package com.muchbeer.ktorplug.di

import com.muchbeer.ktorplug.repository.PostRepository
import com.muchbeer.ktorplug.repository.PostRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.http.cio.*
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {


    @Singleton
    @Provides
    fun provideKtorHttpClient(): HttpClient {
        return HttpClient() {
            // Logging
            install(Logging) {
                level = LogLevel.BODY
            }
            // JSON
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
    fun providePostRepository(httpClient: HttpClient) : PostRepository {
        return PostRepositoryImpl(httpclient = httpClient)
    }

    private val json = kotlinx.serialization.json.Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = false
    }
}