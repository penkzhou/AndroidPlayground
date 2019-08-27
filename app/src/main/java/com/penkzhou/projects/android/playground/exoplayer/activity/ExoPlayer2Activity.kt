package com.penkzhou.projects.android.playground.exoplayer.activity

import android.app.PictureInPictureParams
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.MediaSessionCompat
import android.util.Rational
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator
import com.penkzhou.projects.android.playground.R
import com.penkzhou.projects.android.playground.exoplayer.core.PlayerHolder
import com.penkzhou.projects.android.playground.exoplayer.core.PlayerState
import kotlinx.android.synthetic.main.activity_exo_player_2.*

class ExoPlayer2Activity : AppCompatActivity() {
    lateinit var playerHolder: PlayerHolder
    val playerState = PlayerState()
    lateinit var mediaSession: MediaSessionCompat
    lateinit var mediaSessionConnector: MediaSessionConnector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exo_player_2)
        playerHolder = PlayerHolder(this, exo_player_view, playerState)
        mediaSession = MediaSessionCompat(this, packageName)
        mediaSessionConnector = MediaSessionConnector(mediaSession)
        mediaSessionConnector.setQueueNavigator(object : TimelineQueueNavigator(mediaSession) {
            override fun getMediaDescription(player: Player?, windowIndex: Int): MediaDescriptionCompat {
                return PlayerHolder.MediaCatalog.list[windowIndex]
            }
        })
    }


    override fun onStart() {
        super.onStart()
        playerHolder.start()
        mediaSessionConnector.setPlayer(playerHolder.player)
        mediaSession.isActive = true
    }

    override fun onStop() {
        super.onStop()
        playerHolder.stop()
        mediaSessionConnector.setPlayer(null)
        mediaSession.isActive = false
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaSession.release()
        playerHolder.release()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onUserLeaveHint() {

        enterPictureInPictureMode(
                with(PictureInPictureParams.Builder()) {
                    val width = 16
                    val height = 9
                    setAspectRatio(Rational(width, height))
                    build()
                }
        )
    }

    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean, newConfig: Configuration?) {
        exo_player_view.useController = !isInPictureInPictureMode
    }
}