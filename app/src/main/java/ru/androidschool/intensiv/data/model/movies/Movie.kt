package ru.androidschool.intensiv.data.model.movies

import androidx.annotation.Keep
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.androidschool.intensiv.network.BigDecimalNumericSerializer
import ru.androidschool.intensiv.network.deserializer.MovieDeserializer
import java.math.BigDecimal

private const val RATING_TO_STARS_RATIO = 2

@Keep
@Serializable(with = MovieDeserializer::class)
data class Movie @OptIn(ExperimentalSerializationApi::class) constructor(
    val adult: Boolean,

    @SerialName("backdrop_path")
    val backdropPath: String,

    @SerialName("genre_ids")
    val genreIds: List<String>,

    val id: Int,

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
) {
    val rating: Float
        get() = voteAverage.div(BigDecimal(RATING_TO_STARS_RATIO)).toFloat()
}