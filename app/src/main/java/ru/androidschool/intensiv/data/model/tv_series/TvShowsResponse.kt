package ru.androidschool.intensiv.data.model.tv_series

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class TvShowsResponse(

    val page: Int,
    val results: List<TvShow>
)
