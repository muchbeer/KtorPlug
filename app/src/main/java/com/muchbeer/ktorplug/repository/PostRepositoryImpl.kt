package com.muchbeer.ktorplug.repository

import com.muchbeer.ktorplug.data.DataState
import com.muchbeer.ktorplug.data.PostRequest
import com.muchbeer.ktorplug.data.PostResponse
import com.muchbeer.ktorplug.handleGetState
import com.muchbeer.ktorplug.handlePostState
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import java.net.UnknownHostException
import com.muchbeer.ktorplug.PostConstant as PostConstant

class PostRepositoryImpl(
    private val httpclient : HttpClient
) : PostRepository {
    override fun getPosts(): Flow<DataState<List<PostResponse>>>   {
        return handleGetState(httpclient, PostConstant.BASE_URL)
    }
    override fun createPost(postRequest: PostRequest): Flow<DataState<PostResponse?>> {
        return handlePostState(httpclient, PostConstant.BASE_URL, postRequest)
    }

}