package com.muchbeer.ktorplug.utility

import com.muchbeer.ktorplug.data.BackState
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


inline fun<reified T> handleImageUpload(httpClient: HttpClient, file : File, desc : String) :
        Flow<BackState<T>> = flow{

    try {
        val response : T = httpClient.submitFormWithBinaryData(
            url = PostConstant.LINK_URL_IMAGE,
            formData = formData {
                append("desc", desc)
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
        emit(BackState.Success(response))
    } catch (e: RedirectResponseException) {
        emit(BackState.Error(error = e.response.status.description))
    } catch ( e : ClientRequestException) {
        emit(BackState.Error(error = e.response.status.description))
    } catch (e : ServerResponseException) {
        emit(BackState.Error(error = e.response.status.description))
    } catch (e : Exception) {
        emit(BackState.Error(error = e.message.toString()))
    } catch (e : UnknownHostException) {
        emit(BackState.Error(error = e.message.toString()))
    }
}

inline fun<reified T> handleGetState(httpClient : HttpClient, getUrl : String) :
        Flow<BackState<T>> =  flow {

    try {
        val retrievePost : T =   httpClient.get {
            url(getUrl)
        }
        emit(BackState.Success(retrievePost))
    }  catch (e: RedirectResponseException) {
        emit(BackState.Error(error = e.response.status.description))
    } catch ( e : ClientRequestException) {
        emit(BackState.Error(error = e.response.status.description))
    } catch (e : ServerResponseException) {
        emit(BackState.Error(error = e.response.status.description))
    } catch (e : Exception) {
        emit(BackState.Error(error = e.message.toString()))
    } catch (e : UnknownHostException) {
        emit(BackState.Error(error = e.message.toString()))
    }
}


    inline fun<reified T> handlePostState(httpClient : HttpClient, postUrl : String,
                                          postRequest: PostRequestDto
    ) :
            Flow<BackState<T>> =  flow {

        try {

            val postData : T =   httpClient.post {
                url(postUrl)
                contentType(ContentType.Application.Json)
                body = postRequest
            }
            emit(BackState.Success(postData))
        }  catch (e: RedirectResponseException) {
            //3xx
            emit(BackState.Error(error = e.response.status.description))
        } catch ( e : ClientRequestException) {
            emit(BackState.Error(error = e.response.status.description))
        } catch (e : ServerResponseException) {
            emit(BackState.Error(error = e.response.status.description))
        } catch (e : Exception) {
            emit(BackState.Error(error = e.message.toString()))
        } catch (e : UnknownHostException) {
            emit(BackState.Error(error = e.message.toString()))
        }

    }

inline fun<reified T> handleNetworkState(
                                         crossinline networkCall : suspend () -> T
                                            ) :
        Flow<BackState<T>> =  flow {

    try {
        emit(BackState.Success(networkCall()))
    } catch (e: RedirectResponseException) {
        //3xx
        emit(BackState.Error(error = e.response.status.description))
    } catch ( e : ClientRequestException) {
        emit(BackState.Error(error = e.response.status.description))
    } catch (e : ServerResponseException) {
        emit(BackState.Error(error = e.response.status.description))
    } catch (e : Exception) {
        emit(BackState.Error(error = e.message.toString()))
    } catch (e : UnknownHostException) {
        emit(BackState.Error(error = e.message.toString()))
        }
    }

