package com.muchbeer.ktorplug.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muchbeer.ktorplug.data.DataState
import com.muchbeer.ktorplug.data.ImageResponse
import com.muchbeer.ktorplug.data.PostRequest
import com.muchbeer.ktorplug.data.PostResponse
import com.muchbeer.ktorplug.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repository : PostRepository
) : ViewModel(){

   // private val _uiState = MutableStateFlow(LatestNewsUiState.Success(emptyList()))
    val retrievePost : StateFlow<DataState<List<PostResponse>>> = repository.getPosts()
        .stateIn(
            initialValue = DataState.Loading,
            scope = viewModelScope,
            started = WhileSubscribed(5000)
        )

    fun sendStatus(request: PostRequest) : StateFlow<DataState<PostResponse?>> {
       val serverResponse : StateFlow<DataState<PostResponse?>> =  repository.createPost(request)
           .stateIn(
               initialValue = DataState.Loading,
               scope = viewModelScope,
               started = WhileSubscribed(5000)
           )
        return serverResponse
    }

    fun uploadImage(fileName : File) : StateFlow<DataState<ImageResponse>> {
        val imageResponse : StateFlow<DataState<ImageResponse>> =  repository.uploadImage(fileName)
            .stateIn(
                initialValue = DataState.Loading,
                scope = viewModelScope,
                started = WhileSubscribed(5000)
            )
        return imageResponse
    }
}