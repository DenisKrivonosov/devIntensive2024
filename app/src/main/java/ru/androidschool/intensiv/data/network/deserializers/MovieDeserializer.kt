package ru.androidschool.intensiv.data.network.deserializers

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
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.data.model.movies.Movie
import ru.androidschool.intensiv.data.network.BigDecimalNumericSerializer
import java.math.BigDecimal

internal object MovieDeserializer : KSerializer<Movie> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Movie")

    override fun serialize(encoder: Encoder, value: Movie) {
        throw UnsupportedOperationException("Serialization of MovieDeserializer is not supported")
    }

    override fun deserialize(decoder: Decoder): Movie {
        require(decoder is JsonDecoder)
        val rootElement = decoder.decodeJsonElement()
        val remote = decoder.json.decodeFromJsonElement<RemoteMovie>(rootElement)

        return Movie(
            id = remote.id,
            adult = remote.adult,
            backdropPath = "${BuildConfig.POSTER_PATH_PREFIX}${remote.backdropPath}",
            genreIds = remote.genreIds,
            originalLanguage = remote.originalLanguage,
            originalTitle = remote.originalTitle,
            overview = remote.overview,
            popularity = remote.popularity,
            posterPath = "${BuildConfig.POSTER_PATH_PREFIX}${remote.posterPath}",
            releaseDate = remote.releaseDate,
            title = remote.title,
            video = remote.video,
            voteAverage = remote.voteAverage,
            voteCount = remote.voteCount,
        )
    }

    @Keep
    @Serializable
    private class RemoteMovie @OptIn(ExperimentalSerializationApi::class) constructor(
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
    )
}
