package com.everlapp.androidexamples.cicerone

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    class MainScreen : SupportAppScreen() {
        override fun getFragment() = FragmentMainCCR()
    }

    class FirstScreen : SupportAppScreen() {
        override fun getFragment(): androidx.fragment.app.Fragment = FragmentOneCCR()
    }

}