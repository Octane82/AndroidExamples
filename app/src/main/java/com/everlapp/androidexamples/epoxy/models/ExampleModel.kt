package com.everlapp.androidexamples.epoxy.models

import com.airbnb.epoxy.EpoxyModel
import com.everlapp.androidexamples.R

// @EpoxyModelClass(layout = R.layout.epoxy_exampe_view_item)
open class ExampleModel : EpoxyModel<String>() {

    override fun getDefaultLayout(): Int {
        return R.layout.epoxy_exampe_view_item
    }


}