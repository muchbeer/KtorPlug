package com.muchbeer.ktorplug.data.remote.sampledto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostRequestDto(
    @SerialName("body")
    val body : String,
    val title : String,
    val userId : Int
)