package ru.androidschool.intensiv.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieDbRepository
import ru.androidschool.intensiv.data.model.movies.Movie
import ru.androidschool.intensiv.data.model.movies.MoviesResponse
import ru.androidschool.intensiv.databinding.FeedFragmentBinding
import ru.androidschool.intensiv.databinding.FeedHeaderBinding
import ru.androidschool.intensiv.ui.movie_details.MovieDetailsFragment.Companion.KEY_MOVIE_ID
import timber.log.Timber

class FeedFragment : Fragment(R.layout.feed_fragment) {

    private val binding: FeedFragmentBinding by viewBinding(CreateMethod.INFLATE)
    private val searchBinding by viewBinding(FeedHeaderBinding::bind)

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private val options = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
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

        searchBinding.searchToolbar.onTextChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<String> {
                    override fun onSubscribe(d: Disposable) {
                        Timber.d(TAG, "subscribed on searchBarObservable")
                    }

                    override fun onNext(newText: String) {
                        Timber.d(TAG, "onNext on searchBindingObservable")
                        openSearch(newText)
                    }

                    override fun onError(e: Throwable) {
                        Timber.d(TAG, "onError on searchBindingObservable")
                        // Log error here since request failed
                        Timber.e(TAG, e.toString())
                    }

                    override fun onComplete() {
                        Timber.d(TAG, "onComplete on searchBindingObservable")
                    }
                }
            )

        val nowPlayingMoviesObservable = MovieDbRepository.getNowPlayingMovies(language = "ru")
        val upcomingMoviesObservable = MovieDbRepository.getUpcomingMovies(language = "ru")
        val getPopularMoviesObservable = MovieDbRepository.getPopularMovies(language = "ru")

        nowPlayingMoviesObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : SingleObserver<MoviesResponse> {
                    override fun onSubscribe(d: Disposable) {
                        Timber.d(TAG, "subscribed on nowPlayingMoviesObservable")
                    }

                    override fun onError(e: Throwable) {
                        // Log error here since request failed
                        Timber.e(TAG, e.toString())
                    }

                    override fun onSuccess(response: MoviesResponse) {
                        val moviesList = response.results.map {
                            MovieItem(it) { movie -> openMovieDetails(movie) }
                        }
                        binding.moviesRecyclerView.adapter = adapter.apply { addAll(moviesList) }
                    }
                }
            )

        upcomingMoviesObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : SingleObserver<MoviesResponse> {
                    override fun onSubscribe(d: Disposable) {
                        Timber.d(TAG, "subscribed on upcomingMoviesObservable")
                    }

                    override fun onError(e: Throwable) {
                        // Log error here since request failed
                        Timber.e(TAG, e.toString())
                    }

                    override fun onSuccess(response: MoviesResponse) {
                        Timber.d(TAG, response.results.toString())
                    }
                }
            )

        getPopularMoviesObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : SingleObserver<MoviesResponse> {
                    override fun onSubscribe(d: Disposable) {
                        Timber.d(TAG, "subscribed on getPopularMoviesObservable")
                    }

                    override fun onError(e: Throwable) {
                        // Log error here since request failed
                        Timber.e(TAG, e.toString())
                    }

                    override fun onSuccess(response: MoviesResponse) {
                        Timber.d(TAG, response.results.toString())
                    }
                }
            )
    }

    private fun openMovieDetails(movie: Movie) {
        val bundle = Bundle()
        bundle.putInt(KEY_MOVIE_ID, movie.id)
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }

    private fun openSearch(searchText: String) {
        val bundle = Bundle()
        bundle.putString(KEY_SEARCH, searchText)
        findNavController().navigate(R.id.search_dest, bundle, options)
    }

    override fun onStop() {
        super.onStop()
        searchBinding.searchToolbar.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    companion object {
        private val TAG = FeedFragment::class.java.simpleName
        const val KEY_SEARCH = "search"
    }
}
