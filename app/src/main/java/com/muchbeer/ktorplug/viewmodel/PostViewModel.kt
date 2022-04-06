package com.muchbeer.ktorplug.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muchbeer.ktorplug.data.DataState
import com.muchbeer.ktorplug.data.PostRequest
import com.muchbeer.ktorplug.data.PostResponse
import com.muchbeer.ktorplug.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
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
  //  fun getPost
}