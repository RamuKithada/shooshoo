package com.android.shooshoo.adapter;

import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.shooshoo.R;
import com.android.shooshoo.activity.FeedCommentsActivity;
import com.android.shooshoo.activity.LoginActivity;
import com.android.shooshoo.models.Feed;
import com.android.shooshoo.utils.ApiUrls;
import com.android.shooshoo.utils.SimplePlayerViewHolder;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.text.format.DateUtils.DAY_IN_MILLIS;
import static com.android.shooshoo.utils.ApiUrls.PROFILE_IMAGE_URL;
import static com.android.shooshoo.utils.ApiUrls.SPONSOR_FEEDS_VIDEO_URL;

/**
 * This is used to show the list of Videos in Feed activity
 */

public class FullVideoAdapter extends RecyclerView.Adapter<SimplePlayerViewHolder> implements SimplePlayerViewHolder.VideoViewedListener {

    private ArrayList<Feed> modelArrayList;
    private Context context;
    FeedClickListener clickListener;


    public FullVideoAdapter(Context context, ArrayList<Feed> modelArrayList,FeedClickListener clickListener)
    {
        this.modelArrayList=modelArrayList;
        this.context=context;
        this.clickListener=clickListener;
    }

    @Override public SimplePlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_feed_list_item, parent, false);
        return new SimplePlayerViewHolder(view);
    }
 int cutPos=-1;
    public int getCurrentPosition(){
        return cutPos;
    }

    @Override public void onBindViewHolder(final SimplePlayerViewHolder holder, final int position) {
        final Feed feed=modelArrayList.get(position);
        String url=SPONSOR_FEEDS_VIDEO_URL+feed.getType()+"/"+feed.getChallengeId()+"/"+feed.getUrl();
        holder.setListener(this);
        holder.bind(Uri.parse(url));
        Log.e("url",url);
        cutPos=position;
        holder.tv_video_des.setText(feed.getPostDescription());
        holder.tv_views_count.setText(feed.getViews());
        holder.tv_like_count.setText(feed.getLikes());
        if(ApiUrls.validateString(feed.getUserName()))
            holder.tv_name.setText(feed.getUserName());
      holder.tv_time.setText(ApiUrls.getDurationTimeStamp(feed.getCreatedOn()));
      if(feed.isLike())
            holder.iv_like.setImageResource(R.drawable.like_active);
      else
            holder.iv_like.setImageResource(R.drawable.like_normal);

        if(ApiUrls.validateString(feed.getImage()))
        Picasso.with(context).load(PROFILE_IMAGE_URL+feed.getImage()).error(R.drawable.profile_1).into(holder.profile_pic);
        holder.comment_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener!=null)
                    clickListener.onClick(v,feed);
            }
        });
        holder.likes_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener!=null)
                    clickListener.onClick(v,feed);
            }
        });
        holder.share_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener!=null)
                     clickListener.onClick(v,feed);
            }
        });
        holder.donation_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener!=null)
                    clickListener.onClick(v,feed);

            }
        });
        holder.plus_mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener!=null)
                     clickListener.onClick(v,feed);
            }
        });
        holder.profile_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener!=null)
                    clickListener.onClick(v,feed);
            }
        });
    }

    @Override public int getItemCount() {
        return modelArrayList.size();
    }


    @Override
    public void viewed() {
  if(cutPos>=0){
         if(clickListener!=null)
             if(!modelArrayList.get(cutPos).isViewed())
             {
                 clickListener.onView(modelArrayList.get(cutPos));
             }
      }
    }

    public interface FeedClickListener{
        void onClick(View view,Feed feed);
        void onView(Feed feed);
  }


}