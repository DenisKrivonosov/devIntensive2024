package ru.androidschool.intensiv.presentation.feed

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
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Observables
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.model.movies.MovieType
import ru.androidschool.intensiv.data.model.movies.MovieVO
import ru.androidschool.intensiv.data.repository.MoviesRepository
import ru.androidschool.intensiv.data.repository.MoviesRepositoryImpl
import ru.androidschool.intensiv.databinding.FeedFragmentBinding
import ru.androidschool.intensiv.databinding.FeedHeaderBinding
import ru.androidschool.intensiv.domain.ext.applySchedulers
import ru.androidschool.intensiv.presentation.feed.recycler.MovieItem
import ru.androidschool.intensiv.presentation.movie_details.MovieDetailsFragment.Companion.KEY_MOVIE_ID
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
        binding.moviesRecyclerView.adapter = adapter

        val moviesRepository = MoviesRepositoryImpl

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

        val nowPlayingMoviesSource = moviesRepository.getNowPlayingMovies()
        val upcomingMoviesSource = moviesRepository.getUpcomingMovies()
        val getPopularMoviesSource = moviesRepository.getPopularMovies()

        val allMoviesDisposable = Observables.combineLatest(
            nowPlayingMoviesSource,
            upcomingMoviesSource,
            getPopularMoviesSource
        ) { nowPlayingMovies, upcomingMovies, popularMovies ->
            val map = HashMap<MovieType, List<MovieVO>>()
            map[MovieType.NOW_PLAYING] = nowPlayingMovies
            map[MovieType.UPCOMING] = upcomingMovies
            map[MovieType.POPULAR] = popularMovies
            map
        }
            .applySchedulers()
            .subscribe(
                { response ->
                    val nowPlayingMovies = response[MovieType.NOW_PLAYING]?.map {
                        MovieItem(it) { movie -> openMovieDetails(movie) }
                    }
                    val popularMovies = response[MovieType.POPULAR]?.map {
                        MovieItem(it) { movie -> openMovieDetails(movie) }
                    }
                    val upcomingMovies = response[MovieType.UPCOMING]?.map {
                        MovieItem(it) { movie -> openMovieDetails(movie) }
                    }
                    val nowPlayingMoviesContainer = MainCardContainer(
                        title = R.string.now_playing,
                        items = nowPlayingMovies ?: emptyList()
                    )
                    val popularMoviesContainer = MainCardContainer(
                        title = R.string.popular,
                        items = popularMovies ?: emptyList()
                    )
                    val upcomingMoviesContainer = MainCardContainer(
                        title = R.string.upcoming,
                        items = upcomingMovies ?: emptyList()
                    )
                    val allMovies = listOf(
                        nowPlayingMoviesContainer,
                        popularMoviesContainer,
                        upcomingMoviesContainer
                    )
                    binding.moviesRecyclerView.adapter = adapter.apply {
                        clear()
                        addAll(allMovies)
                    }
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

    private fun openMovieDetails(movie: MovieVO) {
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
        compositeDisposable.clear()
        searchBinding.searchToolbar.clear()
        super.onStop()
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    companion object {
        private val TAG = FeedFragment::class.java.simpleName
        const val KEY_SEARCH = "search"
    }
}
