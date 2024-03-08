package ru.androidschool.intensiv.data.model.movies

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class MoviesDatesRange(

    @SerialName("minimum")
    val startDate: String,

    @SerialName("maximum")
    val endDate: String
)
