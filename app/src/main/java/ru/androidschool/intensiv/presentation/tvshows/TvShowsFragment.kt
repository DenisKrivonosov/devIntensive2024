package ru.androidschool.intensiv.presentation.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.model.tv_series.TvShow
import ru.androidschool.intensiv.data.repository.MoviesRepositoryImpl
import ru.androidschool.intensiv.databinding.TvShowsFragmentBinding
import ru.androidschool.intensiv.domain.usecase.GetPopularTvShowsUseCase
import ru.androidschool.intensiv.presentation.tvshows.recycler.DividerDecoration
import ru.androidschool.intensiv.presentation.tvshows.recycler.ErrorItem
import ru.androidschool.intensiv.presentation.tvshows.recycler.LoadingItem
import ru.androidschool.intensiv.presentation.tvshows.recycler.TvShowItem

class TvShowsFragment : Fragment(R.layout.tv_shows_fragment), TvShowsPresenter.TvShowsView {

    private val repository = MoviesRepositoryImpl
    private val getPopularTvShowsUseCase = GetPopularTvShowsUseCase(repository)
    private val presenter: TvShowsPresenter by lazy {
        TvShowsPresenter(getPopularTvShowsUseCase)
    }

    private val binding: TvShowsFragmentBinding by viewBinding(CreateMethod.INFLATE)

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        presenter.attachView(this)
        presenter.getTvShows()

    }

    override fun onStop() {
        presenter.clearCompositeDisposable()
        super.onStop()
    }

    override fun showTvShows(movies: List<TvShow>) {
        val moviesList = movies.map { TvShowItem(it) {} }
        binding.tvShowsRecyclerView.adapter = adapter.apply {
            clear()
            addAll(moviesList) }
    }

    override fun showLoading() {
        val loadingItemsList = List(5) { LoadingItem() }
        binding.tvShowsRecyclerView.adapter = adapter.apply {
            clear()
            addAll(loadingItemsList)
        }
    }

    override fun showError() {
        val errorItem = ErrorItem { presenter.getTvShows() }
        binding.tvShowsRecyclerView.adapter = adapter.apply {
            clear()
            add(errorItem)
        }
    }

    private fun initUi() {
        binding.tvShowsRecyclerView.addItemDecoration(DividerDecoration())
    }
}
