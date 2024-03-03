package ru.androidschool.intensiv.data.model.movies

import androidx.annotation.Keep
import kotlinx.serialization.Serializable
import ru.androidschool.intensiv.data.model.common.Cast

@Keep
@Serializable
data class MovieCreditsResponse(
    val id: Int,
    val cast: List<Cast>
)
