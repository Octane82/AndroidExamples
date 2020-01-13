package com.everlapp.androidexamples.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.everlapp.androidexamples.epoxy.models.Event
import com.everlapp.androidexamples.epoxy.models.EventModel_
import com.everlapp.androidexamples.epoxy.models.HeaderModel_


class SampleController(private val callbacks: AdapterCallbacks) : TypedEpoxyController<List<Event>>() {

    interface AdapterCallbacks {
        fun onEventClickListener(event: Event)
    }


//    @AutoModel
//    lateinit var header: HeaderModel_


//    @AutoModel
//    lateinit var default: ExampleModel_



    override fun buildModels(data: List<Event>?) {
        // Header
        HeaderModel_()
                .id("header")
                .title("Its a header")
                .description("Its description")
                .addTo(this)


        // Items
        data?.forEachIndexed{ i, event ->
            EventModel_()
                    .id(i)
                    .title(event.title)
                    .description(event.description)
                    .time("Unknown")
                    .itemClickListener{ v ->
                        callbacks.onEventClickListener(event)
                    }
                    .addTo(this)
        }

    }


}