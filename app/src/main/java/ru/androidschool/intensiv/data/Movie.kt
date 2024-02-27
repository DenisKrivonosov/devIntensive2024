package ru.androidschool.intensiv.data

data class Movie(
    val title: String,
    val posterUrl: String,
    val posterPreviewUrl: String,
    val voteAverage: Double
) {
    val rating: Float
        get() = voteAverage.div(2).toFloat()
}
