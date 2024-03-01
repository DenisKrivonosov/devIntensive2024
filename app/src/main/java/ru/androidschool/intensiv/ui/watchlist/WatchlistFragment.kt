package ru.androidschool.intensiv.ui.watchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import ru.androidschool.intensiv.data.MockRepository
import ru.androidschool.intensiv.databinding.FragmentWatchlistBinding

class WatchlistFragment : Fragment() {

    private val binding: FragmentWatchlistBinding by viewBinding(CreateMethod.INFLATE)

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

        binding.moviesRecyclerView.layoutManager = GridLayoutManager(context, 4)
        binding.moviesRecyclerView.adapter = adapter.apply { addAll(listOf()) }

        val moviesList =
            MockRepository.getMovies().map {
                MoviePreviewItem(
                    it
                ) { movie -> }
            }.toList()

        binding.moviesRecyclerView.adapter = adapter.apply { addAll(moviesList) }
    }

    companion object {
        @JvmStatic
        fun newInstance() = WatchlistFragment()
    }
}
