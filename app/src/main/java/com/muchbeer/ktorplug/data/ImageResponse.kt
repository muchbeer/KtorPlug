package com.muchbeer.ktorplug.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageResponse(
    @SerialName("error")
    val error : Boolean,
    @SerialName("message")
    val message : String,
    @SerialName("image")
    val image : String
)
