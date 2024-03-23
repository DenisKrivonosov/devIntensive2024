package ru.androidschool.intensiv.data.model.movies

class MovieDtoMapper {

    fun mapToMovieDto(movie: Movie, movieType: MovieType): MovieDto {
        return MovieDto(
            id = movie.id,
            adult = movie.adult,
            backdropPath = movie.backdropPath,
            genreIds = movie.genreIds,
            originalLanguage = movie.originalLanguage,
            originalTitle = movie.originalTitle,
            overview = movie.overview,
            popularity = movie.popularity,
            posterPath = movie.posterPath,
            releaseDate = movie.releaseDate,
            title = movie.title,
            video = movie.video,
            voteAverage = movie.voteAverage,
            voteCount = movie.voteCount,
            movieType = movieType
        )
    }
}
