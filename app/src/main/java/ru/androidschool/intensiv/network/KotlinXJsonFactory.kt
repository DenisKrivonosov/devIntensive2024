package ru.androidschool.intensiv.network

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual

object KotlinXJsonFactory {

    @OptIn(ExperimentalSerializationApi::class)
    fun createJson() = Json {
        encodeDefaults = true
        isLenient = true
        ignoreUnknownKeys = true
        coerceInputValues = true
        serializersModule = SerializersModule {
            contextual(BigDecimalNumericSerializer())
        }
    }
}
