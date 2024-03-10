package ru.androidschool.intensiv.data.model.movies

import ru.androidschool.intensiv.data.model.common.CastMember

data class CompositeMovieDetails(
    val movieDetails: MovieDetails,
    val movieCast: List<CastMember>
)
