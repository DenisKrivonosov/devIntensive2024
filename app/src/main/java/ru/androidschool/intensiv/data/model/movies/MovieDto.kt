package ru.androidschool.intensiv.data.model.movies

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.androidschool.intensiv.data.network.BigDecimalNumericSerializer
import java.math.BigDecimal

private const val RATING_TO_STARS_RATIO = 2
internal const val MOVIES_TABLE_NAME = "movies"

@Entity(tableName = MOVIES_TABLE_NAME)
data class MovieDto @OptIn(ExperimentalSerializationApi::class) constructor(
    @PrimaryKey val id: Int,

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

    val movieType: MovieType
) {
    val rating: Float
        get() = voteAverage.div(BigDecimal(RATING_TO_STARS_RATIO)).toFloat()
}
