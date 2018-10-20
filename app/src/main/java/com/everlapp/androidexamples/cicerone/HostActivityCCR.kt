package com.everlapp.androidexamples.cicerone

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.everlapp.androidexamples.App
import com.everlapp.androidexamples.R
import kotlinx.android.synthetic.main.activity_host_cicerone.*
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class HostActivityCCR : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_cicerone)

        /*val fragmentOne = FragmentOneCCR()
        val fragmentTwo = FragmentTwoCCR()
        val fragmentThree = FragmentThreeCCR()*/

        val router = App.INSTANCE.router


        btnNext.setOnClickListener { router.navigateTo(Screens.FirstScreen()) }

        btnPrev.setOnClickListener {  }
    }


    override fun onResume() {
        super.onResume()
        App.INSTANCE.navigatorHolder.setNavigator(navigator)
    }


    override fun onPause() {
        super.onPause()
        App.INSTANCE.navigatorHolder.removeNavigator()
    }


    val navigator = SupportAppNavigator(this, R.id.fragmentContainer)

}