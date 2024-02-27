package ru.androidschool.intensiv.ui.tvshows

import android.view.View
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.Movie
import ru.androidschool.intensiv.databinding.TvShowItemBinding

class TvShowItem(
    private val content: Movie,
    private val onClick: (movie: Movie) -> Unit
) : BindableItem<TvShowItemBinding>() {

    override fun getLayout(): Int = R.layout.tv_show_item

    override fun bind(view: TvShowItemBinding, position: Int) {
        view.description.text = content.title
        view.movieRating.rating = content.rating
        view.content.setOnClickListener {
            onClick.invoke(content)
        }
        Picasso.get()
            .load(content.posterUrl)
            .into(view.imagePreview)
    }

    override fun initializeViewBinding(v: View) = TvShowItemBinding.bind(v)
}
