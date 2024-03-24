package ru.androidschool.intensiv.data.model.movies

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

internal const val MOVIES_TABLE_NAME = "movies"

@Entity(tableName = MOVIES_TABLE_NAME)
data class MovieEntity(
    @PrimaryKey val id: Int,
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: List<Genre>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: BigDecimal,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: BigDecimal,
    val voteCount: String,
    val movieType: MovieType
)
