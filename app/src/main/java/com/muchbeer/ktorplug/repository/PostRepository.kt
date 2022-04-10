package com.muchbeer.ktorplug.repository

import com.muchbeer.ktorplug.data.remote.DataState
import com.muchbeer.ktorplug.data.remote.sampledto.ImageResponseDto
import com.muchbeer.ktorplug.data.remote.sampledto.PostRequestDto
import com.muchbeer.ktorplug.data.remote.sampledto.PostResponseDto
import kotlinx.coroutines.flow.Flow
import java.io.File

interface PostRepository {

     fun getPosts() : Flow<DataState<List<PostResponseDto>>>
      fun getPostFromGeneric() : Flow<DataState<List<PostResponseDto>>>
     fun createPost(postRequest : PostRequestDto) : Flow<DataState<PostResponseDto?>>
      fun createPostFromGeneric(postRequest : PostRequestDto) : Flow<DataState<PostResponseDto?>>
      fun uploadImage(filePath : File) : Flow<DataState<ImageResponseDto>>

}