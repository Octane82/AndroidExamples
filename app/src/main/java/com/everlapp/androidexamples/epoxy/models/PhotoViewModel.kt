package com.everlapp.androidexamples.epoxy.models

import android.widget.ImageView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.everlapp.androidexamples.R

@EpoxyModelClass(layout = R.layout.epoxy_photo_item_view)
abstract class PhotoViewModel : EpoxyModelWithHolder<PhotoViewModel.PhotoModelHolder>() {

    @EpoxyAttribute
    var url: String = ""

    override fun bind(holder: PhotoModelHolder) {
        Glide.with(holder.ivImage.context)
                .load(url)
                .into(holder.ivImage)
    }

    inner class PhotoModelHolder : BaseEpoxyHolder() {
        val ivImage: ImageView by bind(R.id.iv_image)
    }
}