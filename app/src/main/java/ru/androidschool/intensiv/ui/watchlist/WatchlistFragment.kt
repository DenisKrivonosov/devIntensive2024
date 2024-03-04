package ru.androidschool.intensiv.ui.watchlist

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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieDbRepository
import ru.androidschool.intensiv.data.model.movies.Movie
import ru.androidschool.intensiv.data.model.movies.MoviesResponse
import ru.androidschool.intensiv.databinding.FragmentWatchlistBinding
import ru.androidschool.intensiv.ui.feed.MovieItem
import ru.androidschool.intensiv.ui.movie_details.MovieDetailsFragment.Companion.KEY_MOVIE_ID
import timber.log.Timber

class WatchlistFragment : Fragment() {

    private val binding: FragmentWatchlistBinding by viewBinding(CreateMethod.INFLATE)

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

        binding.moviesRecyclerView.layoutManager = GridLayoutManager(context, 4)
        binding.moviesRecyclerView.adapter = adapter.apply { addAll(listOf()) }

        val nowPlayingMoviesCall = MovieDbRepository.getNowPlayingMovies(page = 1, language = "ru")

        nowPlayingMoviesCall.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                val moviesList = response.body()!!.results.map {
                    MovieItem(it) { movie ->
                        openMovieDetails(
                            movie
                        )
                    }
                }
                binding.moviesRecyclerView.adapter = adapter.apply { addAll(moviesList) }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                // Log error here since request failed
                Timber.e(TAG, t.toString())
            }
        })
    }

    private fun openMovieDetails(movie: Movie) {
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
