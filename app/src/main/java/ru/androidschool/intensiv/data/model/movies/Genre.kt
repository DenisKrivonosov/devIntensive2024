package ru.androidschool.intensiv.data.model.movies

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

internal const val GENRES_TABLE_NAME = "genres"

@Serializable
@Entity(tableName = GENRES_TABLE_NAME)
data class Genre(@PrimaryKey val id: String)
