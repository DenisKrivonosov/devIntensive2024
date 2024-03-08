package ru.androidschool.intensiv.data.model.tv_series

import androidx.annotation.Keep
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.androidschool.intensiv.network.BigDecimalNumericSerializer
import ru.androidschool.intensiv.network.deserializers.TvShowDeserializer
import java.math.BigDecimal

private const val RATING_TO_STARS_RATIO = 2

@Keep
@Serializable(with = TvShowDeserializer::class)
data class TvShow @OptIn(ExperimentalSerializationApi::class) constructor(
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
    val posterPath: String?,

    @SerialName("first_air_date")
    val firstAirDate: String,

    val name: String,

    @SerialName("vote_average")
    @Serializable(with = BigDecimalNumericSerializer::class)
    val voteAverage: BigDecimal,

    @SerialName("vote_count")
    val voteCount: String,
) {
    val rating: Float
        get() = voteAverage.div(BigDecimal(RATING_TO_STARS_RATIO)).toFloat()
}
