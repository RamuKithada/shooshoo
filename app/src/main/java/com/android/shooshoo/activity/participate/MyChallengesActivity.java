package com.android.shooshoo.activity.participate;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.shooshoo.R;
import com.android.shooshoo.activity.BaseActivity;
import com.android.shooshoo.activity.CompanyDetailsActivity;
import com.android.shooshoo.activity.HomeActivity;
import com.android.shooshoo.activity.registration.LoginActivity;
import com.android.shooshoo.adapter.HomeBrandAdapter;
import com.android.shooshoo.adapter.RecentPostAdapter;
import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.Feed;
import com.android.shooshoo.presenters.PostChallengePresenter;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.RuleListFragmentDialog;
import com.android.shooshoo.utils.UserSession;
import com.android.shooshoo.views.PostChallengeView;
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
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.android.shooshoo.utils.ApiUrls.VIDEO_URL;

public class MyChallengesActivity extends BaseActivity implements View.OnClickListener, PostChallengeView, SimpleExoPlayer.EventListener{
    /***
     * {@link MyChallengesActivity} shows the  challenge and posts of the recent challenge
      *
     */
    @BindView(R.id.rv_recent_posts)
    RecyclerView rv_recent_posts;
    @BindView(R.id.camera)
    LinearLayout camera;
    @BindView(R.id.rules)
    LinearLayout rules;
    @BindView(R.id.brand)
    LinearLayout brand;
    /*@BindView(R.id.created_at)
    TextView created_at;*/
    @BindView(R.id.tv_key_des)
    TextView tv_key_des;
    @BindView(R.id.participants)
    TextView participants;

    @BindView(R.id.video_thumb)
    ImageView video_thumb;
    @BindView(R.id.save_challenge)
    ImageView save_challenge;

    @BindView(R.id.iv_report)
    ImageView iv_report;

    @BindView(R.id.iv_back)
    ImageView iv_back;

    @BindView(R.id.brands_list)
    RecyclerView brands_list;

    @BindView(R.id.tv_no_data)
    TextView tv_no_data;




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
    ConnectionDetector connectionDetector=null;
    PostChallengePresenter challengePresenter=null;
    RecentPostAdapter recentPostAdapter;
    ArrayList<String> ruleList=new ArrayList<>();
    UserSession userSession;
    ArrayList<Feed> feeds=new ArrayList<Feed>();

    private View.OnClickListener bottomNavigationOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(MyChallengesActivity.this, HomeActivity.class);
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
    private boolean both=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_challenges);
        ButterKnife.bind(this);
        brand.setOnClickListener(this);
        camera.setOnClickListener(this);
        rules.setOnClickListener(this);
        playerView.setUseController(false);
        connectionDetector=new ConnectionDetector(this);
        challengePresenter=new PostChallengePresenter();
        challengePresenter.attachView(this);
        recentPostAdapter=new RecentPostAdapter(this,feeds);
        rv_recent_posts.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        rv_recent_posts.setAdapter(recentPostAdapter);
        userSession=new UserSession(this);
        save_challenge.setOnClickListener(this);
        if(getIntent().hasExtra("challenge")){
            Challenge challenge=getIntent().getParcelableExtra("challenge");
            setChallenge(challenge);
        }else {

            int image = getIntent().getIntExtra("image", -1);
            if (image > -1)
                video_thumb.setImageResource(image);
            String titles = getIntent().getStringExtra("name");
            if (titles != null) {
                title.setText(titles);
            }
            String des = getIntent().getStringExtra("des");
            if (des != null)
                sub_title.setText(des);
            videoUri ="http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4";
            setUpVideo();
        }
        video_layout.getLayoutParams().height=getResources().getDisplayMetrics().heightPixels*3/4;
        navigation_home.setOnClickListener(bottomNavigationOnClickListener);
        navigation_challengers.setOnClickListener(bottomNavigationOnClickListener);
        navigation_feed.setOnClickListener(bottomNavigationOnClickListener);
        navigation_winners.setOnClickListener(bottomNavigationOnClickListener);
        navigation_radar.setOnClickListener(bottomNavigationOnClickListener);

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
        iv_report.setOnClickListener(this);
        iv_back.setOnClickListener(this);

    }
Challenge challenge;
    private void setChallenge(Challenge challenge) {
        this.challenge=challenge;
        title.setText(challenge.getChallengeName());
        sub_title.setText(challenge.getDescription());
//        created_at.setText(ApiUrls.getDurationTimeStamp(challenge.getCreatedOn()));
        tv_key_des.setText(challenge.getKeyDescription());
        participants.setText(challenge.getParticipants());
        videoUri =VIDEO_URL+challenge.getType()+"s/videos/"+challenge.getChallengeVideo();
        setUpVideo();
        if(challenge.getVideoEntries().equalsIgnoreCase("yes")&&challenge.getPhotoEntries().equalsIgnoreCase("yes"))
            both=true;



        if(connectionDetector.isConnectingToInternet())
        {
            challengePresenter.getRecentPosts(challenge.getChallengeId(),challenge.getType());
            challengePresenter.getRules();
        }
         else
             showMessage("Check Internet connection");

        HomeBrandAdapter homeBrandAdapter=new HomeBrandAdapter(this,challenge.getCompanies(),1);
        brands_list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        brands_list.setAdapter(homeBrandAdapter);
    }

    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.camera:
           if(checkingPermissionAreEnabledOrNot()) {
                if(!both)
                {
                    startCamera(false);
                }else {
                    showAlert();
                }
           }else requestMultiplePermission();

            break;

        case R.id.brand:
            Intent intentBrand=new Intent(this, CompanyDetailsActivity.class);
            intentBrand.putExtra("companyId",challenge.getBrands());
            startActivity(intentBrand);
            break;
        case R.id.rules:
            RuleListFragmentDialog showFragment=new RuleListFragmentDialog();
            Bundle args = new Bundle();
            args.putStringArrayList("list",ruleList);
            showFragment.setArguments(args);
            showFragment.show(getSupportFragmentManager(),"rules");

            break;
        case R.id.save_challenge:
            challengePresenter.saveChallenge(userSession.getUserId(),challenge.getChallengeId(),challenge.getType());
            break;
        case R.id.iv_report:

                    showPopup(v);

            break;
        case R.id.iv_back:
           finish();
            break;
    }
    }

    private void showAlert() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this,R.style.datepicker);
        builder.setTitle("Challenge Post");
        builder.setMessage("This Challenge have both video and image challenges.Choose one of them");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setPositiveButton("Image", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startCamera(true);
            }
        });
        builder.setNegativeButton("Video", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startCamera(false);

            }
        });
        builder.create().show();

    }

    /**
     * setUpVideo is to setup the video player to play the video
     */
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
    protected void onResume(){
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                Uri videoFileUri = data.getData();
                Log.e("Uri", "" + videoUri);
                Intent intent = new Intent(this, PostVideoActivity.class);
                intent.putExtra("post", videoUri);
                intent.putExtra("mpost", videoFileUri);
                intent.putExtra("challenge", challenge);
                startActivity(intent);
//          buildMediaSource(videoFileUri);
            }
        }
    }

    @Override
    public void onSuccessfulUpload(String msg) {

    }
    @Override
    public void onRecentPosts(List<Feed> feeds) {
    /*    if(feeds!=null)
        feeds.addAll(feeds);
        recentPostAdapter.notifyDataSetChanged();*/
    if(feeds==null)
    {
        tv_no_data.setVisibility(View.VISIBLE);
        return;
    }

        if(feeds.size()>0)
        {
            rv_recent_posts.setAdapter(new RecentPostAdapter(this,feeds));
            tv_no_data.setVisibility(View.GONE);
        }
        else
        {
            rv_recent_posts.setVisibility(View.GONE);
            tv_no_data.setVisibility(View.VISIBLE);
        }

//        Log.e("count",""+recentPostAdapter.getItemCount() );
    }

    @Override
    public void onRules(List<String> rules) {
    if(rules!=null)
        ruleList.addAll(rules);
    }

    @Override
    public void onChallengeInfo(ResponseBody responseBody) {

    }

    public boolean checkingPermissionAreEnabledOrNot() {
        int camera = ContextCompat.checkSelfPermission(this, CAMERA);
        int write = ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE);
        int read = ContextCompat.checkSelfPermission(this, RECORD_AUDIO);
        return camera == PackageManager.PERMISSION_GRANTED && write == PackageManager.PERMISSION_GRANTED && read==PackageManager.PERMISSION_GRANTED;
    }
    private void requestMultiplePermission() {
        ActivityCompat.requestPermissions(this, new String[]
                {
                        CAMERA,
                        WRITE_EXTERNAL_STORAGE,
                        RECORD_AUDIO
                }, 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
switch (requestCode){
    case 100:
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if(grantResults.length>=3)
            {
                if(checkingPermissionAreEnabledOrNot())
                {
                    if(userSession.isLogin()) {
                        if(!both)
                            startCamera(false);
                        else
                            showAlert();
                    }
                    else
                        startActivity(new Intent(this, LoginActivity.class));
                }
                else
                    requestMultiplePermission();
            }
        } else {
            requestMultiplePermission();
        }
        break;
}


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        challengePresenter.getRecentPosts(challenge.getChallengeId(),challenge.getType());
    }

    PopupWindow popupWindow;
    private void showPopup(View view) {
             TextView textView=new TextView(this);
             textView.setPadding(15,2,15,2);
             textView.setText("report");
             textView.setBackgroundColor(Color.parseColor("#FFFFFF"));
             textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    popupWindow=null;
                    challengePresenter.reportChallenge(userSession.getUserId(),challenge.getChallengeId(),challenge.getType());
                }
            });
            //instantiate popup window
            popupWindow = new PopupWindow(textView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            //display the popup window
            popupWindow.showAsDropDown(view, -view.getWidth(),0, Gravity.LEFT);
    }

    private void startCamera(boolean image) {


        Intent intent = new Intent(this, CameraActivity.class);
//        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra("post", videoUri);
        intent.putExtra("challenge", challenge);
        intent.putExtra("image",image);
        startActivity(intent);
    }
}
