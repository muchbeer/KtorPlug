package com.muchbeer.ktorplug.data.remote.sampledto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageResponseDto(
    @SerialName("error")
    val error : Boolean,
    @SerialName("message")
    val message : String,
    @SerialName("image")
    val image : String
)
