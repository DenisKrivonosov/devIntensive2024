package ru.androidschool.intensiv.network.deserializer

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
import ru.androidschool.intensiv.data.model.tv_series.TvShow
import ru.androidschool.intensiv.network.BigDecimalNumericSerializer
import java.math.BigDecimal

private const val POSTER_PATH_PREFIX = "https://image.tmdb.org/t/p/w440_and_h660_face/"

internal object TvShowDeserializer : KSerializer<TvShow> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("TvSerial")

    override fun serialize(encoder: Encoder, value: TvShow) {
        throw UnsupportedOperationException("Serialization of NotActivatedLoan is not supported")
    }

    override fun deserialize(decoder: Decoder): TvShow {
        require(decoder is JsonDecoder)
        val rootElement = decoder.decodeJsonElement()
        val remote = decoder.json.decodeFromJsonElement<TvShowRemote>(rootElement)

        return TvShow(
            id = remote.id,
            adult = remote.adult,
            backdropPath = if (remote.backdropPath != null) {
                POSTER_PATH_PREFIX + remote.backdropPath
            } else {
                null
            },
            genreIds = remote.genreIds,
            originalLanguage = remote.originalLanguage,
            originalName = remote.originalName,
            overview = remote.overview,
            popularity = remote.popularity,
            posterPath = POSTER_PATH_PREFIX + remote.posterPath,
            firstAirDate = remote.firstAirDate,
            originalCountry = remote.originalCountry,
            voteAverage = remote.voteAverage,
            voteCount = remote.voteCount,
            name = remote.name
        )
    }

    @Keep
    @Serializable
    private class TvShowRemote @OptIn(ExperimentalSerializationApi::class) constructor(
        val adult: Boolean,

        @SerialName("backdrop_path")
        val backdropPath: String?,

        @SerialName("genre_ids")
        val genreIds: List<String>,

        val id: Int,

        @SerialName("original_country")
        val originalCountry: List<String>? = null,

        @SerialName("original_language")
        val originalLanguage: String,

        @SerialName("original_name")
        val originalName: String,

        val overview: String,

        @Serializable(with = BigDecimalNumericSerializer::class)
        val popularity: BigDecimal,

        @SerialName("poster_path")
        val posterPath: String,

        @SerialName("first_air_date")
        val firstAirDate: String,

        val name: String,

        @SerialName("vote_average")
        @Serializable(with = BigDecimalNumericSerializer::class)
        val voteAverage: BigDecimal,

        @SerialName("vote_count")
        val voteCount: String,
    )
}