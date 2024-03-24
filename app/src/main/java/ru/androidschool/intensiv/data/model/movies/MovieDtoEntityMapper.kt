package ru.androidschool.intensiv.data.model.movies

class MovieDtoToEntityMapper {

    fun mapToMovieEntity(movie: Movie, movieType: MovieType): MovieEntity {
        return MovieEntity(
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
