package com.muchbeer.ktorplug.data

sealed class BackState<out R> {

    data class Success<T>(val data: T) : BackState<T>()
    data class Error(val error: String) : BackState<Nothing>()
}
