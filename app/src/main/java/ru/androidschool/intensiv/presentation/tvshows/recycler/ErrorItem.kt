package ru.androidschool.intensiv.presentation.tvshows.recycler

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.databinding.TvShowErrorBinding

class ErrorItem(private val onClick: () -> Unit) : BindableItem<TvShowErrorBinding>() {

    override fun getLayout(): Int = R.layout.tv_show_item

    override fun bind(view: TvShowErrorBinding, position: Int) {
        view.retryButton.setOnClickListener {
            onClick.invoke()
        }
    }

    override fun initializeViewBinding(v: View) = TvShowErrorBinding.bind(v)
}
