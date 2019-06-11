package com.android.shooshoo.utils;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

import im.ene.toro.ToroPlayer;
import im.ene.toro.ToroUtil;
import im.ene.toro.exoplayer.ExoPlayerViewHelper;
import im.ene.toro.exoplayer.Playable;
import im.ene.toro.media.PlaybackInfo;
import im.ene.toro.widget.Container;

public class SimplePlayerViewHolder extends RecyclerView.ViewHolder implements ToroPlayer,ToroPlayer.EventListener {

    public SimpleExoPlayerView playerView;
    public ExoPlayerViewHelper helper;
    public CardView card;
    public ImageView iv_pauseresume;
    public ProgressBar progress_circular;
    public Uri mediaUri;
    public boolean isPlay=true;
    public LinearLayout comment_view;
    public LinearLayout options_lay;
    public LinearLayout message_lay;
    public LinearLayout profile_lay;
    public TextView text1;
    public SimplePlayerViewHolder(final View itemView) {
        super(itemView);
        playerView = (SimpleExoPlayerView) itemView.findViewById(R.id.player);
        playerView.setRepeatToggleModes(Player.REPEAT_MODE_ALL);
        card=(CardView)itemView.findViewById(R.id.card);
        comment_view=itemView.findViewById(R.id.comment_view);
        iv_pauseresume=(ImageView)itemView.findViewById(R.id.iv_pauseresume);
        profile_lay=(LinearLayout)itemView.findViewById(R.id.profile_lay);
        options_lay=(LinearLayout)itemView.findViewById(R.id.options_layout);
        message_lay=(LinearLayout)itemView.findViewById(R.id.message_layout);
        text1=(TextView)itemView.findViewById(R.id.text1);
        progress_circular=(ProgressBar)itemView.findViewById(R.id.progress_circular);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(helper==null)
                    return;
                if(isPlay && isPlaying())
                {
                    isPlay=false;
                    helper.pause();
                    iv_pauseresume.setVisibility(View.VISIBLE);

                }
                else {
                    isPlay=true;
                    helper.play();
                    iv_pauseresume.setVisibility(View.GONE);

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

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                if(playerView.getPlayer()!=null)
                    playerView.getPlayer().setRepeatMode(Player.REPEAT_MODE_ALL);

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
        text1.startAnimation(aniFade);
        aniFade.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                options_lay.setVisibility(View.GONE);
                message_lay.setVisibility(View.GONE);
                profile_lay.setVisibility(View.GONE);
                text1.setVisibility(View.GONE);
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
        text1.startAnimation(aniFade);
        aniFade.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                options_lay.setVisibility(View.VISIBLE);
                message_lay.setVisibility(View.VISIBLE);
                profile_lay.setVisibility(View.VISIBLE);
                text1.setVisibility(View.VISIBLE);
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

}
