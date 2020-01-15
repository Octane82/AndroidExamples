package com.everlapp.androidexamples.epoxy

import com.airbnb.epoxy.AutoModel
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.Typed2EpoxyController
import com.everlapp.androidexamples.epoxy.models.*


class SampleController(private val callbacks: AdapterCallbacks) : Typed2EpoxyController<List<Event>, Boolean>() {

    interface AdapterCallbacks {
        fun onEventClickListener(event: Event)
    }


    @AutoModel
    lateinit var loaderModel: LoaderViewModel_



    override fun buildModels(data: List<Event>?, loadingMore: Boolean) {
        // Header
        HeaderModel_()
                .id("header")
                .title("Its a header")
                .description("Its description")
                .addTo(this)

        // Carousel
        val photoModels = mutableListOf<PhotoViewModel>()
        for (i in 0..10) {
            photoModels.add(PhotoViewModel_()
                    .id(i)
                    .url("https://mycodeandlife.files.wordpress.com/2013/01/384088_2317070728022_2086719259_n.jpg")
            )
        }

        CarouselModel_()
                .id("carousel")
                .models(photoModels)
                .addTo(this)
        // ----------------------------------

        // Items
        data?.forEachIndexed{ i, event ->
            EventModel_()                           // EventModel - EpoxyModelWithHolder
                    .id(i)
                    .title(event.title)
                    .description(event.description)
                    .time("Unknown")
                    .itemClickListener{ v ->
                        callbacks.onEventClickListener(event)
                    }
                    .addTo(this)
        }

        // Show progress
        loaderModel.addIf(loadingMore, this)


    }


}