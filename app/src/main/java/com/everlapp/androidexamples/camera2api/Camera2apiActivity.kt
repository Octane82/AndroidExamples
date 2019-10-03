package com.everlapp.androidexamples.camera2api

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.media.Image
import android.media.ImageReader
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.view.Surface
import android.view.TextureView
import android.widget.Toast
import com.everlapp.androidexamples.R
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_camera_two_api.*
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class Camera2apiActivity : AppCompatActivity(), TextureView.SurfaceTextureListener {

    var backgroundThread: HandlerThread? = null
    var backgroundHandler: Handler? = null


    private val disposables = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_two_api)
    }


    override fun onResume() {
        super.onResume()
        startBackgroundThread()
        iv_camera_img.surfaceTextureListener = this
    }


    override fun onPause() {
        super.onPause()
        stopBackgroundThread()
    }


    override fun onStop() {
        super.onStop()
        disposables.dispose()
    }


    override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
        Timber.e("Surf Texture available")
        val rxPermissions = RxPermissions(this)
        disposables.add(
                rxPermissions
                        .request(Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe({ isGranted ->
                            if (isGranted) {
                                setupCamera()
                            } else
                                Timber.e("Camera permission NOT Granted")
                        }, { Timber.e(it) })
        )
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {

    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {

    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {

        return true
    }


    private fun startBackgroundThread() {
        backgroundThread = HandlerThread("CameraBackground")
        backgroundThread?.start()
        backgroundHandler = Handler(backgroundThread?.looper)
    }


    private fun stopBackgroundThread() {
        backgroundThread?.quitSafely()
        try {
            backgroundThread?.join()
            backgroundThread = null
            backgroundHandler = null
        } catch (e: InterruptedException) {

        }
    }





    private fun setupCamera() {
        Timber.e("Setup camera LIST: ")

        val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            //val myCameras = cameraManager.cameraIdList
            val myCameras = mutableListOf<CameraService>()

            // Cameras info
            cameraManager.cameraIdList.forEach { cameraId ->
                Timber.e("CameraID: $cameraId")

                // Characteristics
                val characteristic = cameraManager.getCameraCharacteristics(cameraId)

                // Output format camera support
                val confMap = characteristic.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)

                // Какая камера куда смотрит
                when (characteristic.get(CameraCharacteristics.LENS_FACING)) {
                    CameraCharacteristics.LENS_FACING_FRONT ->
                        Timber.d("Camera with ID: $cameraId is FRONT Camera")
                    CameraCharacteristics.LENS_FACING_BACK ->
                        Timber.d("Camera with ID: $cameraId is BACK Camera")
                }

                // Получение списка разрешений, котрые поддерживаются для формата jpeg
                val sizesJpeg = confMap.getOutputSizes(ImageFormat.JPEG)
                sizesJpeg?.let { sizes ->
                    sizes.forEach { size ->
                        Timber.d("W:${size.width} H:${size.height}")
                    }
                } ?: kotlin.run {
                    Timber.e("The camera does not support jpeg")
                }

                // Created camera service for each camera
                myCameras.add(CameraService(cameraManager, cameraId))

            }

            // myCameras[0].openCamera()
            // TODO: Select cameras
            // Front camera (Selfie)
            btn_front_camera.setOnClickListener {
                if (myCameras[0].isOpen()) {
                    myCameras[0].closeCamera()
                }
                if (!myCameras[1].isOpen()) {
                    myCameras[1].openCamera()
                }
            }

            // Back camera (Main cam)
            btn_back_camera.setOnClickListener {
                if (myCameras[1].isOpen()) {
                    myCameras[1].closeCamera()
                }
                if (!myCameras[0].isOpen()) {
                    myCameras[0].openCamera()
                }
            }
            
            // Take photo
            btn_take_photo.setOnClickListener {
                if (myCameras[0].isOpen()) {
                    myCameras[0].takePhoto()
                }
                if (myCameras[1].isOpen()) {
                    myCameras[1].takePhoto()
                }
            }


        } catch (e: CameraAccessException) {
            Timber.e("Camera Access exception")
            e.printStackTrace()
        }
    }


    /**
     * Camera service
     */
    inner class CameraService(private val cameraManager: CameraManager,
                              private val cameraId: String) {

        private var cameraDevice: CameraDevice? = null
        private var captureSession: CameraCaptureSession? = null

        private lateinit var imageReader: ImageReader

        val fileImage = File(Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "test1.jpg")


        fun openCamera() {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraManager.openCamera(cameraId, cameraCallback, null)
                    }
                }
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
        }


        fun closeCamera() {
            if (cameraDevice != null) {
                cameraDevice?.close()
                cameraDevice = null
            }
        }


        fun isOpen() : Boolean = cameraDevice != null


        fun takePhoto() {
            try {
                val captureBuilder = cameraDevice?.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
                captureBuilder?.addTarget(imageReader.surface)

                val captureCallback = object: CameraCaptureSession.CaptureCallback() {
                    override fun onCaptureCompleted(session: CameraCaptureSession,
                                                    request: CaptureRequest,
                                                    result: TotalCaptureResult) {
                        //super.onCaptureCompleted(session, request, result)
                        Timber.e("onCapture completed !!!")
                    }
                }

                captureSession?.stopRepeating()
                captureSession?.abortCaptures()
                captureSession?.capture(captureBuilder?.build(), captureCallback, null)

            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
        }


        


        /**
         * Camera state callback
         */
        val cameraCallback = object: CameraDevice.StateCallback(){

            override fun onOpened(camera: CameraDevice) {
                cameraDevice = camera
                Timber.d("Open camera with ID: ${cameraDevice?.id}")

                createCameraPreviewSession()
            }

            override fun onClosed(camera: CameraDevice) {
                super.onClosed(camera)
                Timber.d("Camera closed!!!")
            }

            override fun onDisconnected(camera: CameraDevice) {
                cameraDevice?.close()
                cameraDevice = null
                Timber.d("Disconnect camera with ID: ${cameraDevice?.id}")
            }

            override fun onError(camera: CameraDevice, error: Int) {
                Timber.e("ERROR! camera id: ${camera.id} error: $error")
            }
        }



        private fun createCameraPreviewSession() {
            Timber.d("Create camera preview session")

            imageReader = ImageReader.newInstance(1920, 1080, ImageFormat.JPEG, 1)
            imageReader.setOnImageAvailableListener(imageAvailableListener, null)


            val texture = iv_camera_img.surfaceTexture
            Timber.e("Surface texture: $texture")
            val surface = Surface(texture)

            try {
                val builder = cameraDevice?.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
                builder?.addTarget(surface)
                val conf = mutableListOf<Surface>()
                conf.add(surface)
                conf.add(imageReader.surface)
                cameraDevice?.createCaptureSession(conf, object: CameraCaptureSession.StateCallback() {
                    override fun onConfigured(session: CameraCaptureSession) {
                        Timber.d("CAMERA Configured")
                        captureSession = session
                        try {
                            captureSession?.setRepeatingRequest(builder?.build(), null, null)
                        } catch (e: CameraAccessException) {
                            e.printStackTrace()
                        }
                    }

                    override fun onConfigureFailed(session: CameraCaptureSession) {
                        Timber.e("Camera config failed")
                    }
                }, null)


            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
        }


        private val imageAvailableListener = ImageReader.OnImageAvailableListener { reader ->
            Timber.e("PHOTO Available for saving!")
            Toast.makeText(this@Camera2apiActivity,
                    "PHOTO Available for saving!", Toast.LENGTH_LONG).show()
            backgroundHandler?.post(ImageSaver(reader.acquireNextImage(), fileImage))
        }

    }


    /**
     * Image save in background thread
     */
    inner class ImageSaver(private val image: Image,
                           private val file: File) : Runnable {


        override fun run() {
            val buffer = image.planes[0].buffer
            val bytes = ByteArray(buffer.remaining())
            buffer.get(bytes)
            var output: FileOutputStream? = null
            try {
                output = FileOutputStream(file)
                output.write(bytes)
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                image.close()
                if (null != output) {
                    try {
                        output.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }
        }
    }




}