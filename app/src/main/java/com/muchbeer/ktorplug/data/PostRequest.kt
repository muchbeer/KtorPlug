package com.muchbeer.ktorplug.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostRequest(
    @SerialName("body")
    val body : String,
    val title : String,
    val userId : Int
)