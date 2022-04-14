package com.muchbeer.ktorplug.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muchbeer.ktorplug.data.DataState
import com.muchbeer.ktorplug.data.db.CgrievanceEntity
import com.muchbeer.ktorplug.data.remote.sampledto.ImageResponseDto
import com.muchbeer.ktorplug.data.remote.sampledto.PostRequestDto
import com.muchbeer.ktorplug.data.remote.sampledto.PostResponseDto
import com.muchbeer.ktorplug.repository.DataStorePref
import com.muchbeer.ktorplug.repository.PostRepository
import com.muchbeer.ktorplug.utility.logs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repository : PostRepository,
    private val dataStorePref : DataStorePref
) : ViewModel(){

    private val _searchItemState = MutableStateFlow("")

    fun setSearchQuery(search: String) =viewModelScope.launch {
        _searchItemState.value = search    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    val searchListStateData : StateFlow<DataState<List<CgrievanceEntity>>> = _searchItemState
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { search ->
         repository.retrieveFromDb(search)
        } .catch { throwable ->
            logs("PostViewModel","The error occured is : ${throwable.message}")
        }.stateIn(
            initialValue = DataState.Loading,
            scope = viewModelScope,
            started = WhileSubscribed(5000)
        )

    val retrievePost : StateFlow<DataState<List<PostResponseDto>>> = repository.getPostFromGeneric()
        .stateIn(
            initialValue = DataState.Loading,
            scope = viewModelScope,
            started = WhileSubscribed(5000)
        )

    fun sendStatus(request: PostRequestDto) : StateFlow<DataState<PostResponseDto?>> {
        return repository.createPostFromGeneric(request)
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

    companion object {
        private val TAG = PostViewModel::class.simpleName.toString()
    }
}