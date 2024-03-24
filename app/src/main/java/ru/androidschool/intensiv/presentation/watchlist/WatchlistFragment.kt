package ru.androidschool.intensiv.presentation.watchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.disposables.CompositeDisposable
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.model.movies.MovieVO
import ru.androidschool.intensiv.data.repository.MoviesRepository
import ru.androidschool.intensiv.data.repository.MoviesRepositoryImpl
import ru.androidschool.intensiv.databinding.FragmentWatchlistBinding
import ru.androidschool.intensiv.domain.ext.applySchedulers
import ru.androidschool.intensiv.presentation.feed.recycler.MovieItem
import ru.androidschool.intensiv.presentation.movie_details.MovieDetailsFragment.Companion.KEY_MOVIE_ID
import timber.log.Timber

class WatchlistFragment : Fragment() {

    private val binding: FragmentWatchlistBinding by viewBinding(CreateMethod.INFLATE)

    private val compositeDisposable = CompositeDisposable()

    private val options = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }

    private val adapter by lazy { GroupAdapter<GroupieViewHolder>() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.moviesRecyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.moviesRecyclerView.adapter = adapter.apply { addAll(listOf()) }

        val moviesRepository = MoviesRepositoryImpl

        val nowPlayingMoviesSource = moviesRepository.observeLikedMovies()

        val nowPlayingMoviesSourceDisposable = nowPlayingMoviesSource
            .applySchedulers()
            .subscribe(
                { response ->
                    val moviesList = response.map {
                        MovieItem(it) { movie ->
                            openMovieDetails(
                                movie
                            )
                        }
                    }
                    binding.moviesRecyclerView.adapter = adapter.apply {
                        clear()
                        addAll(moviesList)
                    }
                },
                { error ->
                    // Log error here since request failed
                    Timber.e(TAG, error.toString())
                }
            )
        compositeDisposable.add(nowPlayingMoviesSourceDisposable)
    }

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    private fun openMovieDetails(movie: MovieVO) {
        val bundle = Bundle()
        bundle.putInt(KEY_MOVIE_ID, movie.id)
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }

    companion object {
        @JvmStatic
        fun newInstance() = WatchlistFragment()

        private val TAG = WatchlistFragment::class.java.simpleName
    }
}