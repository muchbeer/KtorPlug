package com.muchbeer.ktorplug.data

sealed class DataState<out R> {

    data class Success<T>(val data: T) : DataState<T>()
    data class Error(val error: String) : DataState<Nothing>()
    data class ErrorException(val exception: Exception) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}