package com.everlapp.androidexamples.epoxy.models

import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.everlapp.androidexamples.R
import timber.log.Timber

@EpoxyModelClass(layout = R.layout.header_view)
abstract class HeaderModel : EpoxyModelWithHolder<HeaderModel.HeaderHolder>() {

    @EpoxyAttribute
    var title: String = ""

    @EpoxyAttribute
    var description: String = ""

    override fun bind(holder: HeaderHolder) {
        Timber.e("BIND HM")
        holder.headerTitle.text = title
        holder.headerDescription.text = description
    }


    inner class HeaderHolder : BaseEpoxyHolder() {
        val headerTitle: TextView by bind(R.id.tv_header_title)
        val headerDescription: TextView by bind(R.id.tv_header_description)
    }

}