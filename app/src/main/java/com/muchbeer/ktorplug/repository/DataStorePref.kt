package com.muchbeer.ktorplug.repository

import kotlinx.coroutines.flow.Flow

interface DataStorePref {
    suspend fun saveFullName(muchbeerFamily: String)
    fun retrieveName() : Flow<String>
    suspend fun deleteAllData()
}