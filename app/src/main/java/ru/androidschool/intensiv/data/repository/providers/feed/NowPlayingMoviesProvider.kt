package ru.androidschool.intensiv.data.repository.providers.feed

import io.reactivex.Observable
import ru.androidschool.intensiv.data.database.MoviesDao
import ru.androidschool.intensiv.data.model.movies.MovieType.NOW_PLAYING
import ru.androidschool.intensiv.data.model.movies.MovieVO
import ru.androidschool.intensiv.data.model.movies.mapper.MovieDtoToMovieWithMovieTypeDtoMapper
import ru.androidschool.intensiv.data.model.movies.mapper.MovieEntityToVOMapper
import ru.androidschool.intensiv.data.model.movies.mapper.MovieWithMovieTypeDtoToEntityMapper
import ru.androidschool.intensiv.data.model.movies.mapper.MovieWithMovieTypeDtoToVOMapper
import ru.androidschool.intensiv.data.network.MovieApi
import ru.androidschool.intensiv.data.repository.CacheProvider

class NowPlayingMoviesProvider(
    private val dao: MoviesDao,
    private val api: MovieApi,
    private val dtoEnricherMapper: MovieDtoToMovieWithMovieTypeDtoMapper,
    private val dtoToEntityMapper: MovieWithMovieTypeDtoToEntityMapper,
    private val entityToVoMapper: MovieEntityToVOMapper,
    private val dtoToVoMapper: MovieWithMovieTypeDtoToVOMapper,
) : CacheProvider<List<MovieVO>>() {
    override fun createRemoteObservable(): Observable<List<MovieVO>> {
        return api.getNowPlayingMovies()
            .map {
                it.results.map { movie ->
                    dtoEnricherMapper.mapToMovieWithMovieTypeDto(movie, NOW_PLAYING)
                }
            }
            .doOnSuccess { movies ->
                val entityMovies = dtoToEntityMapper.toEntityObject(movies)
                dao.saveMovies(entityMovies)
            }
            .map {
                dtoToVoMapper.toViewObject(it)
            }
            .toObservable()
    }

    override fun createCacheObservable(): Observable<List<MovieVO>> {
        return dao.getMoviesByType(movieType = NOW_PLAYING).toObservable().map {
            entityToVoMapper.toViewObject(it)
        }
    }
}
