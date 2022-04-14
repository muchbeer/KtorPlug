package com.muchbeer.ktorplug.data

import com.muchbeer.ktorplug.utility.logs
import kotlinx.coroutines.flow.*


    inline fun <T> networkBoundResource(
        crossinline fetch: suspend () -> T,
        crossinline saveFetchResult: suspend (T) -> Unit,

    ) = flow {

        emit("")
            try {
                saveFetchResult(fetch())

            } catch (throwable: Throwable) {
                logs("NETLOC", "error is : ${throwable.message}")
            }



    }

