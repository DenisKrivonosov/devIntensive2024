package ru.androidschool.intensiv.data.model.production

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class ProductionCompany(

    val id: Int,

    @SerialName("logo_path")
    val logoPath: String?,

    val name: String,

    @SerialName("origin_country")
    val originCountry: String,
)
