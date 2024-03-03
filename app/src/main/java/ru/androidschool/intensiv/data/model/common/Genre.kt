package ru.androidschool.intensiv.data.model.common

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Genre(
    val id: Int,
    val name: String
)
