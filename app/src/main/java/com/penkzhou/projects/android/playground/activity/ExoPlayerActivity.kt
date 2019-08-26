package com.penkzhou.projects.android.playground.activity

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSourceFactory
import com.google.android.exoplayer2.source.ClippingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.dash.manifest.DashManifest
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.video.VideoListener
import com.penkzhou.projects.android.playground.R
import okhttp3.OkHttpClient

class ExoPlayerActivity : AppCompatActivity() {
    private var playerView: PlayerView? = null
    private var player: SimpleExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exo_player)
        playerView = findViewById(R.id.player_view)
        // Instantiate the player.
        player = ExoPlayerFactory.newSimpleInstance(this)
        //        player.setPlaybackParameters(new PlaybackParameters(2));
        // Attach player to the view.
        playerView!!.player = player
        playerView!!.controllerAutoShow = false
        playerView!!.showController()
        // Prepare the player with the dash media source.
        val okHttpClient = OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor())
                .build()
        val factory = OkHttpDataSourceFactory(okHttpClient, "playground-app")
        val mediaSource = ProgressiveMediaSource.Factory(factory).createMediaSource(Uri.parse(NORMAL_MEDIA_URL))
        val hlsMediaSource = HlsMediaSource.Factory(factory).createMediaSource(Uri.parse(HLS_URL))
        val dashMediaSource = DashMediaSource.Factory(factory).createMediaSource(Uri.parse(DASH_URL))
        //        MediaSource smoothStreamSource = new SsMediaSource.Factory(factory).createMediaSource(Uri.parse(SMOOTH_STREAM_URL));
        val clipSource = ClippingMediaSource(hlsMediaSource, 20000000, 25000000)
        player!!.playWhenReady = true
        player!!.addListener(object : Player.EventListener {
            override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {
                Log.e(TAG, "onTimelineChanged: " + timeline!!)
                if (manifest is DashManifest) {
                    val dashManifest = manifest as DashManifest?
                    Log.e(TAG, "DashManifest: \navailabilityStartTimeMs :" + dashManifest!!.availabilityStartTimeMs
                            + "\ndurationMs :" + dashManifest.durationMs
                            + "\ndynamic :" + dashManifest.dynamic
                            + "\nminBufferTimeMs :" + dashManifest.minBufferTimeMs
                            + "\ngetPeriodCount :" + dashManifest.periodCount
                            + "\nlocation :" + dashManifest.location
                    )

                    // Do something with the manifest.
                }

            }

            override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {
                Log.e(TAG, "onTracksChanged: trackGroups.length: " + trackGroups!!.length)
                for (i in 0 until trackGroups.length) {
                    val trackGroup = trackGroups.get(i)
                    Log.e(TAG, String.format("\t trackGroups[%d]: ", i))
                    Log.e(TAG, String.format("\t format size--->%d: ", trackGroup.length))
                    //                    for (int j = 0; j < trackGroup.length; j++) {
                    //                        Format format = trackGroup.getFormat(j);
                    //
                    //                        Log.e(TAG, "Format: \ncodecs :" + format.codecs
                    //                                + "\ncontainerMimeType :" + format.containerMimeType
                    //                                + "\nid :" + format.id
                    //                                + "\nlabel :" + format.label
                    //                                + "\nlanguage :" + format.language
                    //                                + "\nsampleMimeType :" + format.sampleMimeType
                    //                                + "\nbitrate :" + format.bitrate
                    //                                + "\nframeRate :" + format.frameRate
                    //                                + "\nchannelCount :" + format.channelCount
                    //                                + "\npixelWidthHeightRatio :" + format.pixelWidthHeightRatio
                    //                        );
                    //                    }
                    Log.e(TAG, "onTracksChanged: \n")
                }

            }

            override fun onLoadingChanged(isLoading: Boolean) {
                val loadingString = if (isLoading) "开始loading" else "停止loading"
                Log.e(TAG, "onLoadingChanged : $loadingString")

            }

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                var playState = ""
                when (playbackState) {
                    Player.STATE_IDLE -> playState = "STATE_IDLE"
                    Player.STATE_ENDED -> playState = "STATE_ENDED"
                    Player.STATE_BUFFERING -> playState = "STATE_BUFFERING"
                    Player.STATE_READY -> playState = "STATE_READY"
                }
                Log.e(TAG, "onPlayerStateChanged: playWhenReady : $playWhenReady ; playbackState :$playState")

            }

            override fun onRepeatModeChanged(repeatMode: Int) {
                Log.e(TAG, "onRepeatModeChanged: $repeatMode")

            }

            override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
                Log.e(TAG, "onShuffleModeEnabledChanged: $shuffleModeEnabled")

            }

            override fun onPlayerError(error: ExoPlaybackException?) {
                Log.e(TAG, "onPlayerError: " + error!!.message)

            }

            override fun onPositionDiscontinuity(reason: Int) {
                var playState = ""
                when (reason) {
                    Player.DISCONTINUITY_REASON_AD_INSERTION -> playState = "DISCONTINUITY_REASON_AD_INSERTION"
                    Player.DISCONTINUITY_REASON_INTERNAL -> playState = "DISCONTINUITY_REASON_INTERNAL"
                    Player.DISCONTINUITY_REASON_PERIOD_TRANSITION -> playState = "DISCONTINUITY_REASON_PERIOD_TRANSITION"
                    Player.DISCONTINUITY_REASON_SEEK -> playState = "DISCONTINUITY_REASON_SEEK"
                    Player.DISCONTINUITY_REASON_SEEK_ADJUSTMENT -> playState = "DISCONTINUITY_REASON_SEEK_ADJUSTMENT"
                }
                Log.e(TAG, "onPositionDiscontinuity: $playState")

            }

            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
                Log.e(TAG, "onPlaybackParametersChanged: \nspeed :" + playbackParameters!!.speed
                        + "\npitch :" + playbackParameters.pitch
                        + "\nskipSilence :" + playbackParameters.skipSilence
                )

            }

            override fun onSeekProcessed() {
                Log.e(TAG, "onSeekProcessed: ")

            }
        })
        player!!.addVideoListener(object : VideoListener {
            override fun onVideoSizeChanged(width: Int, height: Int, unappliedRotationDegrees: Int, pixelWidthHeightRatio: Float) {
                Log.e(TAG, "onVideoSizeChanged: \nwidth :" + width
                        + "\nheight :" + height
                        + "\nunappliedRotationDegrees :" + unappliedRotationDegrees
                        + "\npixelWidthHeightRatio :" + pixelWidthHeightRatio
                )

            }

            override fun onSurfaceSizeChanged(width: Int, height: Int) {
                Log.e(TAG, "onSurfaceSizeChanged: \nwidth :" + width
                        + "\nheight :" + height
                )
            }

            override fun onRenderedFirstFrame() {
                Log.e(TAG, "onRenderedFirstFrame: ")

            }
        })
        player!!.prepare(hlsMediaSource)
    }

    override fun onPause() {
        super.onPause()
        playerView!!.onPause()
        player!!.release()
    }

    companion object {
        private val HLS_URL = "https://bili.meijuzuida.com/20190202/1862_22c36873/800k/hls/index.m3u8"
        private val DASH_URL = "http://www.bok.net/dash/tears_of_steel/cleartext/stream.mpd"
        private val NORMAL_MEDIA_URL = "https://storage.coverr.co/videos/coverr-man-communicating-by-phone-1565704013660?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6IjExNDMyN0NEOTRCMUFCMTFERTE3IiwiaWF0IjoxNTY2MTk4NjY5LCJleHAiOjE1NjYyMDIyNjl9.BgvhMzACGBtiJj8UhAHhWyI6wz2kIYc3wARCoTLId1I"
        private val TAG = "exoplayer_demo"
    }


}
