package com.penkzhou.projects.android.playground.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.penkzhou.projects.android.playground.R
import kotlinx.android.synthetic.main.activity_exo_player_2.*

class ExoPlayer2Activity : AppCompatActivity() {
    lateinit var playerHolder: PlayerHolder
    val playerState = PlayerState()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exo_player_2)
        playerHolder = PlayerHolder(this, exo_player_view, playerState)
    }

    override fun onStart() {
        super.onStart()
        playerHolder.start()
    }

    override fun onStop() {
        super.onStop()
        playerHolder.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        playerHolder.release()
    }
}