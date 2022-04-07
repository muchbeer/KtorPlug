package com.muchbeer.ktorplug.repository

import androidx.lifecycle.LiveData
import com.muchbeer.ktorplug.data.DataState
import com.muchbeer.ktorplug.data.ImageResponse
import com.muchbeer.ktorplug.data.PostRequest
import com.muchbeer.ktorplug.data.PostResponse
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.Flow
import java.io.File

interface PostRepository {

     fun getPosts() : Flow<DataState<List<PostResponse>>>
     fun createPost(postRequest : PostRequest) : Flow<DataState<PostResponse?>>
      fun uploadImage(file : File) : Flow<DataState<ImageResponse>>

}