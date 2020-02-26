package com.everlapp.androidexamples.databinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.everlapp.androidexamples.R

/**
 * https://codelabs.developers.google.com/codelabs/android-databinding/#0
 *
 */
class DatabindingActivity : AppCompatActivity() {

    private val viewModel: DatabindingViewModel by lazy { ViewModelProviders.of(this).get(DatabindingViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ActivityDataBindingBinding - auto generated class by (activity_data_binding.xml)
        val binding: ActivityDataBindingBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_data_binding)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        // Alternative
        // val binding: ActivityDataBindingBinding = ActivityDataBindingBinding.inflate(layoutInflater)

        // If using Fragment, ListView, RecycleView
        // val listItemBinding = ListItemBinding.inflate(layoutInflater, viewGroup, false)
        // or
        // val listItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item, viewGroup, false)

        binding.user = User("Vasya", "Pupkin", 32)
        //binding.observableField.set("First value")

        /*GlobalScope.launch(Dispatchers.Main) {
            delay(3000)
            binding.user.observableField.set("Changed first value")
        }*/
    }


}