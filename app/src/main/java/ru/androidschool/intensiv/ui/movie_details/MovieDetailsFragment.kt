package ru.androidschool.intensiv.ui.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Singles
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieDbRepository
import ru.androidschool.intensiv.data.model.movies.CompositeMovieDetails
import ru.androidschool.intensiv.databinding.MovieDetailsFragmentBinding
import ru.androidschool.intensiv.ext.applySchedulers
import timber.log.Timber

class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment) {

    private val binding: MovieDetailsFragmentBinding by viewBinding(CreateMethod.INFLATE)
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

        val movieId = this.arguments?.getInt(KEY_MOVIE_ID, 1) ?: 1

        val movieDetailsSource = MovieDbRepository.getMovieDetails(
            movieId = movieId,
            language = "ru"
        )

        val movieCreditsSource = MovieDbRepository.getMovieCredits(
            movieId = movieId,
            language = "ru"
        )

        val allMovieDetailsDisposable = Singles.zip(
            movieDetailsSource,
            movieCreditsSource
        ) { movieDetails, movieCredits ->
            CompositeMovieDetails(movieDetails, movieCredits.castMembers)
        }

        val movieDetailsSourceDisposable = allMovieDetailsDisposable
            .applySchedulers()
            .doOnSubscribe {
                showLoaderView()
            }
            .doFinally {
                showContentView()
            }
            .subscribe(
                { compositeMovieDetails ->
                    with(compositeMovieDetails.movieDetails) {
                        Picasso.get()
                            .load(posterPath)
                            .into(binding.posterImageView)

                        binding.movieTitle.text = title
                        binding.movieRating.rating = rating
                        binding.movieOverview.text = overview
                    }
                    with(compositeMovieDetails.movieCast) {
                        val castList = map { CastItem(it) }
                        binding.movieCastRecycler.adapter = adapter.apply { addAll(castList) }
                    }
                },
                { error ->
                    // Log error here since request failed
                    Timber.e(TAG, error.toString())
                }
            )

        compositeDisposable.addAll(movieDetailsSourceDisposable, movieDetailsSourceDisposable)
    }

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    private fun showContentView() {
        binding.loaderView.isVisible = false
        binding.contentView.isVisible = true
    }

    private fun showLoaderView() {
        binding.loaderView.isVisible = true
        binding.contentView.isVisible = false
    }

    companion object {

        const val KEY_MOVIE_ID = "movie_id"
        private val TAG = MovieDetailsFragment::class.java.simpleName
    }
}
