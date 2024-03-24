package ru.androidschool.intensiv.data.repository

import io.reactivex.Observable
import io.reactivex.Single
import ru.androidschool.intensiv.MovieFinderApp
import ru.androidschool.intensiv.data.database.MoviesDatabase
import ru.androidschool.intensiv.data.model.movies.MovieCreditsResponse
import ru.androidschool.intensiv.data.model.movies.MovieDetails
import ru.androidschool.intensiv.data.model.movies.MovieVO
import ru.androidschool.intensiv.data.model.movies.mapper.MovieDtoToMovieWithMovieTypeDtoMapper
import ru.androidschool.intensiv.data.model.movies.mapper.MovieEntityToVOMapper
import ru.androidschool.intensiv.data.model.movies.mapper.MovieWithMovieTypeDtoToEntityMapper
import ru.androidschool.intensiv.data.model.movies.mapper.MovieWithMovieTypeDtoToVOMapper
import ru.androidschool.intensiv.data.model.tv_series.TvShowsResponse
import ru.androidschool.intensiv.data.network.MovieApiClient
import ru.androidschool.intensiv.data.repository.providers.feed.NowPlayingMoviesProvider
import ru.androidschool.intensiv.data.repository.providers.feed.PopularMoviesProvider
import ru.androidschool.intensiv.data.repository.providers.feed.UpcomingMoviesProvider

interface MoviesRepository {
    fun getNowPlayingMovies(): Observable<List<MovieVO>>

    fun getUpcomingMovies(): Observable<List<MovieVO>>

    fun getPopularMovies(): Observable<List<MovieVO>>

    fun observeLikedMovies(): Observable<List<MovieVO>>

    fun getPopularTvShows(
        page: Int = 1,
        language: String = "ru"
    ): Single<TvShowsResponse>

    fun getMovieDetails(
        movieId: Int,
        language: String = "ru"
    ): Single<MovieDetails>

    fun getMovieCredits(
        movieId: Int,
        language: String = "ru"
    ): Single<MovieCreditsResponse>
}

object MoviesRepositoryImpl : MoviesRepository {

    private val api = MovieApiClient.apiClient
    private val dao = MoviesDatabase.get(MovieFinderApp.instance!!.applicationContext).moviesDao()
    private val likesDao = MoviesDatabase.get(
        MovieFinderApp.instance!!.applicationContext
    ).movieLikesDao()
    private val moviedtoEnricherMapper = MovieDtoToMovieWithMovieTypeDtoMapper()
    private val movieDtoEntityMapper = MovieWithMovieTypeDtoToEntityMapper()
    private val movieEntityToVoMapper = MovieEntityToVOMapper()
    private val movieDtoToVoMapper = MovieWithMovieTypeDtoToVOMapper()


    private val nowPlayingMoviesProvider = NowPlayingMoviesProvider(
        api = api,
        dao = dao,
        dtoEnricherMapper = moviedtoEnricherMapper,
        dtoToEntityMapper = movieDtoEntityMapper,
        entityToVoMapper = movieEntityToVoMapper,
        dtoToVoMapper = movieDtoToVoMapper
    )

    private val upcomingMoviesProvider = UpcomingMoviesProvider(
        api = api,
        dao = dao,
        dtoEnricherMapper = moviedtoEnricherMapper,
        dtoToEntityMapper = movieDtoEntityMapper,
        entityToVoMapper = movieEntityToVoMapper,
        dtoToVoMapper = movieDtoToVoMapper
    )

    private val popularMoviesProvider = PopularMoviesProvider(
        api = api,
        dao = dao,
        dtoEnricherMapper = moviedtoEnricherMapper,
        dtoToEntityMapper = movieDtoEntityMapper,
        entityToVoMapper = movieEntityToVoMapper,
        dtoToVoMapper = movieDtoToVoMapper
    )

    override fun getNowPlayingMovies(): Observable<List<MovieVO>> {
        return nowPlayingMoviesProvider.getObservable()
    }

    override fun getUpcomingMovies(): Observable<List<MovieVO>> {
        return upcomingMoviesProvider.getObservable()
    }

    override fun getPopularMovies(): Observable<List<MovieVO>> {
        return popularMoviesProvider.getObservable()
    }

    override fun observeLikedMovies(): Observable<List<MovieVO>> {
        return likesDao.observeLikedMovies().map {
            movieEntityToVoMapper.toViewObject(it)
        }
    }

    override fun getPopularTvShows(
        page: Int,
        language: String
    ): Single<TvShowsResponse> {
        return MovieApiClient.apiClient.getPopularTvShows(page, language)
    }

    override fun getMovieDetails(
        movieId: Int,
        language: String
    ): Single<MovieDetails> {
        return MovieApiClient.apiClient.getMovieDetails(movieId, language)
    }

    override fun getMovieCredits(
        movieId: Int,
        language: String
    ): Single<MovieCreditsResponse> {
        return MovieApiClient.apiClient.getMovieCredits(movieId, language)
    }
}
