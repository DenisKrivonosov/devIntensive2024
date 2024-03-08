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
import ru.androidschool.intensiv.data.model.common.Cast
import ru.androidschool.intensiv.network.BigDecimalNumericSerializer
import java.math.BigDecimal

private const val POSTER_PATH_PREFIX = "https://image.tmdb.org/t/p/w440_and_h660_face/"

internal object CastDeserializer : KSerializer<Cast> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Movie")

    override fun serialize(encoder: Encoder, value: Cast) {
        throw UnsupportedOperationException("Serialization of NotActivatedLoan is not supported")
    }

    override fun deserialize(decoder: Decoder): Cast {
        require(decoder is JsonDecoder)
        val rootElement = decoder.decodeJsonElement()
        val remote = decoder.json.decodeFromJsonElement<RemoteCast>(rootElement)

        return Cast(
            adult = remote.adult,
            gender = remote.gender,
            id = remote.id,
            knownForDepartment = remote.knownForDepartment,
            name = remote.name,
            originalName = remote.originalName,
            popularity = remote.popularity,
            profilePath = if (remote.profilePath != null) {
                "${POSTER_PATH_PREFIX}${remote.profilePath}"
            } else {
                null
            },
            castId = remote.castId,
            character = remote.character,
            creditId = remote.creditId,
            order = remote.order
        )
    }

    @Keep
    @Serializable
    private class RemoteCast @OptIn(ExperimentalSerializationApi::class) constructor(

        val adult: Boolean,
        val gender: Int,
        val id: Int,

        @SerialName("known_for_department")
        val knownForDepartment: String,

        val name: String,

        @SerialName("original_name")
        val originalName: String,

        @Serializable(with = BigDecimalNumericSerializer::class)
        val popularity: BigDecimal,

        @SerialName("profile_path")
        val profilePath: String?,

        @SerialName("cast_id")
        val castId: Int,

        val character: String,

        @SerialName("credit_id")
        val creditId: String,

        val order: Int
    )
}
