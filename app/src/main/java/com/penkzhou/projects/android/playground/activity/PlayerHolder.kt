package com.penkzhou.projects.android.playground.activity

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

enum class SourceType {
    local_video, local_audio, http_audio, http_video, playlist
}

data class PlayerState(var window: Int = 0,
                       var position: Long = 0,
                       var whenReady: Boolean = true,
                       var source: SourceType = SourceType.local_audio)

class PlayerHolder(val context: Context,
                   val playerView: PlayerView,
                   val playerState: PlayerState) : AnkoLogger {
    val player: ExoPlayer

    init {
        player = ExoPlayerFactory.newSimpleInstance(context, DefaultTrackSelector(), DefaultLoadControl())
                .also {
                    playerView.player = it
                    info { "Simple Player created." }
                }
    }


    fun selectMediaSourceToPlay(source: SourceType): Uri {
        return when (source) {
            SourceType.http_audio -> Uri.parse("http://www.bok.net/dash/tears_of_steel/cleartext/stream.mpd")
            SourceType.http_video -> Uri.parse("http://www.bok.net/dash/tears_of_steel/cleartext/stream.mpd")
            SourceType.local_audio -> Uri.parse("http://www.bok.net/dash/tears_of_steel/cleartext/stream.mpd")
            SourceType.local_video -> Uri.parse("http://www.bok.net/dash/tears_of_steel/cleartext/stream.mpd")
            SourceType.playlist -> Uri.parse("http://www.bok.net/dash/tears_of_steel/cleartext/stream.mpd")
        }
    }


    private fun createMediaSource(uri: Uri): MediaSource {
        return DashMediaSource.Factory(
                DefaultDataSourceFactory(context, "playground")).createMediaSource(uri)
    }

    fun start() {
        player.prepare(createMediaSource(selectMediaSourceToPlay(SourceType.http_video)))
        with(playerState) {
            player.playWhenReady = whenReady
            player.seekTo(window, position)
        }
        info { "Simple Player created." }
    }

    fun stop() {
        with(player) {
            with(playerState) {
                position = currentPosition
                window = currentWindowIndex
                whenReady = playWhenReady
            }
            stop()
        }
        info { "Player stop." }
    }

    fun release() {
        //释放codecs, MediaSources 等资源
        player.release()
        info { "Player release." }
    }
}