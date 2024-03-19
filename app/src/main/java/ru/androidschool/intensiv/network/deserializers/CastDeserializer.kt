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
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.data.model.common.CastMember
import ru.androidschool.intensiv.network.BigDecimalNumericSerializer
import java.math.BigDecimal

internal object CastDeserializer : KSerializer<CastMember> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Movie")

    override fun serialize(encoder: Encoder, value: CastMember) {
        throw UnsupportedOperationException("Serialization of Cast is not supported")
    }

    override fun deserialize(decoder: Decoder): CastMember {
        require(decoder is JsonDecoder)
        val rootElement = decoder.decodeJsonElement()
        val remote = decoder.json.decodeFromJsonElement<RemoteCast>(rootElement)

        return CastMember(
            adult = remote.adult,
            gender = remote.gender,
            id = remote.id,
            knownForDepartment = remote.knownForDepartment,
            name = remote.name,
            originalName = remote.originalName,
            popularity = remote.popularity,
            profilePath = remote.profilePath?.let {
                "${BuildConfig.POSTER_PATH_PREFIX}${remote.profilePath}"
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
