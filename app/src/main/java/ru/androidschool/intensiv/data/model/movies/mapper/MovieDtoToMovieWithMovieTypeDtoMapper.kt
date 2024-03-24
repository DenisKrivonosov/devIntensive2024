package ru.androidschool.intensiv.data.model.movies.mapper

import ru.androidschool.intensiv.data.model.movies.MovieDto
import ru.androidschool.intensiv.data.model.movies.MovieType
import ru.androidschool.intensiv.data.model.movies.MovieWithMovieTypeDto

class MovieDtoToMovieWithMovieTypeDtoMapper {

    fun mapToMovieWithMovieTypeDto(dto: MovieDto, movieType: MovieType): MovieWithMovieTypeDto {
        return MovieWithMovieTypeDto(
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
            movieType = movieType
        )
    }
}
