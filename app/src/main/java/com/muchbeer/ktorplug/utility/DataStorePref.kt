package com.muchbeer.ktorplug.utility

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.muchbeer.ktorplug.utility.PostConstant.GRIEVANCE_PREFERENCE_NAME
import com.muchbeer.ktorplug.utility.PostConstant.SAMPLE_KEY
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


private val Context.dataStore by preferencesDataStore( name = GRIEVANCE_PREFERENCE_NAME )
class DataStorePref (val context: Context) {

    suspend fun <T> saveValue(valueName : T, key: Preferences.Key<T>) {
        context.dataStore.edit { preferences ->
            preferences[key] = valueName
        }
    }

    fun <T> retrievValueFlow(
        key: Preferences.Key<T>,
        defaultValue: T
    ): Flow<T> {
        return context.dataStore.data
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


    //Don't convert to Stateflow
    val phoneNumber: Flow<String?>
        get() = context.dataStore.data
            .catch { exception ->
                if(exception is IOException) {
                    logs("Error reading preferences. ", exception.message.toString())
                    emit(emptyPreferences())
                } else { throw exception }
            }
            .map { preferences ->
                preferences[SAMPLE_KEY]
            }





}