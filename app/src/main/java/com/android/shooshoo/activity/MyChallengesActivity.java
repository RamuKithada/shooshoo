package com.android.shooshoo.activity;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.shooshoo.R;
import com.android.shooshoo.adapter.ProfileFeedsAdapter;
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

public class MyChallengesActivity extends AppCompatActivity implements View.OnClickListener , SimpleExoPlayer.EventListener{
@BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.rv_recnet_posts)
    RecyclerView rv_recnet_posts;
    @BindView(R.id.camera)
    LinearLayout camera;
    @BindView(R.id.brand)
    LinearLayout brand;

    @BindView(R.id.video_thumb)
    ImageView video_thumb;

    @BindView(R.id.sub_title)
    TextView sub_title;
    @BindView(R.id.title)
    TextView title;
    int[] images=new int[]{R.drawable.food_context1,R.drawable.food_context2,R.drawable.food_context3,R.drawable.food_context4,R.drawable.food_context5};

    @BindView(R.id.navigation_home)
    LinearLayout navigation_home;
    @BindView(R.id.navigation_challengers)
    LinearLayout navigation_challengers;
    @BindView(R.id.navigation_feed)
    LinearLayout navigation_feed;
    @BindView(R.id.navigation_winners)
    LinearLayout navigation_winners;
    @BindView(R.id.navigation_radar)
    LinearLayout navigation_radar;
    @BindView(R.id.player)
    SimpleExoPlayerView playerView;
    @BindView(R.id.iv_playpause)
    ImageView iv_playpause;
    @BindView(R.id.video_layout)
    RelativeLayout video_layout;
    String videoUri="";
    SimpleExoPlayer player;
    private boolean isPlaying=true;
    private View.OnClickListener bottomNavigationOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(MyChallengesActivity.this,HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            switch (v.getId()) {
                case R.id.navigation_home:
                    intent.putExtra("icon",0);
                    break;
                case R.id.navigation_challengers:
                    intent.putExtra("icon",1);
                    break;
                case R.id.navigation_feed:
                    intent.putExtra("icon",2);
                    break;
                case R.id.navigation_winners:
                    intent.putExtra("icon",3);
                    break;
                case R.id.navigation_radar:
                    intent.putExtra("icon",4);
                    break;
            }
            startActivity(intent);
            finish();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_challenges);
        ButterKnife.bind(this);
        brand.setOnClickListener(this);
        camera.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        playerView.setUseController(false);
        rv_recnet_posts.setAdapter(new ProfileFeedsAdapter(images));
       int image =getIntent().getIntExtra("image",-1);
       if(image>-1)
           video_thumb.setImageResource(image);
        String titles=getIntent().getStringExtra("name");
        if(titles!=null){
            title.setText(titles);
        }
        String des=getIntent().getStringExtra("des");
        if(des!=null)
            sub_title.setText(des);



        navigation_home.setOnClickListener(bottomNavigationOnClickListener);
        navigation_challengers.setOnClickListener(bottomNavigationOnClickListener);
        navigation_feed.setOnClickListener(bottomNavigationOnClickListener);
        navigation_winners.setOnClickListener(bottomNavigationOnClickListener);
        navigation_radar.setOnClickListener(bottomNavigationOnClickListener);
        videoUri ="http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4";
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

    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.camera:
            Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
            intent.setType("*/*");
//            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//            Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
//            Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
//            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
//            contentSelectionIntent.setType("*/*");
//            Intent[] intentArray = new Intent[]{takePictureIntent,takeVideoIntent};
//            chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
//            chooserIntent.putExtra(Intent.EXTRA_TITLE, "Choose an action");
//            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
            startActivityForResult(intent, 1);
//            startActivity(new Intent(this,CameraActivity.class));
            break;
        case R.id.iv_back:
            finish();
            break;
        case R.id.brand:
            startActivity(new Intent(this,BrandProfileActivity.class));
            break;
    }
    }

    private void setUpVideo() {
        initializePlayer();
        if (videoUri == null) {
            return;
        }
        buildMediaSource(Uri.parse(videoUri));
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
            player.setRepeatMode(Player.REPEAT_MODE_ALL);
                playerView.setPlayer(player);
                playerView.setRepeatToggleModes(Player.REPEAT_MODE_ALL);
        }
    }

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

    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    private void pausePlayer() {
        if (player != null) {
            player.setPlayWhenReady(false);
            player.getPlaybackState();
            isPlaying=false;
            activityPaused=false;
        }
    }

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
