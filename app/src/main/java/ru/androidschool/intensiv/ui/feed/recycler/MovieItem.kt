package ru.androidschool.intensiv.ui.feed.recycler

import android.view.View
import coil.load
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.model.movies.MovieEntity
import ru.androidschool.intensiv.databinding.ItemWithTextBinding

class MovieItem(
    private val content: MovieEntity,
    private val onClick: (movie: MovieEntity) -> Unit
) : BindableItem<ItemWithTextBinding>() {

    override fun getLayout(): Int = R.layout.item_with_text

    override fun bind(view: ItemWithTextBinding, position: Int) {
        view.description.text = content.title
        view.movieRating.rating = content.rating
        view.content.setOnClickListener {
            onClick.invoke(content)
        }
        view.imagePreview.load(content.posterPath)
    }

    override fun initializeViewBinding(v: View) = ItemWithTextBinding.bind(v)
}