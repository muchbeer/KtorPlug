package com.muchbeer.ktorplug.repository

import androidx.lifecycle.LiveData
import com.muchbeer.ktorplug.data.DataState
import com.muchbeer.ktorplug.data.PostRequest
import com.muchbeer.ktorplug.data.PostResponse
import kotlinx.coroutines.flow.Flow

interface PostRepository {

     fun getPosts() : Flow<DataState<List<PostResponse>>>
     fun createPost(postRequest : PostRequest) : Flow<DataState<PostResponse?>>

}