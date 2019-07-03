package com.android.shooshoo.utils;

import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import im.ene.toro.ToroPlayer;
import im.ene.toro.ToroUtil;
import im.ene.toro.exoplayer.ExoPlayerViewHelper;
import im.ene.toro.exoplayer.Playable;
import im.ene.toro.media.PlaybackInfo;
import im.ene.toro.widget.Container;

public class SimplePlayerViewHolder extends RecyclerView.ViewHolder implements ToroPlayer,ToroPlayer.EventListener {

    public SimpleExoPlayerView playerView;
    public ExoPlayerViewHelper helper;
    public RelativeLayout card;
    public ImageView iv_pauseresume;
    public ProgressBar progress_circular;
    public Uri mediaUri;
    public boolean isPlay=true;
    public LinearLayout comment_view,likes_view,share_view,donation_view;
    public RelativeLayout options_lay;
    public LinearLayout message_lay;
    public LinearLayout profile_lay;
    public TextView tv_video_des,tv_name,tv_time,tv_like_count,tv_views_count;
    public CircleImageView profile_pic;
    public ImageView iv_like;
    public ImageView plus_mark,imageView;
    public RelativeLayout upper_layer;

    public SimplePlayerViewHolder(final View itemView) {
        super(itemView);
        playerView = (SimpleExoPlayerView) itemView.findViewById(R.id.player);
        playerView.setRepeatToggleModes(Player.REPEAT_MODE_ALL);
        card=(RelativeLayout) itemView.findViewById(R.id.card);
        comment_view=itemView.findViewById(R.id.comment_view);
        likes_view=itemView.findViewById(R.id.likes_view);
        share_view=itemView.findViewById(R.id.share_view);
        imageView=itemView.findViewById(R.id.imageView);
        donation_view=itemView.findViewById(R.id.donation_view);
        iv_pauseresume=(ImageView)itemView.findViewById(R.id.iv_pauseresume);
        plus_mark=(ImageView)itemView.findViewById(R.id.plus_mark);
        profile_lay=(LinearLayout)itemView.findViewById(R.id.profile_lay);
        options_lay=(RelativeLayout)itemView.findViewById(R.id.options_layout);
        message_lay=(LinearLayout)itemView.findViewById(R.id.message_layout);
        tv_video_des=(TextView)itemView.findViewById(R.id.tv_video_des);
        profile_pic=itemView.findViewById(R.id.profile_pic);
        tv_name=itemView.findViewById(R.id.tv_name);
        tv_time=itemView.findViewById(R.id.tv_time);
        iv_like=itemView.findViewById(R.id.iv_like);
        tv_like_count=itemView.findViewById(R.id.tv_like_count);
        tv_views_count=itemView.findViewById(R.id.tv_views_count);
        upper_layer=itemView.findViewById(R.id.upper_layer);

        progress_circular=(ProgressBar)itemView.findViewById(R.id.progress_circular);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(upper_layer.getVisibility()==View.VISIBLE){
                     upper_layer.setVisibility(View.GONE);
                }else {
                    upper_layer.setVisibility(View.VISIBLE);
                }


                if(helper==null)
                    return;

                if(progress_circular.getVisibility()!=View.VISIBLE) {
                    if (isPlay && isPlaying()) {
                        isPlay = false;
                        helper.pause();
                        iv_pauseresume.setVisibility(View.VISIBLE);

                    } else {
                        isPlay = true;
                        helper.play();
                        iv_pauseresume.setVisibility(View.GONE);

                    }
                }
            }
        });
    }

    @Override public View getPlayerView() {
        return playerView;
    }

    @Override public PlaybackInfo getCurrentPlaybackInfo() {
        return helper != null ? helper.getLatestPlaybackInfo() : new PlaybackInfo();
    }

    @Override
    public void initialize(Container container, PlaybackInfo playbackInfo) {
        if (helper == null) {
            helper = new ExoPlayerViewHelper(container, this, mediaUri);
        }

        helper.initialize(container,playbackInfo);
        helper.addPlayerEventListener(this);

        helper.addEventListener(new Playable.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {
                Log.e("onTimelineChanged","");
            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                Log.e("onTracksChanged","");
            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
                Log.e("onLoadingChanged","");
            }


            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                if(playerView.getPlayer()!=null) {
                    if (playerView.getPlayer().getRepeatMode() != Player.REPEAT_MODE_ALL)
                        playerView.getPlayer().setRepeatMode(Player.REPEAT_MODE_ALL);
                    if (playWhenReady){
                        if(handler==null)
                        {
                            handler=new Handler();
                            handler.post(runnable);
                        }

                }
                }


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
                if(playerView.getPlayer()!=null)
                {
                    long c_pos=  playerView.getPlayer().getCurrentPosition();
                    long duration=playerView.getPlayer().getDuration();
                    if(duration>0&&c_pos>0){
                        float percentage=(c_pos*100)/duration;
                        Log.e("c : "+c_pos,"duration: "+duration+" "+"percentage :"+percentage);
                        if(percentage>30){

                            Log.e("VIews","Viewed");
                        }
                    }



                }

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {
//                Log.e("onSeekProcessed","");
//              if(!isPlay)
//                  pause();

            }

            @Override
            public void onMetadata(Metadata metadata) {

            }

            @Override
            public void onCues(List<Cue> cues) {

            }

            @Override
            public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {

            }

            @Override
            public void onRenderedFirstFrame() {
                Log.e("onRenderedFirstFrame","");

            }
        });


    }

    @Override public void play() {
        if (helper != null){
            helper.play();
            iv_pauseresume.setVisibility(View.GONE);

        }
    }

    private void fadeOut() {
        Animation aniFade = AnimationUtils.loadAnimation(itemView.getContext(),R.anim.fade_out);
        options_lay.startAnimation(aniFade);
        message_lay.startAnimation(aniFade);
        profile_lay.startAnimation(aniFade);
        tv_video_des.startAnimation(aniFade);
        aniFade.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                options_lay.setVisibility(View.GONE);
                message_lay.setVisibility(View.GONE);
                profile_lay.setVisibility(View.GONE);
                tv_video_des.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override public void pause() {
        if (helper != null)
        {
            helper.pause();
        }
    }

    private void fadeIn() {
        Animation aniFade = AnimationUtils.loadAnimation(itemView.getContext(),R.anim.fade_in);
        options_lay.startAnimation(aniFade);
        message_lay.startAnimation(aniFade);
        profile_lay.startAnimation(aniFade);
        tv_video_des.startAnimation(aniFade);
        aniFade.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                options_lay.setVisibility(View.VISIBLE);
                message_lay.setVisibility(View.VISIBLE);
                profile_lay.setVisibility(View.VISIBLE);
                tv_video_des.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override public boolean isPlaying() {
        return helper != null && helper.isPlaying();
    }

    @Override public void release() {
        if (helper != null) {
            helper.release();
            helper = null;


        }
    }

    @Override public boolean wantsToPlay() {
        return ToroUtil.visibleAreaOffset(this, itemView.getParent()) >= 0.85;
    }

    @Override public int getPlayerOrder() {
        return getAdapterPosition();
    }

    public void bind(Uri media) {
        this.mediaUri = media;
        if(handler!=null){
        handler.removeCallbacks(runnable);
        handler=null;
        }

    }

    @Override
    public void onFirstFrameRendered() {

    }

    @Override
    public void onBuffering() {
        progress_circular.setVisibility(View.VISIBLE);
        iv_pauseresume.setVisibility(View.GONE);

    }

    @Override
    public void onPlaying() {
//        fadeOut();
        iv_pauseresume.setVisibility(View.GONE);
        progress_circular.setVisibility(View.GONE);


    }

    @Override
    public void onPaused() {
//        fadeIn();
        iv_pauseresume.setVisibility(View.VISIBLE);
        progress_circular.setVisibility(View.GONE);
    }

    @Override
    public void onCompleted() {

        //        fadeIn();
//        play();
        iv_pauseresume.setVisibility(View.VISIBLE);
        progress_circular.setVisibility(View.GONE);
    }
    VideoViewedListener listener=null;

    public void setListener(VideoViewedListener listener) {
        this.listener = listener;
    }

    public Handler handler=null;
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            calculateDuration();
            if(handler!=null) {
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 2000);
            }
        }
    };
    public  void calculateDuration(){
        if(playerView==null)
            return;
        if(playerView.getPlayer()==null)
            return;

        long c_pos = playerView.getPlayer().getCurrentPosition();
        long duration = playerView.getPlayer().getDuration();
        if (duration > 0 && c_pos > 0) {
            float percentage = (c_pos * 100) / duration;
            Log.e("c : " + c_pos, "duration: " + duration + " " + "percentage :" + percentage);
            if (percentage > 30) {
                if(listener!=null)
                    listener.viewed();
            }
        }
    }

    public  interface VideoViewedListener{
        void viewed();
    };

}
