package com.muchbeer.ktorplug.utility

import com.muchbeer.ktorplug.data.DataState
import com.muchbeer.ktorplug.data.remote.sampledto.PostRequestDto
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.lang.Exception
import java.net.UnknownHostException


inline fun<reified T> handleImageUpload(httpClient: HttpClient, file : File) :
        Flow<DataState<T>> = flow{
    emit(DataState.Loading)

    try {
        val response : T = httpClient.submitFormWithBinaryData(
            url = PostConstant.LINK_URL_IMAGE,
            formData = formData {
                append("desc", "Gianna")
                append(
                    key = "image",
                    file.readBytes(), Headers.build {
                        append(HttpHeaders.ContentType, "image/png")
                        append(HttpHeaders.ContentDisposition, "filename=ktor_logo.png")
                    })
            }
        ) {
            onUpload { bytesSentTotal, contentLength ->
                println("Sent $bytesSentTotal bytes from $contentLength")
            }
        }
        emit(DataState.Success(response))
    } catch (e: RedirectResponseException) {
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

inline fun<reified T> handleGetState(httpClient : HttpClient, getUrl : String) :
        Flow<DataState<T>> =  flow {

    emit(DataState.Loading)
    try {
        val retrievePost : T =   httpClient.get {
            url(getUrl)
        }
        emit(DataState.Success(retrievePost))
    }  catch (e: RedirectResponseException) {
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
                                          postRequest: PostRequestDto
    ) :
            Flow<DataState<T>> =  flow {

        emit(DataState.Loading)
        try {

            val postData : T =   httpClient.post {
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

inline fun<reified T> handleNetworkState(
                                         crossinline networkCall : suspend () -> T
                                            ) :
        Flow<DataState<T>> =  flow {
            emit(DataState.Loading)

    try {
        emit(DataState.Success(networkCall()))
    } catch (e: RedirectResponseException) {
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

