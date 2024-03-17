package ru.androidschool.intensiv.ui.watchlist

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.model.movies.Movie
import ru.androidschool.intensiv.databinding.ItemSmallBinding
import ru.androidschool.intensiv.ext.loadImage

class MoviePreviewItem(
    private val content: Movie,
    private val onClick: (movie: Movie) -> Unit
) : BindableItem<ItemSmallBinding>() {

    override fun getLayout() = R.layout.item_small

    override fun bind(view: ItemSmallBinding, position: Int) {
        with(view.imagePreview) {
            loadImage(content.posterPath)
            setOnClickListener {
                onClick.invoke(content)
            }
        }

    }

    override fun initializeViewBinding(v: View): ItemSmallBinding = ItemSmallBinding.bind(v)
}
