package com.muchbeer.ktorplug.data.remote.datasource

import com.muchbeer.ktorplug.data.BackState
import com.muchbeer.ktorplug.data.DataState
import com.muchbeer.ktorplug.data.db.CgrievanceEntity
import com.muchbeer.ktorplug.data.remote.sampledto.ImageResponseDto
import com.muchbeer.ktorplug.data.remote.sampledto.PostRequestDto
import com.muchbeer.ktorplug.data.remote.sampledto.PostResponseDto
import kotlinx.coroutines.flow.Flow
import java.io.File

interface RemoteDataSource {
    fun getPostFromGeneric() : Flow<BackState<List<PostResponseDto>>>
    fun createPost(postRequest : PostRequestDto) : Flow<BackState<PostResponseDto?>>
    fun createPostFromGeneric(postRequest : PostRequestDto) : Flow<BackState<PostResponseDto?>>
    fun uploadImage(filePath : File, descName : String) : Flow<BackState<ImageResponseDto>>
}