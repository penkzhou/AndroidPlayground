package com.penkzhou.projects.android.playground.activity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSourceFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.video.VideoListener;
import com.penkzhou.projects.android.playground.R;

import okhttp3.OkHttpClient;

public class ExoPlayerActivity extends AppCompatActivity {
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private static final String HLS_URL = "https://mnmedias.api.telequebec.tv/m3u8/29880.m3u8";
    private static final String DASH_URL = "http://www.bok.net/dash/tears_of_steel/cleartext/stream.mpd";
    private static final String TAG = "exoplayer_demo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_player);
        playerView = findViewById(R.id.player_view);
        // Instantiate the player.
        player = ExoPlayerFactory.newSimpleInstance(this);
//        player.setPlaybackParameters(new PlaybackParameters(2));
        // Attach player to the view.
        playerView.setPlayer(player);
        playerView.setControllerAutoShow(false);
        playerView.showController();
        // Prepare the player with the dash media source.
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
        DataSource.Factory factory = new OkHttpDataSourceFactory(okHttpClient,"playground-app");
        Uri uri = Uri.parse("https://storage.coverr.co/videos/coverr-man-communicating-by-phone-1565704013660?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6IjExNDMyN0NEOTRCMUFCMTFERTE3IiwiaWF0IjoxNTY2MTk4NjY5LCJleHAiOjE1NjYyMDIyNjl9.BgvhMzACGBtiJj8UhAHhWyI6wz2kIYc3wARCoTLId1I");
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(factory).createMediaSource(uri);
        MediaSource hlsMediaSource = new HlsMediaSource.Factory(factory).createMediaSource(Uri.parse(HLS_URL));
        MediaSource dashMediaSource = new DashMediaSource.Factory(factory).createMediaSource(Uri.parse(DASH_URL));
//        MediaSource smoothStreamSource = new SsMediaSource.Factory(factory).createMediaSource(Uri.parse(SMOOTH_STREAM_URL));
        player.setPlayWhenReady(true);
        player.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {
                Log.e(TAG, "onTimelineChanged: " + timeline);

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                Log.e(TAG, "onTracksChanged: trackGroups.length: " + trackGroups.length);
                for (int i = 0; i < trackGroups.length; i++) {
                    TrackGroup trackGroup = trackGroups.get(i);
                    Log.e(TAG, String.format("\t trackGroups[%d]: ", i));
                    Log.e(TAG, String.format("\t format size--->%d: ", trackGroup.length));
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
                    Log.e(TAG, "onTracksChanged: \n");
                }

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
                String loadingString = isLoading ? "开始loading" : "停止loading";
                Log.e(TAG, "onLoadingChanged : " + loadingString);

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                String playState = "";
                switch (playbackState) {
                    case Player.STATE_IDLE:
                        playState = "STATE_IDLE";
                        break;
                    case Player.STATE_ENDED:
                        playState = "STATE_ENDED";
                        break;
                    case Player.STATE_BUFFERING:
                        playState = "STATE_BUFFERING";
                        break;
                    case Player.STATE_READY:
                        playState = "STATE_READY";
                        break;
                }
                Log.e(TAG, "onPlayerStateChanged: playWhenReady : " + playWhenReady + " ; playbackState :" + playState);

            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {
                Log.e(TAG, "onRepeatModeChanged: " + repeatMode);

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
                Log.e(TAG, "onShuffleModeEnabledChanged: " + shuffleModeEnabled);

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                Log.e(TAG, "onPlayerError: " + error.getMessage());

            }

            @Override
            public void onPositionDiscontinuity(int reason) {
                String playState = "";
                switch (reason) {
                    case Player.DISCONTINUITY_REASON_AD_INSERTION:
                        playState = "DISCONTINUITY_REASON_AD_INSERTION";
                        break;
                    case Player.DISCONTINUITY_REASON_INTERNAL:
                        playState = "DISCONTINUITY_REASON_INTERNAL";
                        break;
                    case Player.DISCONTINUITY_REASON_PERIOD_TRANSITION:
                        playState = "DISCONTINUITY_REASON_PERIOD_TRANSITION";
                        break;
                    case Player.DISCONTINUITY_REASON_SEEK:
                        playState = "DISCONTINUITY_REASON_SEEK";
                        break;
                    case Player.DISCONTINUITY_REASON_SEEK_ADJUSTMENT:
                        playState = "DISCONTINUITY_REASON_SEEK_ADJUSTMENT";
                        break;
                }
                Log.e(TAG, "onPositionDiscontinuity: " + playState);

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
                Log.e(TAG, "onPlaybackParametersChanged: \nspeed :" + playbackParameters.speed
                        + "\npitch :" + playbackParameters.pitch
                        + "\nskipSilence :" + playbackParameters.skipSilence
                );

            }

            @Override
            public void onSeekProcessed() {
                Log.e(TAG, "onSeekProcessed: ");

            }
        });
        player.addVideoListener(new VideoListener() {
            @Override
            public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
                Log.e(TAG, "onVideoSizeChanged: \nwidth :" + width
                        + "\nheight :" + height
                        + "\nunappliedRotationDegrees :" + unappliedRotationDegrees
                        + "\npixelWidthHeightRatio :" + pixelWidthHeightRatio
                );

            }

            @Override
            public void onSurfaceSizeChanged(int width, int height) {
                Log.e(TAG, "onSurfaceSizeChanged: \nwidth :" + width
                        + "\nheight :" + height
                );
            }

            @Override
            public void onRenderedFirstFrame() {
                Log.e(TAG, "onRenderedFirstFrame: ");

            }
        });
        player.prepare(hlsMediaSource);
    }

    @Override
    protected void onPause() {
        super.onPause();
        playerView.onPause();
        player.release();
    }



}
