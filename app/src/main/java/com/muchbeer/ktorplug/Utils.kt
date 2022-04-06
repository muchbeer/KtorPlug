package com.muchbeer.ktorplug

object PostConstant {

    private const val _BASE_URL = BuildConfig.BASE_URL
    const val BASE_URL = _BASE_URL + "posts"

    private const val TIME_OUT = 60_000
}

val <T> T.exhaustive : T
    get() = this