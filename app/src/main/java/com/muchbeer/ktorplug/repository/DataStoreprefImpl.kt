package com.muchbeer.ktorplug.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.muchbeer.ktorplug.utility.PostConstant.FULL_NAME
import com.muchbeer.ktorplug.utility.PostConstant.GRIEVANCE_PREFERENCE_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


// private val Context.dataStore by preferencesDataStore( name = GRIEVANCE_PREFERENCE_NAME )
class DataStoreprefImpl @Inject constructor(private val dataStore: DataStore<Preferences>)

    : DataStorePref{

    suspend fun <T> saveValue(valueName : T, key: Preferences.Key<T>) {
        dataStore.edit { preferences ->
            preferences[key] = valueName
        }
    }

    //Don't convert to Stateflow
    fun <T> retrievValueFlow(
        key: Preferences.Key<T>,
        defaultValue: T
    ): Flow<T> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                preferences[key] ?: defaultValue
            }
    }

    override suspend fun saveFullName(muchbeerFamily: String) {
        saveValue(muchbeerFamily, FULL_NAME)  }

    override fun retrieveName(): Flow<String>  = retrievValueFlow(FULL_NAME, "muchbeer")

}