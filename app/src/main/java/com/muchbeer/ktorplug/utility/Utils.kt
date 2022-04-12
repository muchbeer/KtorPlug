package com.muchbeer.ktorplug.utility

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.GsonBuilder
import com.muchbeer.ktorplug.BuildConfig
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

object PostConstant {

    private const val _BASE_URL = BuildConfig.BASE_URL
    const val BASE_URL_POSTS = _BASE_URL + "posts"

    private const val _LINK_URL = BuildConfig.LINK_URL
    const val LINK_URL_IMAGE = _LINK_URL + "home/Api.php?apicall=upload"


    //preferenceKey
    val FULL_NAME = stringPreferencesKey("full_name")
    const val GRIEVANCE_PREFERENCE_NAME ="gadiel_pref"
}


val <T> T.exhaustive : T
    get() = this

fun<T> logPrettyJson (dataModel : T) {
    val gsonPretty = GsonBuilder().setPrettyPrinting().create()
    val jsonDBListPretty: String = gsonPretty.toJson(dataModel)
    Log.d("ViewFragment", "the fetch data is $jsonDBListPretty}")
}

fun logs(className: String, msg: String) {  Log.d(className, msg) }

fun Context.toastMsg(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}