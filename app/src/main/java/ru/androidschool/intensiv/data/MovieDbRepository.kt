package ru.androidschool.intensiv.data

import io.reactivex.Single
import retrofit2.Call
import ru.androidschool.intensiv.data.model.movies.MovieCreditsResponse
import ru.androidschool.intensiv.data.model.movies.MovieDetails
import ru.androidschool.intensiv.data.model.movies.MoviesResponse
import ru.androidschool.intensiv.data.model.tv_series.TvShowsResponse
import ru.androidschool.intensiv.data.network.MovieApiClient

object MovieDbRepository {

    fun getNowPlayingMovies(
        page: Int = 1,
        language: String
    ): Single<MoviesResponse> {
        return MovieApiClient.apiClient.getNowPlayingMovies(page, language)
    }

    fun getUpcomingMovies(
        page: Int = 1,
        language: String
    ): Single<MoviesResponse> {
        return MovieApiClient.apiClient.getUpcomingMovies(page, language)
    }

    fun getPopularMovies(
        page: Int = 1,
        language: String
    ): Single<MoviesResponse> {
        return MovieApiClient.apiClient.getPopularMovies(page, language)
    }

    fun getPopularTvShows(
        page: Int = 1,
        language: String
    ): Single<TvShowsResponse> {
        return MovieApiClient.apiClient.getPopularTvShows(page, language)
    }

    fun getMovieDetails(
        movieId: Int,
        language: String
    ): Single<MovieDetails> {
        return MovieApiClient.apiClient.getMovieDetails(movieId, language)
    }

    fun getMovieCredits(
        movieId: Int,
        language: String
    ): Single<MovieCreditsResponse> {
        return MovieApiClient.apiClient.getMovieCredits(movieId, language)
    }
}
