package ru.androidschool.intensiv.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Singles
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieDbRepository
import ru.androidschool.intensiv.data.model.movies.Movie
import ru.androidschool.intensiv.databinding.FeedFragmentBinding
import ru.androidschool.intensiv.databinding.FeedHeaderBinding
import ru.androidschool.intensiv.ext.applySchedulers
import ru.androidschool.intensiv.ui.movie_details.MovieDetailsFragment.Companion.KEY_MOVIE_ID
import timber.log.Timber

class FeedFragment : Fragment(R.layout.feed_fragment) {

    private val binding: FeedFragmentBinding by viewBinding(CreateMethod.INFLATE)
    private val searchBinding by viewBinding(FeedHeaderBinding::bind)
    private val compositeDisposable = CompositeDisposable()

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

        val searchBarDisposable = searchBinding.searchToolbar.onTextChanged()
            .applySchedulers()
            .subscribe(
                { newText ->
                    Timber.d(TAG, "onNext on searchBindingObservable")
                    openSearch(newText)
                },

                { error ->
                    Timber.d(TAG, "onError on searchBindingObservable")
                    // Log error here since request failed
                    Timber.e(TAG, error.toString())
                }

            )

        val nowPlayingMoviesSource = MovieDbRepository.getNowPlayingMovies(language = "ru")
        val upcomingMoviesSource = MovieDbRepository.getUpcomingMovies(language = "ru")
        val getPopularMoviesSource = MovieDbRepository.getPopularMovies(language = "ru")

        val allMoviesDisposable = Singles.zip(
            nowPlayingMoviesSource,
            upcomingMoviesSource,
            getPopularMoviesSource
        ) { nowPlayingMovies, upcomingMovies, popularMovies ->
            nowPlayingMovies.results + upcomingMovies.results + popularMovies.results

        }
            .applySchedulers()
            .doOnSubscribe {
                showLoaderView()
            }
            .doFinally {
                showMoviesView()
            }
            .subscribe(
                { response ->
                    val moviesList = response.map {
                        MovieItem(it) { movie -> openMovieDetails(movie) }
                    }
                    binding.moviesRecyclerView.adapter = adapter.apply { addAll(moviesList) }
                },
                { error ->
                    // Log error here since request failed
                    Timber.e(TAG, error.toString())
                }

            )

        compositeDisposable.addAll(
            searchBarDisposable,
            allMoviesDisposable
        )
    }

    private fun openMovieDetails(movie: Movie) {
        val bundle = Bundle()
        bundle.putInt(KEY_MOVIE_ID, movie.id)
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }

    private fun showMoviesView() {
        binding.loaderView.isVisible = false
        binding.moviesRecyclerView.isVisible = true
    }

    private fun showLoaderView() {
        binding.loaderView.isVisible = true
        binding.moviesRecyclerView.isVisible = false
    }

    private fun openSearch(searchText: String) {
        val bundle = Bundle()
        bundle.putString(KEY_SEARCH, searchText)
        findNavController().navigate(R.id.search_dest, bundle, options)
    }

    override fun onStop() {
        compositeDisposable.clear()
        searchBinding.searchToolbar.clear()
        super.onStop()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    companion object {
        private val TAG = FeedFragment::class.java.simpleName
        const val KEY_SEARCH = "search"
    }
}
