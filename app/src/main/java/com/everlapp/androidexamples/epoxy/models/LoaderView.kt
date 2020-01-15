package com.everlapp.androidexamples.epoxy.models

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import com.airbnb.epoxy.ModelView
import com.everlapp.androidexamples.R

/**
 * Generated to LoaderViewModel_
 */
@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class LoaderView(context: Context) : LinearLayout(context) {
    init {
        orientation = VERTICAL
        setBackgroundColor(Color.WHITE)
        View.inflate(context, R.layout.epoxy_progress_event_item, this)
    }
}