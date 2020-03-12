package com.everlapp.androidexamples.animations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.fragment.app.Fragment
import com.everlapp.androidexamples.R
import kotlinx.android.synthetic.main.fragment_animation_fragment_details.*
import timber.log.Timber

class DetailsFragment : Fragment() {

    companion object {
        const val ARG_IMAGE_RES_ID = "ARG_IMAGE_RES_ID"
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_animation_fragment_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val resId = arguments?.getInt(ARG_IMAGE_RES_ID)
        resId?.let { id -> image.setImageResource(id) }

        // Drag listener, Touch listener
        // TODO: https://developer.android.com/training/gestures/scale 
        // TODO: Touch gestures - https://developer.android.com/training/gestures
        image.setOnTouchListener { v, event ->
            // val action = MotionEventCompat.getAction()
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    MotionEventCompat.getActionIndex(event).also { pointerIndex ->
                        // Remember where started
                        val lastTouchX = MotionEventCompat.getX(event, pointerIndex)
                        val lastTouchY = MotionEventCompat.getY(event, pointerIndex)
                    }
                    // Save the ID of the pointer
                    val activePointerId = MotionEventCompat.getPointerId(event, 0)
                    Timber.e("PointerID($activePointerId)")
                }

                MotionEvent.ACTION_MOVE -> {
                    // Timber.e("ACTION Move!!!")
                }
            }

            true
        }
    }

}