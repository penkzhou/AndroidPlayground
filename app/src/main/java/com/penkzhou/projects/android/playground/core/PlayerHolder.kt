package com.penkzhou.projects.android.playground.core

import android.content.Context
import android.net.Uri
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.analytics.AnalyticsListener
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSourceFactory
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.video.VideoListener
import okhttp3.OkHttpClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.info
import java.util.*

enum class SourceType {
    LOCAL_VIDEO, LOCAL_AUDIO, HTTP_AUDIO, HTTP_VIDEO, PLAYLIST, HTTP_HLS_VIDEO, HTTP_DASH_VIDEO
}

data class PlayerState(var window: Int = 0,
                       var position: Long = 0,
                       var whenReady: Boolean = true,
                       var source: SourceType = SourceType.LOCAL_AUDIO)

class PlayerHolder(context: Context,
                   private val playerView: PlayerView,
                   private val playerState: PlayerState) : AnkoLogger, Player.EventListener, VideoListener, AnalyticsListener {
    private val player: ExoPlayer
    private val factory: DataSource.Factory
    private val localFactory: DataSource.Factory

    init {
        player = ExoPlayerFactory.newSimpleInstance(context, DefaultTrackSelector(), DefaultLoadControl())
                .also {
                    playerView.player = it
                    it.addListener(PlayerHolder@ this)
                    it.addVideoListener(PlayerHolder@ this)
                    it.addAnalyticsListener(PlayerHolder@ this)
                    info { "Simple Player created." }
                }
        val okHttpClient = OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor())
                .build()
        factory = OkHttpDataSourceFactory(okHttpClient, "playground-app_network")
        localFactory = DefaultDataSourceFactory(context, "playground-app_local")
    }


    val mediaMap = mapOf<SourceType, Uri>(
            SourceType.LOCAL_AUDIO to Uri.parse("asset:///music.mp3"),
            SourceType.LOCAL_VIDEO to Uri.parse("asset:///video.mp4"),
            SourceType.HTTP_AUDIO to Uri.parse("https://mr1.doubanio.com/c18b1421c9a31a4683187c00c5d48c40/0/fm/song/p2369960_128k.mp4"),
            SourceType.HTTP_VIDEO to Uri.parse("https://www.apple.com/105/media/us/apple-card/2019/c90ec3fe-63dc-4557-b1ea-50d7539c76bd/films/this-is/apple-card-this-is-tpl-cc-us-2019_1280x720h.mp4"),
            SourceType.HTTP_DASH_VIDEO to Uri.parse("http://www.bok.net/dash/tears_of_steel/cleartext/stream.mpd"),
            SourceType.HTTP_HLS_VIDEO to Uri.parse("https://bili.meijuzuida.com/20190202/1862_22c36873/800k/hls/index.m3u8")


    )

    private fun createExtractorMediaSource(sourceType: SourceType): MediaSource {
        return when (sourceType) {
            SourceType.LOCAL_AUDIO -> {
                return ProgressiveMediaSource.Factory(localFactory).createMediaSource(mediaMap.get(sourceType))
            }
            SourceType.LOCAL_VIDEO -> {
                return ProgressiveMediaSource.Factory(localFactory).createMediaSource(mediaMap.get(sourceType))
            }
            SourceType.HTTP_AUDIO -> {
                return ProgressiveMediaSource.Factory(factory).createMediaSource(mediaMap.get(sourceType))
            }
            SourceType.HTTP_VIDEO -> {
                return ProgressiveMediaSource.Factory(factory).createMediaSource(mediaMap.get(sourceType))
            }
            SourceType.HTTP_DASH_VIDEO -> {
                return DashMediaSource.Factory(factory).createMediaSource(mediaMap.get(sourceType))
            }
            SourceType.HTTP_HLS_VIDEO -> {
                return HlsMediaSource.Factory(factory).createMediaSource(mediaMap.get(sourceType))
            }
            SourceType.PLAYLIST -> {
                return ConcatenatingMediaSource(
                        createExtractorMediaSource(SourceType.LOCAL_AUDIO),
                        createExtractorMediaSource(SourceType.LOCAL_VIDEO),
                        createExtractorMediaSource(SourceType.HTTP_AUDIO),
                        createExtractorMediaSource(SourceType.HTTP_VIDEO),
                        createExtractorMediaSource(SourceType.HTTP_DASH_VIDEO),
                        createExtractorMediaSource(SourceType.HTTP_HLS_VIDEO))
            }
        }
    }

    fun start() {
        player.prepare(createExtractorMediaSource(SourceType.PLAYLIST))
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
        player.removeListener(this)
        player.release()
        info { "Player release." }
    }

    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {


    }

    override fun onSeekProcessed() {


    }

    override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {
        error { "onTracksChanged. ${trackGroups?.let { trackGroups.length }}" }

    }

    override fun onPlayerError(error: ExoPlaybackException?) {


    }

    override fun onLoadingChanged(isLoading: Boolean) {


    }

    override fun onPositionDiscontinuity(reason: Int) {


    }

    override fun onRepeatModeChanged(repeatMode: Int) {


    }

    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {


    }

    override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {
//        error { timeline?.periodCount }
//        timeline?.let {
//            for (i: Int in 0 until timeline?.periodCount) {
//                error { "period $i" }
//            }
//        }
//        if (manifest is HlsManifest) {
//            error { "HlsManifest" }
//            manifest.masterPlaylist.videos.forEach {
//                error { it.format.codecs }
//            }
//        } else if (manifest is DashManifest) {
//            error { "DashManifest" }
//        }

    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        when (playbackState) {
            Player.STATE_IDLE ->
                error { "STATE_IDLE ${Date().time}" }

            Player.STATE_BUFFERING ->
                error { "STATE_BUFFERING ${Date().time}" }

            Player.STATE_READY ->
                error { "STATE_READY ${Date().time}" }

            Player.STATE_ENDED ->
                error { "STATE_ENDED ${Date().time}" }
        }

    }

    override fun onDroppedVideoFrames(eventTime: AnalyticsListener.EventTime?, droppedFrames: Int, elapsedMs: Long) {
        error { "onDroppedVideoFrames ${Date().time}" }

    }

    override fun onRenderedFirstFrame() {
        error { "VideoListener.onRenderedFirstFrame ${Date().time}" }
    }


    override fun onVideoSizeChanged(width: Int, height: Int, unappliedRotationDegrees: Int, pixelWidthHeightRatio: Float) {

    }
}