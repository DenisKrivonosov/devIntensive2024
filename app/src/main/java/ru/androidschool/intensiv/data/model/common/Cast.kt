package ru.androidschool.intensiv.data.model.common

import androidx.annotation.Keep
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.androidschool.intensiv.network.BigDecimalNumericSerializer
import ru.androidschool.intensiv.network.deserializers.CastDeserializer
import java.math.BigDecimal

@Keep
@Serializable(with = CastDeserializer::class)
data class Cast @OptIn(ExperimentalSerializationApi::class) constructor(

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
