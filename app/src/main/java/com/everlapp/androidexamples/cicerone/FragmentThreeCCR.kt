package com.everlapp.androidexamples.cicerone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.everlapp.androidexamples.R
import com.everlapp.androidexamples.utils.inflate

class FragmentThreeCCR : androidx.fragment.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            container?.inflate(R.layout.fragment_three_cicerone)


}