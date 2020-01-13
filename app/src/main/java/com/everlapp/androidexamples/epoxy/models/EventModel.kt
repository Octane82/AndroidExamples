package com.everlapp.androidexamples.epoxy.models

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.everlapp.androidexamples.R

@EpoxyModelClass(layout = R.layout.epoxy_event_item_view)
abstract class EventModel : EpoxyModelWithHolder<EventModel.EventViewHolder>() {

    @EpoxyAttribute
    var title: String = ""

    @EpoxyAttribute
    var description: String = ""

    @EpoxyAttribute
    var time: String = ""

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var itemClickListener: View.OnClickListener? = null


    override fun bind(holder: EventViewHolder) {
        holder.eventTitle.text = title
        holder.eventDescription.text = description
        holder.eventTime.text = time
        holder.container.setOnClickListener(itemClickListener)
    }

    override fun unbind(holder: EventViewHolder) {
        holder.container.setOnClickListener(null)
    }

    inner class EventViewHolder : BaseEpoxyHolder() {
        val container: ConstraintLayout by bind(R.id.container_event_item)
        val eventTitle: TextView by bind(R.id.tv_event_title)
        val eventDescription: TextView by bind(R.id.tv_event_description)
        val eventTime: TextView by bind(R.id.tv_event_time)
    }

}