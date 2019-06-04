package com.android.shooshoo.utils;

import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.shooshoo.R;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import im.ene.toro.ToroPlayer;
import im.ene.toro.ToroUtil;
import im.ene.toro.exoplayer.SimpleExoPlayerViewHelper;
import im.ene.toro.media.PlaybackInfo;
import im.ene.toro.widget.Container;

public class SimplePlayerViewHolder extends RecyclerView.ViewHolder implements ToroPlayer {

    public SimpleExoPlayerView playerView;
    public SimpleExoPlayerViewHelper helper;
    public CardView card;
    public ImageView iv_pauseresume;
    public Uri mediaUri;
    public boolean isPlay=true;
    public LinearLayout comment_view;
    public SimplePlayerViewHolder(View itemView) {
        super(itemView);
        playerView = (SimpleExoPlayerView) itemView.findViewById(R.id.player);
        card=(CardView)itemView.findViewById(R.id.card);
        comment_view=itemView.findViewById(R.id.comment_view);
        iv_pauseresume=(ImageView)itemView.findViewById(R.id.iv_pauseresume);
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
            helper = new SimpleExoPlayerViewHelper(container, this, mediaUri);

        }
        helper.initialize(playbackInfo);
    }

    @Override public void play() {
        if (helper != null){
            helper.play();
            iv_pauseresume.setVisibility(View.GONE);
        }
    }

    @Override public void pause() {
        if (helper != null)
        {
            helper.pause();
            iv_pauseresume.setVisibility(View.VISIBLE);
        }
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
}
