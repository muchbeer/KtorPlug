package com.muchbeer.ktorplug.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muchbeer.ktorplug.data.remote.DataState
import com.muchbeer.ktorplug.data.remote.sampledto.ImageResponseDto
import com.muchbeer.ktorplug.data.remote.sampledto.PostRequestDto
import com.muchbeer.ktorplug.data.remote.sampledto.PostResponseDto
import com.muchbeer.ktorplug.repository.DataStorePref
import com.muchbeer.ktorplug.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repository : PostRepository,
    private val dataStorePref : DataStorePref
) : ViewModel(){

   // private val _uiState = MutableStateFlow(LatestNewsUiState.Success(emptyList()))
    val retrievePost : StateFlow<DataState<List<PostResponseDto>>> = repository.getPosts()
        .stateIn(
            initialValue = DataState.Loading,
            scope = viewModelScope,
            started = WhileSubscribed(5000)
        )

    fun sendStatus(request: PostRequestDto) : StateFlow<DataState<PostResponseDto?>> {
        return repository.createPost(request)
           .stateIn(
               initialValue = DataState.Loading,
               scope = viewModelScope,
               started = WhileSubscribed(5000)
           )

    }

    fun uploadImage(fileName: File): StateFlow<DataState<ImageResponseDto>> {
        return repository.uploadImage(fileName)
            .stateIn(
                initialValue = DataState.Loading,
                scope = viewModelScope,
                started = WhileSubscribed(5000)
            )
    }

    fun saveFullname(user : String) = viewModelScope.launch {
        dataStorePref.saveFullName(user)
    }

    val retrieveFullName : Flow<String>   = dataStorePref.retrieveName()

}