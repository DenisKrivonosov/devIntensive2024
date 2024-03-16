package ru.androidschool.intensiv.data.network

import okhttp3.Interceptor
import okhttp3.Response
import ru.androidschool.intensiv.BuildConfig

internal class MovieDbApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newUrl = chain.request().url
            .newBuilder()
            .addQueryParameter("api_key", BuildConfig.THE_MOVIE_DATABASE_API_KEY)
            .build()
        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(newRequest)
    }
}
