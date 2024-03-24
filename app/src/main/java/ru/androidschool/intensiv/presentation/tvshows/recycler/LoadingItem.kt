package ru.androidschool.intensiv.presentation.tvshows.recycler

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.databinding.TvShowLoadingBinding

class LoadingItem : BindableItem<TvShowLoadingBinding>() {

    override fun getLayout(): Int = R.layout.tv_show_loading

    override fun bind(view: TvShowLoadingBinding, position: Int) {}

    override fun initializeViewBinding(v: View) = TvShowLoadingBinding.bind(v)
}
