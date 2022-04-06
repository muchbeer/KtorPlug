package com.muchbeer.ktorplug.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostResponse(
    @SerialName("body")
    val body : String,
    @SerialName("title")
    val title : String,
    @SerialName("id")
    val id : Int,
    @SerialName("userId")
    val userId : Int
)
