package ru.androidschool.intensiv.data.repository

import io.reactivex.Observable
import io.reactivex.Single
import ru.androidschool.intensiv.MovieFinderApp
import ru.androidschool.intensiv.data.database.MoviesDatabase
import ru.androidschool.intensiv.data.model.movies.MovieCreditsResponse
import ru.androidschool.intensiv.data.model.movies.MovieDetails
import ru.androidschool.intensiv.data.model.movies.MovieDtoToEntityMapper
import ru.androidschool.intensiv.data.model.movies.MovieEntity
import ru.androidschool.intensiv.data.model.tv_series.TvShowsResponse
import ru.androidschool.intensiv.data.network.MovieApiClient
import ru.androidschool.intensiv.data.repository.providers.feed.NowPlayingMoviesProvider
import ru.androidschool.intensiv.data.repository.providers.feed.PopularMoviesProvider
import ru.androidschool.intensiv.data.repository.providers.feed.UpcomingMoviesProvider

object MoviesRepository {

    private val api = MovieApiClient.apiClient
    private val dao = MoviesDatabase.get(MovieFinderApp.instance!!.applicationContext).moviesDao()
    private val movieDtoEntityMapper = MovieDtoToEntityMapper()

    private val nowPlayingMoviesProvider = NowPlayingMoviesProvider(
        api = api,
        dao = dao,
        mapper = movieDtoEntityMapper
    )

    private val upcomingMoviesProvider = UpcomingMoviesProvider(
        api = api,
        dao = dao,
        mapper = movieDtoEntityMapper
    )

    private val popularMoviesProvider = PopularMoviesProvider(
        api = api,
        dao = dao,
        mapper = movieDtoEntityMapper
    )

    fun getNowPlayingMovies(): Observable<List<MovieEntity>> {
        return nowPlayingMoviesProvider.getObservable()
    }

    fun getUpcomingMovies(): Observable<List<MovieEntity>> {
        return upcomingMoviesProvider.getObservable()
    }

    fun getPopularMovies(): Observable<List<MovieEntity>> {
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
}
