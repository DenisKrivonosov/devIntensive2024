package ru.androidschool.intensiv.ui.movie_details

import android.view.View
import coil.load
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.model.common.CastMember
import ru.androidschool.intensiv.databinding.ItemCastBinding

class CastItem(private val content: CastMember) : BindableItem<ItemCastBinding>() {

    override fun getLayout(): Int = R.layout.item_cast

    override fun bind(view: ItemCastBinding, position: Int) {
        view.actorName.text = content.name
        content.profilePath?.let { url -> view.actorAvatar.load(url) }
    }

    override fun initializeViewBinding(v: View) = ItemCastBinding.bind(v)
}
