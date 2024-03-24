package ru.androidschool.intensiv.presentation.watchlist

import android.view.View
import coil.load
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.model.movies.MovieDto
import ru.androidschool.intensiv.databinding.ItemSmallBinding

class MoviePreviewItem(
    private val content: MovieDto,
    private val onClick: (movieDto: MovieDto) -> Unit
) : BindableItem<ItemSmallBinding>() {

    override fun getLayout() = R.layout.item_small

    override fun bind(view: ItemSmallBinding, position: Int) {
        with(view.imagePreview) {
            load(content.posterPath)
            setOnClickListener {
                onClick.invoke(content)
            }
        }

    }

    override fun initializeViewBinding(v: View): ItemSmallBinding = ItemSmallBinding.bind(v)
}
