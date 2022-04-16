package com.muchbeer.ktorplug.utility

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.FileProvider
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.GsonBuilder
import com.muchbeer.ktorplug.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

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

 fun Context.getFileUri(fileName: String, filePath : (File) -> Unit): Uri {
    val tmpFile = File.createTempFile(fileName, ".png",  cacheDir).apply {
        createNewFile()
        deleteOnExit()
    }
    filePath(tmpFile)
    return FileProvider.getUriForFile(this, "${BuildConfig.APPLICATION_ID}.provider", tmpFile)
}

inline fun SearchView.onQueryTextChanged(crossinline listener: (String) -> Unit) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            listener(newText.orEmpty())
            return true
        }
    })
}
