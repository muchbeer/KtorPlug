package com.muchbeer.ktorplug.repository

import com.muchbeer.ktorplug.PostConstant.BASE_URL
import com.muchbeer.ktorplug.PostConstant.LINK_URL
import com.muchbeer.ktorplug.data.DataState
import com.muchbeer.ktorplug.data.ImageResponse
import com.muchbeer.ktorplug.data.PostRequest
import com.muchbeer.ktorplug.data.PostResponse
import com.muchbeer.ktorplug.handleGetState
import com.muchbeer.ktorplug.handleImageUpload
import com.muchbeer.ktorplug.handlePostState
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.lang.Exception
import java.net.UnknownHostException
import com.muchbeer.ktorplug.PostConstant as PostConstant

class PostRepositoryImpl(
    private val httpclient : HttpClient
) : PostRepository {
    override fun getPosts(): Flow<DataState<List<PostResponse>>>   {
        return handleGetState(httpclient, BASE_URL)
    }
    override fun createPost(postRequest: PostRequest): Flow<DataState<PostResponse?>> {
        return handlePostState(httpclient, BASE_URL, postRequest)
    }

   // onUpload extension function is used to display upload progress:
    override fun uploadImage(filePath: File): Flow<DataState<ImageResponse>> {
      return handleImageUpload(
          httpClient = httpclient,
          file = filePath
        )
     }

}