package ru.androidschool.intensiv.domain.usecase

import io.reactivex.Single
import ru.androidschool.intensiv.data.model.tv_series.TvShowsResponse
import ru.androidschool.intensiv.data.repository.MoviesRepository
import ru.androidschool.intensiv.domain.ext.applySchedulers
import java.util.concurrent.TimeUnit

class GetPopularTvShowsUseCase(private val repository: MoviesRepository) {

    fun getTvShows(): Single<TvShowsResponse> {
        return repository.getPopularTvShows().delay(2, TimeUnit.SECONDS)
            .applySchedulers()
    }
}
