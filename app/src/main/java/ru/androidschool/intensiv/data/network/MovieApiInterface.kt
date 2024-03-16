package ru.androidschool.intensiv.data.network

import androidx.annotation.IntRange
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.androidschool.intensiv.data.model.movies.MovieCreditsResponse
import ru.androidschool.intensiv.data.model.movies.MovieDetails
import ru.androidschool.intensiv.data.model.movies.MoviesResponse
import ru.androidschool.intensiv.data.model.tv_series.TvShowsResponse

/**
 * API documentation here https://developer.themoviedb.org/docs/getting-started
 */
interface MovieApiInterface {

    /**
     * API now playing movies: https://developer.themoviedb.org/reference/movie-now-playing-list
     */
    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("language") language: String
    ): Single<MoviesResponse>

    /**
     * API upcoming movies: https://developer.themoviedb.org/reference/movie-upcoming-list
     */
    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("language") language: String
    ): Single<MoviesResponse>

    /**
     * API popular movies: https://developer.themoviedb.org/reference/movie-popular-list
     */
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("language") language: String
    ): Single<MoviesResponse>

    /**
     * API popular tv shows: https://developer.themoviedb.org/reference/tv-series-popular-list
     */
    @GET("tv/popular")
    fun getPopularTvShows(
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("language") language: String
    ): Single<TvShowsResponse>

    /**
     * API movie details: https://developer.themoviedb.org/reference/movie-details
     */
    @GET("movie/{movieId}")
    fun getMovieDetails(
        @Path("movieId") movieId: Int,
        @Query("language") language: String
    ): Single<MovieDetails>

    /**
     * API movie credits: https://developer.themoviedb.org/reference/movie-credits
     */
    @GET("movie/{movieId}/credits")
    fun getMovieCredits(
        @Path("movieId") movieId: Int,
        @Query("language") language: String
    ): Single<MovieCreditsResponse>
}
