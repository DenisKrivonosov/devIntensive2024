package ru.androidschool.intensiv.data.model.movies.mapper

import ru.androidschool.intensiv.data.mapper.EntityToViewObjectMapper
import ru.androidschool.intensiv.data.model.movies.MovieEntity
import ru.androidschool.intensiv.data.model.movies.MovieVO

class MovieEntityToVOMapper : EntityToViewObjectMapper<MovieEntity, MovieVO> {

    override fun toViewObject(entity: MovieEntity): MovieVO {
        return MovieVO(
            id = entity.id,
            adult = entity.adult,
            backdropPath = entity.backdropPath,
            genreIds = entity.genreIds,
            originalLanguage = entity.originalLanguage,
            originalTitle = entity.originalTitle,
            overview = entity.overview,
            popularity = entity.popularity,
            posterPath = entity.posterPath,
            releaseDate = entity.releaseDate,
            title = entity.title,
            video = entity.video,
            voteAverage = entity.voteAverage,
            voteCount = entity.voteCount,
            movieType = entity.movieType
        )
    }
}
