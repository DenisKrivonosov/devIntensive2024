package ru.androidschool.intensiv.network.deserializers

import androidx.annotation.Keep
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.decodeFromJsonElement
import ru.androidschool.intensiv.data.model.common.Genre
import ru.androidschool.intensiv.data.model.common.SpokenLanguage
import ru.androidschool.intensiv.data.model.movies.MovieDetails
import ru.androidschool.intensiv.data.model.movies.MoviesCollection
import ru.androidschool.intensiv.data.model.production.ProductionCompany
import ru.androidschool.intensiv.data.model.production.ProductionCountry
import ru.androidschool.intensiv.network.BigDecimalNumericSerializer
import java.math.BigDecimal

private const val POSTER_PATH_PREFIX = "https://image.tmdb.org/t/p/w440_and_h660_face/"

internal object MovieDetailsDeserializer : KSerializer<MovieDetails> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Movie")

    override fun serialize(encoder: Encoder, value: MovieDetails) {
        throw UnsupportedOperationException("Serialization of NotActivatedLoan is not supported")
    }

    override fun deserialize(decoder: Decoder): MovieDetails {
        require(decoder is JsonDecoder)
        val rootElement = decoder.decodeJsonElement()
        val remote = decoder.json.decodeFromJsonElement<MovieDetailsRemote>(rootElement)

        return MovieDetails(
            adult = remote.adult,
            backdropPath = "${POSTER_PATH_PREFIX}${remote.backdropPath}",
            belongsToCollection = remote.belongsToCollection,
            genreIds = remote.genreIds,
            homepage = remote.homepage,
            id = remote.id,
            imdbId = remote.imdbId,
            originalLanguage = remote.originalLanguage,
            originalTitle = remote.originalTitle,
            overview = remote.overview,
            popularity = remote.popularity,
            posterPath = "${POSTER_PATH_PREFIX}${remote.posterPath}",
            productionCompanies = remote.productionCompanies,
            productionCountries = remote.productionCountries,
            releaseDate = remote.releaseDate,
            revenue = remote.revenue,
            runtime = remote.runtime,
            spokenLanguages = remote.spokenLanguages,
            status = remote.status,
            tagline = remote.tagline,
            title = remote.title,
            video = remote.video,
            voteAverage = remote.voteAverage,
            voteCount = remote.voteCount
        )
    }

    @Keep
    @Serializable
    private class MovieDetailsRemote @OptIn(ExperimentalSerializationApi::class) constructor(

        val adult: Boolean,

        @SerialName("backdrop_path")
        val backdropPath: String,

        @SerialName("belongs_to_collection")
        val belongsToCollection: MoviesCollection?,

        @SerialName("genre_ids")
        val genreIds: List<Genre>? = null,

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
    )
}
