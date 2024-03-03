package ru.androidschool.intensiv.ui.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieDbRepository
import ru.androidschool.intensiv.data.model.movies.MovieCreditsResponse
import ru.androidschool.intensiv.data.model.movies.MovieDetails
import ru.androidschool.intensiv.databinding.MovieDetailsFragmentBinding
import timber.log.Timber

class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment) {

    private val binding: MovieDetailsFragmentBinding by viewBinding(CreateMethod.INFLATE)

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

        val movieId = this.arguments?.getInt(KEY_MOVIE_ID, 1) ?: 1

        val movieDetailsCall = MovieDbRepository.getMovieDetails(movieId = movieId, language = "ru")

        val movieCreditsCall = MovieDbRepository.getMovieCredits(movieId = movieId, language = "ru")

        movieDetailsCall.enqueue(object : Callback<MovieDetails> {

            override fun onResponse(
                call: Call<MovieDetails>,
                response: Response<MovieDetails>
            ) {
                val moviesDetails = response.body()!!

                Picasso.get()
                    .load(moviesDetails.posterPath)
                    .into(binding.posterImageView)

                binding.movieTitle.text = moviesDetails.title
                binding.movieRating.rating = moviesDetails.rating
                binding.movieOverview.text = moviesDetails.overview
            }

            override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                // Log error here since request failed
                Timber.e(TAG, t.toString())
            }
        })

        movieCreditsCall.enqueue(object : Callback<MovieCreditsResponse> {

            override fun onResponse(
                call: Call<MovieCreditsResponse>,
                response: Response<MovieCreditsResponse>
            ) {
                val moviesCredits = response.body()!!
                val castList = moviesCredits.cast.map { CastItem(it) }
                binding.movieCastRecycler.adapter = adapter.apply { addAll(castList) }
            }

            override fun onFailure(call: Call<MovieCreditsResponse>, t: Throwable) {
                // Log error here since request failed
                Timber.e(TAG, t.toString())
            }
        })
    }

    companion object {

        const val KEY_MOVIE_ID = "movie_id"
        private val TAG = MovieDetailsFragment::class.java.simpleName
    }
}
