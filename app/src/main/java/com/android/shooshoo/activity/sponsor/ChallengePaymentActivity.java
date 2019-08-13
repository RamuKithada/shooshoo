package com.android.shooshoo.activity.sponsor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.shooshoo.R;
import com.android.shooshoo.activity.BaseActivity;
import com.android.shooshoo.activity.HomeActivity;
import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.Company;
import com.android.shooshoo.presenters.SponsorChallengePresenter;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.views.SponsorChallengeView;
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

import java.util.List;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import static com.android.shooshoo.utils.ApiUrls.VIDEO_URL;
/**{@link ChallengePaymentActivity} is used to show payment screen of the Jockpot challenge registration screen

 */
public class ChallengePaymentActivity extends BaseActivity implements View.OnClickListener , SponsorChallengeView,SimpleExoPlayer.EventListener{

    @BindView(R.id.btn_next)
    TextView btn_next;

    @BindView(R.id.iv_back)
    ImageView iv_back;


    @BindView(R.id.iv_help)
    ImageView iv_help;


    @BindViews({R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5})
    List<Button> buttons;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.sub_title)
    TextView sub_title;

    @BindView(R.id.tv_key_des)
    TextView tv_key_des;
    @BindView(R.id.participants)
    TextView participants;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.country_name)
    AppCompatTextView country_name;
    @BindView(R.id.city_name)
    AppCompatTextView city_name;
    @BindView(R.id.zip_code)
    AppCompatTextView zip_code;

    @BindView(R.id.categories)
    AppCompatTextView categories;

    @BindView(R.id.age_range)
    AppCompatTextView age_range;
    @BindView(R.id.gender)
    AppCompatTextView gender;
    @BindView(R.id.audience_size)
    AppCompatTextView audience_size;


    String videoUri="";
    @BindView(R.id.player)
    SimpleExoPlayerView playerView;
    @BindView(R.id.iv_playpause)
    ImageView iv_playpause;
    @BindView(R.id.video_layout)
    RelativeLayout video_layout;
    SimpleExoPlayer player;
    private boolean isPlaying=true;

   /* @BindView(R.id.budget_per_day)
    TextView budget_per_day;*/

    /*@BindView(R.id.edt_summary)
    EditText edt_summary;

    @BindView(R.id.tv_challenge_name)
    TextView tv_challenge_name;
    @BindView(R.id.tv_end_time)
    TextView tv_end_time;
    @BindView(R.id.tv_start_time)
    TextView tv_start_time;*/
   /* @BindView(R.id.tc_checkbox)
    CheckBox tc_checkbox;*/

    ConnectionDetector connectionDetector;
    SponsorChallengePresenter sponsorChallengePresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_payment);
            ButterKnife.bind(this);
            btn_next.setOnClickListener(this);
            iv_back.setOnClickListener(this);
        iv_help.setOnClickListener(this);
            setStage(4);
            tv_title.setText("Summary");
/*
        if (getIntent().hasExtra("budget")) {
*//*            int progress = getIntent().getIntExtra("budget", 0);
            TextView textView = this.budget_per_day;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("€ ");
            stringBuilder.append(progress);
            stringBuilder.append(" Average/Day");
            textView.setText(stringBuilder.toString());*//*
        }*/
        Challenge challenge = this.userSession.getChallenge();
        if (challenge != null) {
            setChallenge(challenge);

        }
        this.connectionDetector = new ConnectionDetector(this);
        this.sponsorChallengePresenter = new SponsorChallengePresenter();
        this.sponsorChallengePresenter.attachView(this);
    }
    /**
     * setStage is for selection one of registration step
     * @param step is step of registration process of a challenge
     */

    private void setStage(int step) {
        for(int index=0;index<buttons.size();index++){
            if(index<=step){
                buttons.get(index).setBackgroundResource(R.drawable.selected);
                buttons.get(index).setText(String.valueOf(step+1));
            }
            else
                buttons.get(index).setBackgroundResource(R.drawable.unselected);

        }
    }

    public  void onClick(View view){
        switch (view.getId())
        {
            case R.id.btn_next:

                if (this.connectionDetector.isConnectingToInternet()) {
                    sponsorChallengePresenter.sponsorSummery(userSession.getChallenge().getChallengeId());
                    return;
                }
                else
                    showMessage("Please check your Internet Connection");
;
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_help:
                showMessage("Help");
                break;
        }

    }
    Challenge challenge;
    private void setChallenge(Challenge challenge){
        this.challenge=challenge;
        title.setText(challenge.getChallengeName());
        sub_title.setText(challenge.getDescription());
        tv_key_des.setText(challenge.getKeyDescription());
        participants.setText(challenge.getParticipants());
        videoUri =VIDEO_URL+challenge.getType()+"s/videos/"+challenge.getChallengeVideo();
        setUpVideo();
            country_name.setText(challenge.getCountryName());
            city_name.setText(challenge.getCityName());
            age_range.setText(challenge.getAgeStart() + " - " + challenge.getAgeEnd());
            StringBuilder builderCat=new StringBuilder();
            if(challenge.getCategoryNames()!=null) {
                for (String name : challenge.getCategoryNames()) {
                    if (builderCat.length() == 0)
                        builderCat.append(name);
                    else {
                        builderCat.append(',').append(name);
                    }
                }
                categories.setText(builderCat.toString());
            }
            zip_code.setText(challenge.getAudZipcode());
            gender.setText(challenge.getGender());
        audience_size.setText(userSession.getAudSize());
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
    public void onCompanyRegister(Company company) {

    }

    @Override
    public void onChallengeResponse(Challenge challenge) {
        userSession.setSponsorChallenge(null);
        userSession.savaChallenge(null);
        Intent homeIntent=new Intent(this, HomeActivity.class);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeIntent.putExtra("icon",1);
        startActivity(homeIntent);
    }
}
