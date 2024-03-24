package ru.androidschool.intensiv.data.model.movies

import androidx.annotation.Keep
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.androidschool.intensiv.data.network.BigDecimalNumericSerializer
import ru.androidschool.intensiv.data.network.deserializers.MovieDeserializer
import java.math.BigDecimal

@Keep
@Serializable(with = MovieDeserializer::class)
data class MovieDto @OptIn(ExperimentalSerializationApi::class) constructor(
    val id: Int,

    val adult: Boolean,

    @SerialName("backdrop_path")
    val backdropPath: String,

    @SerialName("genre_ids")
    val genreIds: List<Genre>,

    @SerialName("original_language")
    val originalLanguage: String,

    @SerialName("original_title")
    val originalTitle: String,

    val overview: String,

    @Serializable(with = BigDecimalNumericSerializer::class)
    val popularity: BigDecimal,

    @SerialName("poster_path")
    val posterPath: String,

    @SerialName("release_date")
    val releaseDate: String,

    val title: String,
    val video: Boolean,

    @SerialName("vote_average")
    @Serializable(with = BigDecimalNumericSerializer::class)
    val voteAverage: BigDecimal,

    @SerialName("vote_count")
    val voteCount: String,
)
