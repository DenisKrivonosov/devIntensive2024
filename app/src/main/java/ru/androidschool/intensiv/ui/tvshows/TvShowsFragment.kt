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
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieDbRepository
import ru.androidschool.intensiv.data.model.tv_series.TvShowsResponse
import ru.androidschool.intensiv.databinding.TvShowsFragmentBinding
import timber.log.Timber

class TvShowsFragment : Fragment(R.layout.tv_shows_fragment) {

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

        val popularTvShowsObservable = MovieDbRepository.getPopularTvShows(language = "ru")

        popularTvShowsObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : SingleObserver<TvShowsResponse> {
                    override fun onSubscribe(d: Disposable) {
                        Timber.d(TAG, "subscribed on getPopularMoviesObservable")
                    }

                    override fun onError(e: Throwable) {
                        // Log error here since request failed
                        Timber.e(TAG, e.toString())
                    }

                    override fun onSuccess(response: TvShowsResponse) {
                        val moviesList = response.results.map {
                            TvShowItem(it) {}
                        }
                        binding.tvShowsRecyclerView.adapter = adapter.apply { addAll(moviesList) }
                    }
                }
            )
    }

    companion object {

        private val TAG = TvShowsFragment::class.java.simpleName
    }
}
