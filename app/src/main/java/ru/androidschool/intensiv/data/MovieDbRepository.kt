package ru.androidschool.intensiv.data

import retrofit2.Call
import ru.androidschool.intensiv.data.model.movies.MovieCreditsResponse
import ru.androidschool.intensiv.data.model.movies.MovieDetails
import ru.androidschool.intensiv.data.model.movies.MoviesResponse
import ru.androidschool.intensiv.data.model.tv_series.TvShowsResponse
import ru.androidschool.intensiv.network.MovieApiClient

object MovieDbRepository {

    fun getNowPlayingMovies(
        page: Int,
        language: String
    ): Call<MoviesResponse> {
        return MovieApiClient.apiClient.getNowPlayingMovies(page, language)
    }

    fun getUpcomingMovies(
        page: Int,
        language: String
    ): Call<MoviesResponse> {
        return MovieApiClient.apiClient.getUpcomingMovies(page, language)
    }

    fun getPopularMovies(
        page: Int,
        language: String
    ): Call<MoviesResponse> {
        return MovieApiClient.apiClient.getPopularMovies(page, language)
    }

    fun getPopularTvShows(
        page: Int,
        language: String
    ): Call<TvShowsResponse> {
        return MovieApiClient.apiClient.getPopularTvShows(page, language)
    }

    fun getMovieDetails(
        movieId: Int,
        language: String
    ): Call<MovieDetails> {
        return MovieApiClient.apiClient.getMovieDetails(movieId, language)
    }

    fun getMovieCredits(
        movieId: Int,
        language: String
    ): Call<MovieCreditsResponse> {
        return MovieApiClient.apiClient.getMovieCredits(movieId, language)
    }
}
