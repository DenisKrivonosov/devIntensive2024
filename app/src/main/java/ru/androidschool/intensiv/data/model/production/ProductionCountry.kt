package ru.androidschool.intensiv.data.model.production

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class ProductionCountry(

    @SerialName("iso_3166_1")
    val countryShort: String,

    val name: String
)
