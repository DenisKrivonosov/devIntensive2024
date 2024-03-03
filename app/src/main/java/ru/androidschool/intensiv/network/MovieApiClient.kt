package ru.androidschool.intensiv.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.androidschool.intensiv.network.KotlinXJsonFactory.createJson

object MovieApiClient {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val APPLICATION_JSON_MIME_TYPE = "application/json"

    private var client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor(MovieDbApiKeyInterceptor())
        .build()

    val apiClient: MovieApiInterface by lazy {
        val contentType = APPLICATION_JSON_MIME_TYPE.toMediaType()
        val converterFactory = createJson().asConverterFactory(contentType)
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(converterFactory)
            .build()

        return@lazy retrofit.create(MovieApiInterface::class.java)
    }
}
