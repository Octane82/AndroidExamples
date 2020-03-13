package com.everlapp.androidexamples.animations

//import android.view.MotionEvent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.everlapp.androidexamples.R
import kotlinx.android.synthetic.main.fragment_animation_fragment_details.*
import timber.log.Timber

class DetailsFragment : Fragment() {

    companion object {
        const val ARG_IMAGE_RES_ID = "ARG_IMAGE_RES_ID"
    }

    private var activePointerId: Int = 0
    private var lastTouchX: Float = 0.0f
    private var lastTouchY: Float = 0.0f
    private var posX: Float = 0.0f
    private var posY: Float = 0.0f

    private var imageStartX: Float = 0.0f
    private var imageStartY: Float = 0.0f
    private var dX: Float = 0.0f
    private var dY: Float = 0.0f


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

        var intArr = intArrayOf(0, 0)
        view.getLocationInWindow(intArr)
        Timber.e("INT Array: X:${intArr[0]} Y:${intArr[1]}")

        // https://stackoverflow.com/questions/9398057/android-move-a-view-on-touch-move-action-move
        image.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    dX = view.x - event.rawX
                    dY = view.y - event.rawY

                    Timber.e("ActionDown ViewY(${view.y}) RawY(${event.rawY})")
                    imageStartX = view.x
                    imageStartY = view.y
                }
                MotionEvent.ACTION_MOVE -> {
                    view.animate()
                            .x(event.rawX + dX)
                            .y(event.rawY + dY)
                            .setDuration(0)
                            .start()
                }

                MotionEvent.ACTION_CANCEL -> {
                    Timber.e("Cancel ACTION")
                }
                MotionEvent.ACTION_POINTER_UP -> {
                    Timber.e("PointerUP ACTION")
                }
                else -> {
                    // Timber.e("Default ACTION - Rollback")
                    // Return to image to start position
                    view.animate()
                            .x(imageStartX)
                            .y(imageStartY)
                            .setDuration(200)
                            .start()
                }
            }

            true
        }
    }

}