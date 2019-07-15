package com.android.shooshoo.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.shooshoo.R;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SingleVideoViewActivity extends AppCompatActivity implements  SimpleExoPlayer.EventListener{
    @BindView(R.id.player)
    SimpleExoPlayerView playerView;
    @BindView(R.id.iv_playpause)
    ImageView iv_playpause;
    @BindView(R.id.video_layout)
    RelativeLayout video_layout;
    Uri videoUri;
    SimpleExoPlayer player;
    private boolean isPlaying=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_video_view);
        ButterKnife.bind(this);
        videoUri= getIntent().getParcelableExtra("uri");
//        videoUri ="http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4";
        setUpVideo();
        video_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(player!=null)
                {
                    if(isPlaying) {
                        pausePlayer();
                        iv_playpause.setVisibility(View.VISIBLE);
                    }
                    else {
                        resumePlayer();
                        iv_playpause.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    /**
     * setUpVideo is to setup the video player to play the video
     */
    private void setUpVideo() {
        initializePlayer();
        if (videoUri == null) {
            return;
        }
        buildMediaSource(videoUri);
    }

    private void initializePlayer() {
        if (player == null) {
            // 1. Create a default TrackSelector
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);
            // 2. Create the player
            player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this), trackSelector);
            player.setRepeatMode(Player.REPEAT_MODE_ALL);//
            playerView.setPlayer(player);
            playerView.setRepeatToggleModes(Player.REPEAT_MODE_ALL);// play repeat video
        }
    }

    /**
     *  binding the uri of the video to the media player
     */

    private void buildMediaSource(Uri mUri) {
        // Measures bandwidth during playback. Can be null if not required.
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, getString(R.string.app_name)), bandwidthMeter);
        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        MediaSource mediaSource = new ExtractorMediaSource(mUri,
                dataSourceFactory, extractorsFactory, null, null);
        player.prepare(mediaSource);
        player.setPlayWhenReady(true);
        player.addListener(this);
    }
    /**
     release the media Player
     */
    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    /**pause the media player*/
    private void pausePlayer() {
        if (player != null) {
            player.setPlayWhenReady(false);
            player.getPlaybackState();
            isPlaying=false;
            activityPaused=false;
        }
    }
    /** resume the media player*/
    private void resumePlayer() {

        if (player != null) {
            player.setPlayWhenReady(true);
            player.getPlaybackState();
            isPlaying=true;
            activityPaused=true;
        }
    }




    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }



    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }
    /**conform that the video is playing the video or not  when activity is paused*/
    boolean activityPaused=true;
    @Override
    protected void onResume() {
        super.onResume();
        if(activityPaused)
        {
            resumePlayer();
        }else
            iv_playpause.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        pausePlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }


}
