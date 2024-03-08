package ru.androidschool.intensiv.data.model.movies

import androidx.annotation.Keep
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.androidschool.intensiv.data.model.common.Genre
import ru.androidschool.intensiv.data.model.common.SpokenLanguage
import ru.androidschool.intensiv.data.model.production.ProductionCompany
import ru.androidschool.intensiv.data.model.production.ProductionCountry
import ru.androidschool.intensiv.network.BigDecimalNumericSerializer
import ru.androidschool.intensiv.network.deserializers.MovieDetailsDeserializer
import java.math.BigDecimal

private const val RATING_TO_STARS_RATIO = 2

@Keep
@Serializable(with = MovieDetailsDeserializer::class)
data class MovieDetails @OptIn(ExperimentalSerializationApi::class) constructor(
    val adult: Boolean,

    @SerialName("backdrop_path")
    val backdropPath: String,

    @SerialName("belongs_to_collection")
    val belongsToCollection: MoviesCollection?,

    @SerialName("genre_ids")
    val genreIds: List<Genre>?,

    val homepage: String,
    val id: Int,

    @SerialName("imdb_id")
    val imdbId: String,

    @SerialName("original_language")
    val originalLanguage: String,

    @SerialName("original_title")
    val originalTitle: String,

    val overview: String,

    @Serializable(with = BigDecimalNumericSerializer::class)
    val popularity: BigDecimal,

    @SerialName("poster_path")
    val posterPath: String,

    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompany>,

    @SerialName("production_countries")
    val productionCountries: List<ProductionCountry>,

    @SerialName("release_date")
    val releaseDate: String,

    val revenue: Int,
    val runtime: Int,

    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,

    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,

    @SerialName("vote_average")
    @Serializable(with = BigDecimalNumericSerializer::class)
    val voteAverage: BigDecimal,

    @SerialName("vote_count")
    val voteCount: String
) {
    val rating: Float
        get() = voteAverage.div(BigDecimal(RATING_TO_STARS_RATIO)).toFloat()
}
