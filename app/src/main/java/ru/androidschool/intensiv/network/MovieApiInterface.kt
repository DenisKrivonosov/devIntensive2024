package ru.androidschool.intensiv.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.androidschool.intensiv.data.model.movies.MovieCreditsResponse
import ru.androidschool.intensiv.data.model.movies.MovieDetails
import ru.androidschool.intensiv.data.model.movies.MoviesResponse
import ru.androidschool.intensiv.data.model.tv_series.TvShowsResponse

interface MovieApiInterface {

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("page") page: Int,
        @Query("language") language: String
    ): Call<MoviesResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("page") page: Int,
        @Query("language") language: String
    ): Call<MoviesResponse>

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("page") page: Int,
        @Query("language") language: String
    ): Call<MoviesResponse>

    @GET("tv/popular")
    fun getPopularTvShows(
        @Query("page") page: Int,
        @Query("language") language: String
    ): Call<TvShowsResponse>

    @GET("movie/{movieId}")
    fun getMovieDetails(
        @Path("movieId") page: Int,
        @Query("language") language: String
    ): Call<MovieDetails>

    @GET("movie/{movieId}/credits")
    fun getMovieCredits(
        @Path("movieId") page: Int,
        @Query("language") language: String
    ): Call<MovieCreditsResponse>
}
