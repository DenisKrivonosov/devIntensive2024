package ru.androidschool.intensiv.data.repository.providers.feed

import io.reactivex.Observable
import ru.androidschool.intensiv.data.database.MoviesDao
import ru.androidschool.intensiv.data.model.movies.MovieDto
import ru.androidschool.intensiv.data.model.movies.MovieDtoMapper
import ru.androidschool.intensiv.data.model.movies.MovieType
import ru.androidschool.intensiv.data.network.MovieApi
import ru.androidschool.intensiv.data.repository.CacheProvider

class PopularMoviesProvider(
    private val dao: MoviesDao,
    private val api: MovieApi,
    private val movieDtoMapper: MovieDtoMapper
) : CacheProvider<List<MovieDto>>() {
    override fun createRemoteObservable(): Observable<List<MovieDto>> {
        return api.getPopularMovies().map {
            it.results.map { movie ->
                movieDtoMapper.mapToMovieDto(
                    movie = movie,
                    movieType = MovieType.POPULAR
                )
            }
        }
            .doOnSuccess { dao.saveMovies(it) }
            .toObservable()
    }

    override fun createCacheObservable(): Observable<List<MovieDto>> {

        return dao.getMoviesByType(movieType = MovieType.POPULAR).toObservable()
    }
}
