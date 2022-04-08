package com.muchbeer.ktorplug.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AgrievEntity")
data class AgrievanceEntity(
    @PrimaryKey
    val id : String
)

@Entity(tableName = "BpapEntity")
data class BpapEntity(
    @PrimaryKey
    val id : String
)

@Entity(tableName = "CgrievEntity")
data class CgrievanceEntity(
    @PrimaryKey
    val id : String
)

@Entity(tableName = "DattachEntity")
data class DattachEntity(
    @PrimaryKey
    val id : String
)