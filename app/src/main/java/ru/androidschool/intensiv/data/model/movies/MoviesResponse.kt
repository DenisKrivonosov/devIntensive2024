package ru.androidschool.intensiv.data.model.movies

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class MoviesResponse(

    @SerialName("dates")
    val datesRange: MoviesDatesRange? = null,
    val page: Int,
    val results: List<MovieDto>
)
