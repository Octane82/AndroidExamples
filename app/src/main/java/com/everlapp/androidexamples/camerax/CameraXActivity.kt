package com.everlapp.androidexamples.camerax

import android.Manifest
import android.graphics.Matrix
import androidx.lifecycle.LifecycleOwner
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Rational
import android.util.Size
import android.view.Surface
import android.view.ViewGroup
import androidx.camera.core.CameraX
import androidx.camera.core.Preview
import androidx.camera.core.PreviewConfig
import com.everlapp.androidexamples.R
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_camera_x_activity.*
import timber.log.Timber

class CameraXActivity : AppCompatActivity(), LifecycleOwner {

    private val disposables = CompositeDisposable()

    private lateinit var rxPermissions: RxPermissions


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_x_activity)
        rxPermissions = RxPermissions(this)

        // Request camera permissions
        disposables.add(
                rxPermissions.request(Manifest.permission.CAMERA)
                        .subscribe({ isGranted ->
                            if (isGranted) {
                                viewFinder.post { startCamera() }
                            }
                        }, { Timber.e(it) })
        )

        // Every time the provided texture view changes, recompute layout
        viewFinder.addOnLayoutChangeListener {
            v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            updateTransform()
        }
    }


    override fun onStop() {
        super.onStop()
        disposables.dispose()
    }


    private fun startCamera() {
        // Create configuration object for the viewfinder use case
        val previewConfig = PreviewConfig.Builder().apply {
            setTargetAspectRatio(Rational(1, 1))
            setTargetResolution(Size(640, 480))
        }.build()

        // Build the viewfinder use case
        val preview = Preview(previewConfig)

        // Every time the viewfinder is updated, recompute layout
        preview.setOnPreviewOutputUpdateListener {

            // To update the SurfaceTexture, we have to remove it and re-add it
            val parent = viewFinder.parent as ViewGroup
            parent.removeView(viewFinder)
            parent.addView(viewFinder, 0)

            viewFinder.surfaceTexture = it.surfaceTexture
            updateTransform()
        }


        // Bind use cases to lifecycle
        // If Android Studio complains about "this" being not a LifecycleOwner
        // try rebuilding the project or updating the appcompat dependency to
        // version 1.1.0 or higher.
        CameraX.bindToLifecycle(this, preview)
    }


    private fun updateTransform() {
        val matrix = Matrix()

        // Compute the center of the view finder
        val centerX = viewFinder.width / 2f
        val centerY = viewFinder.height / 2f

        // Correct preview output to account for display rotation
        val rotationDegrees = when (viewFinder.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }

        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)

        // Finally, apply transformations to our texture view
        viewFinder.setTransform(matrix)
    }

}