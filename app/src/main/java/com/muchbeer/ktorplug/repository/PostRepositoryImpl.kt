package com.muchbeer.ktorplug.repository

import com.muchbeer.ktorplug.data.remote.DataState
import com.muchbeer.ktorplug.data.remote.sampledto.ImageResponseDto
import com.muchbeer.ktorplug.data.remote.sampledto.PostRequestDto
import com.muchbeer.ktorplug.data.remote.sampledto.PostResponseDto
import com.muchbeer.ktorplug.utility.*
import com.muchbeer.ktorplug.utility.PostConstant.BASE_URL_POSTS
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import java.io.File

class PostRepositoryImpl(
    private val httpclient : HttpClient,
    ) : PostRepository {

    override fun getPosts(): Flow<DataState<List<PostResponseDto>>> {
        return handleGetState(httpclient, BASE_URL_POSTS)
    }

    override fun getPostFromGeneric(): Flow<DataState<List<PostResponseDto>>> {
        return handleNetworkState {
            httpclient.get {
                url(BASE_URL_POSTS)
            }
        }
    }

    override fun createPost(postRequest: PostRequestDto): Flow<DataState<PostResponseDto?>> {
        return handlePostState(httpclient, BASE_URL_POSTS, postRequest)
    }

    override fun createPostFromGeneric(postRequest: PostRequestDto): Flow<DataState<PostResponseDto?>> {
        return handleNetworkState {
            httpclient.post {
                url(BASE_URL_POSTS)
                contentType(ContentType.Application.Json)
                body = postRequest
            }
        }
    }

    override fun uploadImage(filePath: File): Flow<DataState<ImageResponseDto>> {
        return handleImageUpload(
            httpClient = httpclient,
            file = filePath
        )    }

    override fun workManagerValues() {
     val randomNumber = (10..100).random()
    logs(TAG, "The random number is : $randomNumber")}

    companion object {
        private val TAG = PostRepositoryImpl::class.simpleName.toString()
    }
}

