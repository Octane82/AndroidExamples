package com.everlapp.androidexamples.exoplayer

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.everlapp.androidexamples.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import kotlinx.android.synthetic.main.activity_exoplayer.*
import timber.log.Timber


/**
 * https://codelabs.developers.google.com/codelabs/exoplayer-intro/#0
 */
class MainExoplayerActivity : AppCompatActivity() {

    var player: SimpleExoPlayer? = null
    private lateinit var playbackStateListener: PlaybackStateListener

    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exoplayer)
        playbackStateListener = PlaybackStateListener()
    }


    // Starting with API level 24 Android supports multiple windows.
    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= 24) {
            setupPlayer()
        }
    }

    //  is a helper method called in onResume which allows us to have a full screen experience.
    override fun onResume() {
        super.onResume()
        hideSystemUi()
        if (Build.VERSION.SDK_INT < 24 || player == null) {
            setupPlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT < 24) {
            releasePlayer()
        }
    }


    override fun onStop() {
        super.onStop()
        if (Build.VERSION.SDK_INT >= 24) {
            releasePlayer()
        }
    }


    private fun setupPlayer() {
        player = ExoPlayerFactory.newSimpleInstance(this)
        video_view.player = player

        // https://storage.googleapis.com/exoplayer-test-media-0/play.mp3
        val uri = Uri.parse(getString(R.string.media_url_mp4))
        val mediaSource1 = buildMediaSource(uri)

        val uri2 = Uri.parse(getString(R.string.media_url_mp3))
        val mediaSource2 = buildMediaSource(uri2)

        // Concatenate media source
        val playlist = ConcatenatingMediaSource(mediaSource1, mediaSource2)

        player?.playWhenReady = playWhenReady
        player?.seekTo(currentWindow, playbackPosition)

        player?.addListener(playbackStateListener)
        player?.prepare(playlist, false, false)
    }


    private fun buildMediaSource(uri: Uri): MediaSource {
        val dataSourceFactory = DefaultDataSourceFactory(this, "exoplayer-codelab")
        return ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
    }


    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        video_view.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    // Before we release and destroy the player we store the following information
    //  - This will allow us to resume playback from where the user left off.
    //  - All we need to do is supply this state information when we initialize our player.
    private fun releasePlayer() {
        if (player != null) {
            playWhenReady = player!!.playWhenReady
            playbackPosition = player!!.currentPosition
            currentWindow = player!!.currentWindowIndex
            player?.removeListener(playbackStateListener)
            player?.release()
            player = null
        }
    }


    private inner class PlaybackStateListener : Player.EventListener {

        override fun onPlayerStateChanged(playWhenReady: Boolean,
                                          playbackState: Int) {
            val stateString: String = when (playbackState) {
                ExoPlayer.STATE_IDLE -> "ExoPlayer.STATE_IDLE      -"
                ExoPlayer.STATE_BUFFERING -> "ExoPlayer.STATE_BUFFERING -"
                ExoPlayer.STATE_READY -> "ExoPlayer.STATE_READY     -"
                ExoPlayer.STATE_ENDED -> "ExoPlayer.STATE_ENDED     -"
                else -> "UNKNOWN_STATE             -"
            }
            Timber.d("changed state to $stateString playWhenReady:$playWhenReady")
        }
    }



}