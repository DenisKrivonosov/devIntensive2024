package ru.androidschool.intensiv.data.repository

import io.reactivex.Completable
import io.reactivex.Observable
import ru.androidschool.intensiv.MovieFinderApp
import ru.androidschool.intensiv.data.database.MoviesDatabase
import ru.androidschool.intensiv.data.model.movies.MovieEntity
import ru.androidschool.intensiv.data.model.movies.MovieLike

object LikesRepository {

    private val dao = MoviesDatabase.get(
        MovieFinderApp.instance!!.applicationContext
    ).movieLikesDao()

    fun observeLikedMovies(): Observable<List<MovieEntity>> {
        return dao.observeLikedMovies()
    }

    fun observeIsMovieLiked(movieId: Int): Observable<Boolean> {
        return dao.observeIsMovieLiked(movieId)
    }

    fun changeMovieIsLiked(movieId: Int): Completable {
        return dao.getIsMovieLiked(movieId).flatMapCompletable { isLiked ->
            if (isLiked) {
                dislikeMovie(movieId)
            } else {
                likeMovie(movieId)
            }
        }
    }

    private fun likeMovie(movieId: Int): Completable {
        return dao.addLikeToMovie(MovieLike(null, movieId))
    }

    private fun dislikeMovie(movieId: Int): Completable {
        return dao.removeLikeFromMovie(movieId)
    }
}
