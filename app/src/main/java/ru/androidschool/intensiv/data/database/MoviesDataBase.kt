package ru.androidschool.intensiv.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.androidschool.intensiv.data.model.movies.Genre
import ru.androidschool.intensiv.data.model.movies.MovieEntity
import ru.androidschool.intensiv.data.model.movies.MovieLike

@Database(
    entities = [
        MovieEntity::class,
        Genre::class,
        MovieLike::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
    abstract fun movieLikesDao(): MovieLikesDao

    companion object {
        private var instance: MoviesDatabase? = null

        @Synchronized
        fun get(context: Context): MoviesDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoviesDatabase::class.java, "MoviesDatabase"
                ).build()
            }
            return instance!!
        }
    }
}
