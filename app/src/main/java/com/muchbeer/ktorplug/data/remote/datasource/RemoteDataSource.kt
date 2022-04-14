package com.muchbeer.ktorplug.data.remote.datasource

import com.muchbeer.ktorplug.data.DataState
import com.muchbeer.ktorplug.data.db.CgrievanceEntity
import com.muchbeer.ktorplug.data.remote.sampledto.ImageResponseDto
import com.muchbeer.ktorplug.data.remote.sampledto.PostRequestDto
import com.muchbeer.ktorplug.data.remote.sampledto.PostResponseDto
import kotlinx.coroutines.flow.Flow
import java.io.File

interface RemoteDataSource {
    fun getPostFromGeneric() : Flow<DataState<List<PostResponseDto>>>
    fun createPost(postRequest : PostRequestDto) : Flow<DataState<PostResponseDto?>>
    fun createPostFromGeneric(postRequest : PostRequestDto) : Flow<DataState<PostResponseDto?>>
    fun uploadImage(filePath : File) : Flow<DataState<ImageResponseDto>>
}