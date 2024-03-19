package ru.androidschool.intensiv.ui.movie_details

import android.view.View
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.model.common.CastMember
import ru.androidschool.intensiv.databinding.ItemCastBinding

class CastItem(private val content: CastMember) : BindableItem<ItemCastBinding>() {

    override fun getLayout(): Int = R.layout.item_cast

    override fun bind(view: ItemCastBinding, position: Int) {
        view.actorName.text = content.name
        Picasso.get()
            .load(content.profilePath)
            .into(view.actorAvatar)
    }

    override fun initializeViewBinding(v: View) = ItemCastBinding.bind(v)
}
