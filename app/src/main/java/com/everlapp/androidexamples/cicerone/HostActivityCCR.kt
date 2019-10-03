package com.everlapp.androidexamples.cicerone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.everlapp.androidexamples.App
import com.everlapp.androidexamples.R
import kotlinx.android.synthetic.main.activity_host_cicerone.*
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Replace

class HostActivityCCR : AppCompatActivity() {


    val navigator = SupportAppNavigator(this, R.id.fragmentContainer)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_cicerone)

        val router = App.INSTANCE.router

        // Set main fragment
        if (savedInstanceState == null) {
            navigator.applyCommands(arrayOf<Command>(Replace(Screens.MainScreen())))
        }

        //router.finishChain()

        // Next fragment
        btnNext.setOnClickListener { router.navigateTo(Screens.FirstScreen()) }
        // btnNext.setOnClickListener { router.navigateTo(Screens.MainScreen()) }

        // Previous fragment
        btnPrev.setOnClickListener { router.exit() }
    }


    override fun onResume() {
        super.onResume()
        App.INSTANCE.navigatorHolder.setNavigator(navigator)
    }


    override fun onPause() {
        super.onPause()
        App.INSTANCE.navigatorHolder.removeNavigator()
    }

}