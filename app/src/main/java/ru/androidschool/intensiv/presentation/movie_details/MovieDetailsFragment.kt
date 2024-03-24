package ru.androidschool.intensiv.presentation.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Observables
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.model.movies.CompositeMovieDetails
import ru.androidschool.intensiv.data.repository.LikesRepositoryImpl
import ru.androidschool.intensiv.data.repository.MoviesRepositoryImpl
import ru.androidschool.intensiv.databinding.MovieDetailsFragmentBinding
import ru.androidschool.intensiv.domain.ext.applySchedulers
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

        val moviesRepository = MoviesRepositoryImpl
        val likesRepository = LikesRepositoryImpl

        val movieDetailsSource = moviesRepository.getMovieDetails(
            movieId = movieId
        ).toObservable()

        val movieCreditsSource = moviesRepository.getMovieCredits(
            movieId = movieId
        ).toObservable()

        val isMovieLikedObservable = likesRepository.observeIsMovieLiked(
            movieId = movieId
        )

        val changeMovieIsLikedCompletable = likesRepository.changeMovieIsLiked(
            movieId = movieId
        )

        val allMovieDetailsDisposable = Observables.combineLatest(
            movieDetailsSource,
            movieCreditsSource,
            isMovieLikedObservable
        ) { movieDetails, movieCredits, isMovieLiked ->
            CompositeMovieDetails(movieDetails, movieCredits.castMembers, isMovieLiked)
        }

        val movieDetailsSourceDisposable = allMovieDetailsDisposable
            .applySchedulers()
            .subscribe(
                { compositeMovieDetails ->
                    with(compositeMovieDetails.movieDetails) {
                        binding.posterImageView.load(posterPath)
                        binding.movieTitle.text = title
                        binding.movieRating.rating = rating
                        binding.movieOverview.text = overview
                    }
                    with(compositeMovieDetails.movieCast) {
                        val castList = map { CastItem(it) }
                        binding.movieCastRecycler.adapter = adapter.apply { addAll(castList) }
                    }
                    binding.movieActionLike.isChecked = compositeMovieDetails.isMovieLiked
                },
                { error ->
                    // Log error here since request failed
                    Timber.e(TAG, error.toString())
                }
            )

        binding.movieActionLike.setOnClickListener {
            val changeMovieIsLikedDisposable = changeMovieIsLikedCompletable
                .applySchedulers()
                .subscribe(
                    {
                        Timber.d(TAG, "like changing on movie $movieId completed")
                    }, { error ->
                        Timber.e(TAG, error.toString())
                    }
                )
            compositeDisposable.add(changeMovieIsLikedDisposable)
        }
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        compositeDisposable.addAll(
            movieDetailsSourceDisposable,
            movieDetailsSourceDisposable,
        )
    }

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    companion object {

        const val KEY_MOVIE_ID = "movie_id"
        private val TAG = MovieDetailsFragment::class.java.simpleName
    }
}