package ru.androidschool.intensiv.data.database

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import ru.androidschool.intensiv.data.model.movies.Genre
import java.math.BigDecimal

object Converters {
    @TypeConverter
    fun fromGenresListToString(value: List<Genre>) = Json.encodeToString(serializer(), value)

    @TypeConverter
    fun toStringFromGenresList(value: String): List<Genre> =
        Json.decodeFromString<List<Genre>>(value)

    @JvmStatic
    @TypeConverter
    fun bigDecimalFromString(string: String?): BigDecimal? = string?.let(::BigDecimal)

    @JvmStatic
    @TypeConverter
    fun bigDecimalToString(number: BigDecimal?): String? = number?.toString()
}
