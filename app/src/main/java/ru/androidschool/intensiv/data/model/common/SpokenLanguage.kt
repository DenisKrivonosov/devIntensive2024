package ru.androidschool.intensiv.data.model.common

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class SpokenLanguage(

    @SerialName("english_name")
    val englishName: String,

    @SerialName("iso_639_1")
    val englishNameShort: String,

    val name: String
)
