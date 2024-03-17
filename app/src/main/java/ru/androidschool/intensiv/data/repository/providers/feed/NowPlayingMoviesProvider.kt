package ru.androidschool.intensiv.data.repository.providers.feed

import io.reactivex.Observable
import ru.androidschool.intensiv.data.database.MoviesDao
import ru.androidschool.intensiv.data.model.movies.MovieDto
import ru.androidschool.intensiv.data.model.movies.MovieDtoMapper
import ru.androidschool.intensiv.data.model.movies.MovieType
import ru.androidschool.intensiv.data.network.MovieApi
import ru.androidschool.intensiv.data.repository.CacheProvider

class NowPlayingMoviesProvider(
    private val dao: MoviesDao,
    private val api: MovieApi,
    private val movieDtoMapper: MovieDtoMapper
) : CacheProvider<List<MovieDto>>() {
    override fun createRemoteObservable(): Observable<List<MovieDto>> {
        return api.getNowPlayingMovies().map {
            it.results.map { movie ->
                movieDtoMapper.mapToMovieDto(
                    movie = movie,
                    movieType = MovieType.NOW_PLAYING
                )
            }
        }
            .doOnSuccess { dao.saveMovies(it) }
            .toObservable()
    }

    override fun createCacheObservable(): Observable<List<MovieDto>> {
        return dao.getMoviesByType(movieType = MovieType.NOW_PLAYING).toObservable()
    }
}
