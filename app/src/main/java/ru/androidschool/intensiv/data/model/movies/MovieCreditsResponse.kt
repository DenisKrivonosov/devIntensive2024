package ru.androidschool.intensiv.data.model.movies

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.androidschool.intensiv.data.model.common.CastMember

@Keep
@Serializable
data class MovieCreditsResponse(
    val id: Int,
    @SerialName("cast")
    val castMembers: List<CastMember>
)
