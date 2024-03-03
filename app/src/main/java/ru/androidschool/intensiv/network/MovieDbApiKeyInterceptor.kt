package ru.androidschool.intensiv.network

import okhttp3.Interceptor
import okhttp3.Response
import ru.androidschool.intensiv.MovieFinderApp.Companion.API_KEY

internal class MovieDbApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newUrl = chain.request().url
            .newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()
        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(newRequest)
    }
}