package ru.androidschool.intensiv.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.androidschool.intensiv.data.model.movies.MoviesResponse
import ru.androidschool.intensiv.data.model.tv_series.TvShowsResponse

interface MovieApiInterface {

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("page") page: Int,
        @Query("language") language: String
    ): Call<MoviesResponse>

    @GET("tv/popular")
    fun getPopularTvShows(
        @Query("page") page: Int,
        @Query("language") language: String
    ): Call<TvShowsResponse>
}
