package ru.androidschool.intensiv.presentation.tvshows

import android.annotation.SuppressLint
import io.reactivex.disposables.CompositeDisposable
import ru.androidschool.intensiv.data.model.tv_series.TvShow
import ru.androidschool.intensiv.domain.usecase.GetPopularTvShowsUseCase
import ru.mikhailskiy.intensiv.presentation.base.BasePresenter
import timber.log.Timber

class TvShowsPresenter(private val useCase: GetPopularTvShowsUseCase) :
    BasePresenter<TvShowsPresenter.TvShowsView>() {

    private val compositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    fun getTvShows() {
        val disposable = useCase.getTvShows()
            .doOnSubscribe {
                view?.showLoading()
            }
            .subscribe(
                { response ->
                    view?.showTvShows(response.results)
                },
                { error ->
                    view?.showError()
                    // Log error here since request failed
                    Timber.e("GetPopularTvShowsUseCase", error.toString())
                }
            )
        compositeDisposable.add(disposable)
    }

    fun clearCompositeDisposable() {
        compositeDisposable.clear()
    }

    interface TvShowsView {
        fun showLoading()
        fun showError()
        fun showTvShows(movies: List<TvShow>)
    }
}
