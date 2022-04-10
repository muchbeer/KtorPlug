package com.muchbeer.ktorplug.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.muchbeer.ktorplug.utility.PostConstant.FULL_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


// private val Context.dataStore by preferencesDataStore( name = GRIEVANCE_PREFERENCE_NAME )
class DataStoreprefImpl @Inject constructor(private val dataStore: DataStore<Preferences>)

    : DataStorePref{

  private  suspend fun <T> saveValue(valueName : T, key: Preferences.Key<T>) {
        dataStore.edit { preferences ->
            preferences[key] = valueName
        }
    }

    private suspend fun deleteAllValues() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    private suspend fun <T> deleteSpecificKey(key: Preferences.Key<T>) {
        dataStore.edit { preferences ->
            preferences.remove(key)
        }
    }
    //Don't convert to Stateflow
 private   fun <T> retrievValueFlow(
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

    override suspend fun deleteAllData() {  deleteAllValues()  }

}