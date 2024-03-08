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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

        val call = MovieDbRepository.getPopularTvShows(page = 1, language = "ru")

        call.enqueue(object : Callback<TvShowsResponse> {
            override fun onResponse(
                call: Call<TvShowsResponse>,
                response: Response<TvShowsResponse>
            ) {
                val moviesList = response.body()!!.results.map {
                    TvShowItem(it) {}
                }
                binding.tvShowsRecyclerView.adapter = adapter.apply { addAll(moviesList) }

            }

            override fun onFailure(call: Call<TvShowsResponse>, t: Throwable) {
                // Log error here since request failed
                Timber.e(TAG, t.toString())
            }
        })
    }

    companion object {

        private val TAG = TvShowsFragment::class.java.simpleName
    }
}
