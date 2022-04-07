package com.muchbeer.ktorplug

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.gson.GsonBuilder
import com.muchbeer.ktorplug.data.DataState
import com.muchbeer.ktorplug.data.PostRequest
import com.muchbeer.ktorplug.data.PostResponse
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.lang.Exception
import java.net.UnknownHostException

object PostConstant {

    private const val _BASE_URL = BuildConfig.BASE_URL
    const val BASE_URL = _BASE_URL + "posts"

    private const val TIME_OUT = 60_000
}


fun <T> Fragment.collectLatestLifecylceFlow (stateFlow: Flow<T>, collect: suspend (T)->Unit) {
     viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
           // stateFlow.collectLatest(collect)
            stateFlow.collect {
                collect(it)
            }
        }
    }
}

fun <T> Fragment.collectActivityFlow (stateFlow: StateFlow<T>, collect: suspend (T)->Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            // stateFlow.collectLatest(collect)
            stateFlow.collectLatest {
                collect(it)
            }
        }
    }
}

 fun Context.showMessage(msg : String) {
     Log.d("ViewFragment", "The error is : $msg")
}
@Suppress("IMPLICIT_CAST_TO_ANY")
 fun<T> useWhenStatement(inputData: DataState<T>, msg : ()-> Unit,
          loader: ()->Unit, exception: () ->Unit, sucessData : (T) ->Unit) {
    when(inputData){
        is DataState.Error -> msg
        is DataState.ErrorException -> exception
        DataState.Loading -> loader
        is DataState.Success -> sucessData(inputData.data)
    }.exhaustive
}

inline fun<reified T> handleGetState(httpClient : HttpClient, getUrl : String) :
                            Flow<DataState<T>> =  flow {

    emit(DataState.Loading)
    try {
        //  emit(DataState.Success(listOf(PostResponse("georgeFamily","family", 1, 1))))
        val retrievePost : T =   httpClient.get<T> {
            url(getUrl)
        }
        emit(DataState.Success(retrievePost))
    }  catch (e: RedirectResponseException) {
        //3xx
        emit(DataState.Error(error = e.response.status.description))
    } catch ( e : ClientRequestException) {
        emit(DataState.Error(error = e.response.status.description))
    } catch (e : ServerResponseException) {
        emit(DataState.Error(error = e.response.status.description))
    } catch (e : Exception) {
        emit(DataState.Error(error = e.message.toString()))
    } catch (e : UnknownHostException) {
        emit(DataState.Error(error = e.message.toString()))
    }
}


inline fun<reified T> handlePostState(httpClient : HttpClient, postUrl : String,
                                            postRequest: PostRequest) :
        Flow<DataState<T>> =  flow {

    emit(DataState.Loading)
    try {
        //  emit(DataState.Success(listOf(PostResponse("georgeFamily","family", 1, 1))))
        val postData : T =   httpClient.post<T>() {
            url(postUrl)
            contentType(ContentType.Application.Json)
            body = postRequest
        }
        emit(DataState.Success(postData))
    }  catch (e: RedirectResponseException) {
        //3xx
        emit(DataState.Error(error = e.response.status.description))
    } catch ( e : ClientRequestException) {
        emit(DataState.Error(error = e.response.status.description))
    } catch (e : ServerResponseException) {
        emit(DataState.Error(error = e.response.status.description))
    } catch (e : Exception) {
        emit(DataState.Error(error = e.message.toString()))
    } catch (e : UnknownHostException) {
        emit(DataState.Error(error = e.message.toString()))
    }
}
val <T> T.exhaustive : T
    get() = this

fun<T> logPrettyJson (dataModel : T) {
    val gsonPretty = GsonBuilder().setPrettyPrinting().create()
    val jsonDBListPretty: String = gsonPretty.toJson(dataModel)
    Log.d("ViewFragment", "the fetch data is $jsonDBListPretty}")
}