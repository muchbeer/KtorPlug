package com.muchbeer.ktorplug.data.remote.datasource

import com.muchbeer.ktorplug.data.BackState
import com.muchbeer.ktorplug.data.DataState
import com.muchbeer.ktorplug.data.db.CgrievanceEntity
import com.muchbeer.ktorplug.data.remote.sampledto.ImageResponseDto
import com.muchbeer.ktorplug.data.remote.sampledto.PostRequestDto
import com.muchbeer.ktorplug.data.remote.sampledto.PostResponseDto
import com.muchbeer.ktorplug.utility.PostConstant
import com.muchbeer.ktorplug.utility.PostConstant.BASE_URL_POSTS
import com.muchbeer.ktorplug.utility.handleImageUpload
import com.muchbeer.ktorplug.utility.handleNetworkState
import com.muchbeer.ktorplug.utility.handlePostState
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import java.io.File

class RemoteDataSourceImpl(
    private val httpclient : HttpClient,
): RemoteDataSource{
    override fun getPostFromGeneric(): Flow<BackState<List<PostResponseDto>>> {
        return handleNetworkState {
            httpclient.get {
                url(BASE_URL_POSTS)
            }
        }
    }

    override fun createPost(postRequest: PostRequestDto): Flow<BackState<PostResponseDto?>> {
        return handlePostState(httpclient, BASE_URL_POSTS, postRequest)
    }

    override fun createPostFromGeneric(postRequest: PostRequestDto): Flow<BackState<PostResponseDto?>> {
        return handleNetworkState {
            httpclient.post {
                url(BASE_URL_POSTS)
                contentType(ContentType.Application.Json)
                body = postRequest
            }
        }
    }

    override fun uploadImage(filePath: File, descName : String): Flow<BackState<ImageResponseDto>> {
        return handleImageUpload(
            httpClient = httpclient,
            file = filePath,
            desc = descName
        )
    }

}