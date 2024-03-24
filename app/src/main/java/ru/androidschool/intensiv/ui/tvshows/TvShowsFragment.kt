package ru.androidschool.intensiv.ui.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.disposables.CompositeDisposable
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.repository.MoviesRepository
import ru.androidschool.intensiv.databinding.TvShowsFragmentBinding
import ru.androidschool.intensiv.ext.applySchedulers
import ru.androidschool.intensiv.ui.tvshows.recycler.DividerDecoration
import ru.androidschool.intensiv.ui.tvshows.recycler.TvShowItem
import timber.log.Timber

class TvShowsFragment : Fragment(R.layout.tv_shows_fragment) {

    private val binding: TvShowsFragmentBinding by viewBinding(CreateMethod.INFLATE)
    private val compositeDisposable = CompositeDisposable()

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

        val popularTvShowsSource = MoviesRepository.getPopularTvShows(language = "ru")

        val popularTvShowsSourceDisposable = popularTvShowsSource
            .applySchedulers()
            .subscribe(
                { response ->
                    val moviesList = response.results.map {
                        TvShowItem(it) {}
                    }
                    binding.tvShowsRecyclerView.adapter = adapter.apply { addAll(moviesList) }
                },
                { error ->
                    // Log error here since request failed
                    Timber.e(TAG, error.toString())
                }
            )
        compositeDisposable.add(popularTvShowsSourceDisposable)
    }

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    private fun initUi() {
        binding.tvShowsRecyclerView.addItemDecoration(DividerDecoration())
    }

    companion object {

        private val TAG = TvShowsFragment::class.java.simpleName
    }
}
