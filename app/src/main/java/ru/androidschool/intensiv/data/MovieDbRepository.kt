package ru.androidschool.intensiv.data

import retrofit2.Call
import retrofit2.http.GET
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

    @GET("tv/popular")
    fun getPopularTvShows(
        page: Int,
        language: String
    ): Call<TvShowsResponse> {
        return MovieApiClient.apiClient.getPopularTvShows(page, language)
    }
}
