package ru.androidschool.intensiv.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Maybe
import ru.androidschool.intensiv.data.model.movies.MOVIES_TABLE_NAME
import ru.androidschool.intensiv.data.model.movies.MovieEntity
import ru.androidschool.intensiv.data.model.movies.MovieType

@Dao
interface MoviesDao {

    @Query("Select * from $MOVIES_TABLE_NAME where movieType = :movieType")
    fun getMoviesByType(movieType: MovieType): Maybe<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMovies(movies: List<MovieEntity>)
}
