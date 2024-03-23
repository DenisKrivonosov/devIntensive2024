package ru.androidschool.intensiv.data.model.movies

import androidx.room.Entity
import androidx.room.PrimaryKey

internal const val MOVIES_LIKES = "movies_likes"

@Entity(tableName = MOVIES_LIKES)
data class MovieLike(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    val movieId: Int,
)
