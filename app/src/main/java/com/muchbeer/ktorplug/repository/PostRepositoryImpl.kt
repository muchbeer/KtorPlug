package com.muchbeer.ktorplug.repository

import com.muchbeer.ktorplug.data.DataState
import com.muchbeer.ktorplug.data.PostRequest
import com.muchbeer.ktorplug.data.PostResponse
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import com.muchbeer.ktorplug.PostConstant as PostConstant

class PostRepositoryImpl(
    private val httpclient : HttpClient
) : PostRepository {
    override fun getPosts(): Flow<DataState<List<PostResponse>>>   = flow {

        emit(DataState.Loading)
         try {
      //  emit(DataState.Success(listOf(PostResponse("georgeFamily","family", 1, 1))))
          val retrievePost : List<PostResponse> =   httpclient.get<List<PostResponse>> {
            url(PostConstant.BASE_URL)
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
        }
    }

    override fun createPost(postRequest: PostRequest): Flow<DataState<PostResponse?>>
                                          = flow{

        emit(DataState.Loading)
         try {
         val responseFromServer =   httpclient.post<PostResponse> {
                url(PostConstant.BASE_URL)
                contentType(ContentType.Application.Json)
                body = postRequest
            }
             emit(DataState.Success(responseFromServer))
        }  catch (e: RedirectResponseException) {
            emit(DataState.Error(e.response.status.description))
        } catch ( e : ClientRequestException) {
             emit(DataState.Error(e.response.status.description))
        } catch (e : ServerResponseException) {
             emit(DataState.Error(e.response.status.description))
        } catch (e : Exception) {
             emit(DataState.Error(e.message.toString()))
        }
    }
}