package ru.androidschool.intensiv.data.repository

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.androidschool.intensiv.MovieFinderApp
import ru.androidschool.intensiv.data.database.MoviesDatabase
import ru.androidschool.intensiv.data.model.movies.MovieCreditsResponse
import ru.androidschool.intensiv.data.model.movies.MovieDetails
import ru.androidschool.intensiv.data.model.movies.MovieDto
import ru.androidschool.intensiv.data.model.movies.MovieDtoMapper
import ru.androidschool.intensiv.data.model.movies.MovieLike
import ru.androidschool.intensiv.data.model.tv_series.TvShowsResponse
import ru.androidschool.intensiv.data.network.MovieApiClient
import ru.androidschool.intensiv.data.repository.providers.feed.NowPlayingMoviesProvider
import ru.androidschool.intensiv.data.repository.providers.feed.PopularMoviesProvider
import ru.androidschool.intensiv.data.repository.providers.feed.UpcomingMoviesProvider

object MovieDbRepository {

    private val api = MovieApiClient.apiClient
    private val dao = MoviesDatabase.get(MovieFinderApp.instance!!.applicationContext).moviesDao()
    private val movieDtoMapper = MovieDtoMapper()

    private val nowPlayingMoviesProvider = NowPlayingMoviesProvider(
        api = api,
        dao = dao,
        movieDtoMapper = movieDtoMapper
    )

    private val upcomingMoviesProvider = UpcomingMoviesProvider(
        api = api,
        dao = dao,
        movieDtoMapper = movieDtoMapper
    )

    private val popularMoviesProvider = PopularMoviesProvider(
        api = api,
        dao = dao,
        movieDtoMapper = movieDtoMapper
    )

    fun getNowPlayingMovies(): Observable<List<MovieDto>> {
        return nowPlayingMoviesProvider.getObservable()
    }

    fun getUpcomingMovies(): Observable<List<MovieDto>> {
        return upcomingMoviesProvider.getObservable()
    }

    fun getPopularMovies(): Observable<List<MovieDto>> {
        return popularMoviesProvider.getObservable()
    }

    fun getPopularTvShows(
        page: Int = 1,
        language: String
    ): Single<TvShowsResponse> {
        return MovieApiClient.apiClient.getPopularTvShows(page, language)
    }

    fun getMovieDetails(
        movieId: Int,
        language: String = "ru"
    ): Single<MovieDetails> {
        return MovieApiClient.apiClient.getMovieDetails(movieId, language)
    }

    fun getMovieCredits(
        movieId: Int,
        language: String = "ru"
    ): Single<MovieCreditsResponse> {
        return MovieApiClient.apiClient.getMovieCredits(movieId, language)
    }

    fun observeLikedMovies(): Observable<List<MovieDto>> {
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
