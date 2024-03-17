package ru.androidschool.intensiv.ui.tvshows

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.model.tv_series.TvShow
import ru.androidschool.intensiv.databinding.TvShowItemBinding
import ru.androidschool.intensiv.ext.loadImage

class TvShowItem(
    private val content: TvShow,
    private val onClick: (tvShow: TvShow) -> Unit
) : BindableItem<TvShowItemBinding>() {

    override fun getLayout(): Int = R.layout.tv_show_item

    override fun bind(view: TvShowItemBinding, position: Int) {
        view.description.text = content.name
        view.movieRating.rating = content.rating
        view.content.setOnClickListener {
            onClick.invoke(content)
        }

        content.posterPath?.let { url -> view.imagePreview.loadImage(url) }
    }

    override fun initializeViewBinding(v: View) = TvShowItemBinding.bind(v)
}
