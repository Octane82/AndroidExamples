package com.everlapp.androidexamples.databinding

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter


// Adapter methods should be static

@BindingAdapter("app:hideIsNotAdult")
fun hideIsNotAdult(view: View, age: Int) {
    view.visibility = if (age < 18) View.GONE else View.VISIBLE
}

@BindingAdapter("app:hideIfZero")
fun hideIfZero(view: View, number: Int) {
    view.visibility = if (number == 0) View.GONE else View.VISIBLE
}

/**
 *  Sets the value of the progress bar so that 5 likes will fill it up.
 *
 *  Showcases Binding Adapters with multiple attributes. Note that this adapter is called
 *  whenever any of the attribute changes.
 */
@BindingAdapter(value = ["app:progressScaled", "android:max"], requireAll = true)
fun setProgress(progressBar: ProgressBar, likes: Int, max: Int) {
    progressBar.progress = (likes * max / 5).coerceAtMost(max)
}

/*@BindingAdapter("android:onCheckedChanged")
fun onCheckedChanged(checkBox: CheckBox, isChecked: Boolean) {

}*/


@BindingAdapter("android:text")
fun setText(view: TextView, text: CharSequence?) {
    // Some checks removed for clarity
    view.text = text
}

