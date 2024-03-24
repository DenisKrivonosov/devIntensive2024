package ru.androidschool.intensiv.data.model.movies.mapper

import ru.androidschool.intensiv.data.mapper.DtoToViewObjectMapper
import ru.androidschool.intensiv.data.model.movies.MovieVO
import ru.androidschool.intensiv.data.model.movies.MovieWithMovieTypeDto

class MovieWithMovieTypeDtoToVOMapper : DtoToViewObjectMapper<MovieWithMovieTypeDto, MovieVO> {

    override fun toViewObject(dto: MovieWithMovieTypeDto): MovieVO {
        return MovieVO(
            id = dto.id,
            adult = dto.adult,
            backdropPath = dto.backdropPath,
            genreIds = dto.genreIds,
            originalLanguage = dto.originalLanguage,
            originalTitle = dto.originalTitle,
            overview = dto.overview,
            popularity = dto.popularity,
            posterPath = dto.posterPath,
            releaseDate = dto.releaseDate,
            title = dto.title,
            video = dto.video,
            voteAverage = dto.voteAverage,
            voteCount = dto.voteCount,
            movieType = dto.movieType
        )
    }
}
