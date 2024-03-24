package ru.androidschool.intensiv.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.androidschool.intensiv.data.model.movies.MOVIES_LIKES
import ru.androidschool.intensiv.data.model.movies.MOVIES_TABLE_NAME
import ru.androidschool.intensiv.data.model.movies.MovieEntity
import ru.androidschool.intensiv.data.model.movies.MovieLike

@Dao
interface MovieLikesDao {
    @Query("SELECT EXISTS(SELECT * FROM $MOVIES_LIKES WHERE movieId = :movieId)")
    fun observeIsMovieLiked(movieId: Int): Observable<Boolean>

    @Transaction
    @Query("SELECT * FROM $MOVIES_TABLE_NAME WHERE id in(Select movieId from $MOVIES_LIKES)")
    fun observeLikedMovies(): Observable<List<MovieEntity>>

    @Query("SELECT EXISTS(SELECT * FROM $MOVIES_LIKES WHERE movieId = :movieId)")
    fun getIsMovieLiked(movieId: Int): Single<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addLikeToMovie(like: MovieLike): Completable

    @Query("DELETE FROM $MOVIES_LIKES WHERE movieId = :movieId")
    fun removeLikeFromMovie(movieId: Int): Completable
}
