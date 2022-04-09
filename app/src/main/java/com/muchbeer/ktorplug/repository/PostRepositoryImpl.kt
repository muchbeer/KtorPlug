package com.muchbeer.ktorplug.repository

import com.muchbeer.ktorplug.data.remote.DataState
import com.muchbeer.ktorplug.data.remote.sampledto.ImageResponseDto
import com.muchbeer.ktorplug.data.remote.sampledto.PostRequestDto
import com.muchbeer.ktorplug.data.remote.sampledto.PostResponseDto
import com.muchbeer.ktorplug.utility.PostConstant.BASE_URL_POSTS
import com.muchbeer.ktorplug.utility.PostConstant.FULL_NAME
import com.muchbeer.ktorplug.utility.handleGetState
import com.muchbeer.ktorplug.utility.handleImageUpload
import com.muchbeer.ktorplug.utility.handlePostState
import io.ktor.client.*
import kotlinx.coroutines.flow.Flow
import java.io.File

class PostRepositoryImpl(
    private val httpclient : HttpClient,
    ) : PostRepository {

    override fun getPosts(): Flow<DataState<List<PostResponseDto>>>   {
        return handleGetState(httpclient, BASE_URL_POSTS)
    }

    override fun createPost(postRequest: PostRequestDto): Flow<DataState<PostResponseDto?>> {
        return handlePostState(httpclient, BASE_URL_POSTS, postRequest)
    }

   // onUpload extension function is used to display upload progress:
    override fun uploadImage(filePath: File): Flow<DataState<ImageResponseDto>> {
      return handleImageUpload(
          httpClient = httpclient,
          file = filePath
        )
     }


}