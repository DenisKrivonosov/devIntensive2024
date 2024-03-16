package ru.androidschool.intensiv.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.data.network.KotlinXJsonFactory.createJson

object MovieApiClient {

    private const val APPLICATION_JSON_MIME_TYPE = "application/json"

    private var client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                this.level = HttpLoggingInterceptor.Level.BODY
            } else {
                this.level = HttpLoggingInterceptor.Level.NONE
            }
        })
        .addInterceptor(MovieDbApiKeyInterceptor())
        .build()

    val apiClient: MovieApiInterface by lazy {
        val contentType = APPLICATION_JSON_MIME_TYPE.toMediaType()
        val converterFactory = createJson().asConverterFactory(contentType)
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return@lazy retrofit.create(MovieApiInterface::class.java)
    }
}
