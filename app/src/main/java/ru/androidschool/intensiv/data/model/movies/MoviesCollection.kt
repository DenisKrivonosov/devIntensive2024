package ru.androidschool.intensiv.data.model.movies

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.androidschool.intensiv.data.network.deserializers.MoviesCollectionDeserializer

@Keep
@Serializable(with = MoviesCollectionDeserializer::class)
data class MoviesCollection(

    val id: Int,
    val name: String,

    @SerialName("poster_path")
    val posterPath: String,

    @SerialName("backdrop_path")
    val backdropPath: String,
)
