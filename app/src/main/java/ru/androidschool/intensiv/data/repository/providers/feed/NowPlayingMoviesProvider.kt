package ru.androidschool.intensiv.data.repository.providers.feed

import io.reactivex.Observable
import ru.androidschool.intensiv.data.database.MoviesDao
import ru.androidschool.intensiv.data.model.movies.MovieDtoToEntityMapper
import ru.androidschool.intensiv.data.model.movies.MovieEntity
import ru.androidschool.intensiv.data.model.movies.MovieType
import ru.androidschool.intensiv.data.network.MovieApi
import ru.androidschool.intensiv.data.repository.CacheProvider

class NowPlayingMoviesProvider(
    private val dao: MoviesDao,
    private val api: MovieApi,
    private val mapper: MovieDtoToEntityMapper
) : CacheProvider<List<MovieEntity>>() {
    override fun createRemoteObservable(): Observable<List<MovieEntity>> {
        return api.getNowPlayingMovies()
            .map {
                it.results.map { movie ->
                    mapper.mapToMovieEntity(
                        movie = movie,
                        movieType = MovieType.NOW_PLAYING
                    )
                }
            }
            .doOnSuccess { dao.saveMovies(it) }
            .toObservable()
    }

    override fun createCacheObservable(): Observable<List<MovieEntity>> {
        return dao.getMoviesByType(movieType = MovieType.NOW_PLAYING).toObservable()
    }
}
