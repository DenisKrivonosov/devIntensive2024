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
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
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

        val movieDetailsObservable = MovieDbRepository.getMovieDetails(
            movieId = movieId,
            language = "ru"
        )

        val movieCreditsObservable = MovieDbRepository.getMovieCredits(
            movieId = movieId,
            language = "ru"
        )

        movieDetailsObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : SingleObserver<MovieDetails> {
                    override fun onSubscribe(d: Disposable) {
                        Timber.d(TAG, "subscribed on movieDetailsObservable")
                    }

                    override fun onError(e: Throwable) {
                        // Log error here since request failed
                        Timber.e(TAG, e.toString())
                    }

                    override fun onSuccess(moviesDetails: MovieDetails) {
                        Picasso.get()
                            .load(moviesDetails.posterPath)
                            .into(binding.posterImageView)

                        binding.movieTitle.text = moviesDetails.title
                        binding.movieRating.rating = moviesDetails.rating
                        binding.movieOverview.text = moviesDetails.overview
                    }
                }
            )

        movieCreditsObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : SingleObserver<MovieCreditsResponse> {
                    override fun onSubscribe(d: Disposable) {
                        Timber.d(TAG, "subscribed on nowPlayingMoviesObservable")
                    }

                    override fun onError(e: Throwable) {
                        // Log error here since request failed
                        Timber.e(TAG, e.toString())
                    }

                    override fun onSuccess(movieCredits: MovieCreditsResponse) {
                        val castList = movieCredits.cast.map { CastItem(it) }
                        binding.movieCastRecycler.adapter = adapter.apply { addAll(castList) }
                    }
                }
            )
    }

    companion object {

        const val KEY_MOVIE_ID = "movie_id"
        private val TAG = MovieDetailsFragment::class.java.simpleName
    }
}
