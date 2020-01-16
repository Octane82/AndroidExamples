package com.everlapp.androidexamples.epoxy.models

import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.everlapp.androidexamples.R

@EpoxyModelClass(layout = R.layout.epoxy_carousel_item_view)
abstract class CustomCarouselModel : EpoxyModelWithHolder<CustomCarouselModel.CustomCarouselViewHolder>() {

    @EpoxyAttribute
    var models: List<PhotoViewModel> = mutableListOf()

    override fun bind(holder: CustomCarouselViewHolder) {
        holder.carousel.setModels(models)
        // Logic hook indicator carousel
    }

    inner class CustomCarouselViewHolder : BaseEpoxyHolder() {
        val carousel: Carousel by bind(R.id.carousel)
    }

}