package com.muchbeer.ktorplug.data.remote.sampledto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PapLoginEntity")
data class PapLoginEntity(
    @PrimaryKey
    val id : String
)