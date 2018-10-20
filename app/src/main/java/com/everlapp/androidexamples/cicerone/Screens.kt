package com.everlapp.androidexamples.cicerone

import android.support.v4.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    class FirstScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = FragmentOneCCR()
    }

}