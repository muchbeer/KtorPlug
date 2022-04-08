package com.muchbeer.ktorplug.data.remote.sampledto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostResponseDto(
    @SerialName("body")
    val body : String,
    @SerialName("title")
    val title : String,
    @SerialName("id")
    val id : Int,
    @SerialName("userId")
    val userId : Int
)
