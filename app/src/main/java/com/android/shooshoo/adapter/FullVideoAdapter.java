package com.android.shooshoo.adapter;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.shooshoo.R;
import com.android.shooshoo.models.Feed;
import com.android.shooshoo.utils.ApiUrls;
import com.android.shooshoo.utils.SimplePlayerViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import im.ene.toro.CacheManager;

import static com.android.shooshoo.utils.ApiUrls.PROFILE_IMAGE_URL;
import static com.android.shooshoo.utils.ApiUrls.SPONSOR_FEEDS_VIDEO_URL;

/**
 * This is used to show the list of Videos in Feed activity
 */

public class FullVideoAdapter extends RecyclerView.Adapter<SimplePlayerViewHolder> implements SimplePlayerViewHolder.VideoViewedListener, CacheManager {
    private ArrayList<Feed> modelArrayList;
    private Context context;
    FeedClickListener clickListener;
    private boolean isLoadingAdded=false;


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

    public void setCurrentPosition(int cutPos) {
        this.cutPos = cutPos;
    }

    @Override public void onBindViewHolder(final SimplePlayerViewHolder holder, final int position) {
        final Feed feed=modelArrayList.get(position);
        String url=SPONSOR_FEEDS_VIDEO_URL+feed.getType()+"/"+feed.getChallengeId()+"/";//+feed.getUrl();
        Picasso.with(context).load(url+feed.getThumbnail()).error(R.drawable.giphy).placeholder(R.drawable.giphy).into(holder.iv_thumb);
        Log.e("thumnailPath",""+url+feed.getThumbnail());
        holder.tv_report.setVisibility(View.GONE);
        if(feed.getUrl().endsWith(".jpg")||feed.getUrl().endsWith(".JPG")||feed.getUrl().endsWith(".jpeg")||feed.getUrl().endsWith(".png")||feed.getUrl().endsWith(".JPEG")||feed.getUrl().endsWith(".PNG"))
        {
            holder.imageView.setVisibility(View.VISIBLE);
            Picasso.with(context).load(url+feed.getUrl()).noPlaceholder().into(holder.imageView);
            holder.bind(Uri.parse(url+feed.getUrl()));
            holder.setListener(this);
            holder.card.setVisibility(View.GONE);
               if(feed.getViewstatus().equalsIgnoreCase("0"))
               {
                viewed();
                feed.setViewstatus("1");
               }
            Log.e("image_url",url+feed.getUrl());
        }else {
            holder.imageView.setVisibility(View.GONE);
            holder.card.setVisibility(View.VISIBLE);
            holder.setListener(this);
            holder.bind(Uri.parse(url+feed.getUrl()));
        }

        cutPos=position;
        holder.tv_video_des.setText(feed.getPostDescription());
        holder.tv_views_count.setText(feed.getViews());
        holder.tv_like_count.setText(feed.getLikes());
        if(ApiUrls.validateString(feed.getUserName()))
            holder.tv_name.setText(feed.getUserName());
      holder.tv_time.setText(ApiUrls.getDurationTimeStamp(feed.getCreatedOn()));
      if(feed.getLikestatus().equalsIgnoreCase("1"))
            holder.iv_like.setImageResource(R.drawable.like_active);
      else
            holder.iv_like.setImageResource(R.drawable.like_normal);

        Picasso.with(context).load(PROFILE_IMAGE_URL+feed.getImage()).error(R.drawable.profile_1).into(holder.profile_pic);
        holder.comment_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener!=null)
                    clickListener.onClick(v,feed);
            }
        });
        holder.tv_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener!=null)
                    clickListener.onClick(v,feed);
            }
        });
        holder.iv_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.tv_report.getVisibility()==View.GONE)
                     holder.tv_report.setVisibility(View.VISIBLE);
                else
                     holder.tv_report.setVisibility(View.GONE);
            }
        });
        holder.likes_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(clickListener!=null)
                        clickListener.onClick(v,feed);
                        if(feed.getLikestatus().equalsIgnoreCase("0"))
                        {
                            holder.iv_like.setImageResource(R.drawable.like_active);
                            feed.setLikestatus("1");
                        }
                        else
                        {
                            holder.iv_like.setImageResource(R.drawable.like_normal);
                            feed.setLikestatus("0");
                        }

                    try {
                        int likes = Integer.parseInt(feed.getLikes());
                        if(feed.getLikestatus().equalsIgnoreCase("1"))
                            likes++;
                        else
                            likes--;

                        feed.setLikes(String.valueOf(likes));
                        holder.tv_like_count.setText(feed.getLikes());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

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
                if(feed.getFollwerstatus().equalsIgnoreCase("0"))
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
        if(modelArrayList==null)
        {
            cutPos=-1;
            return 0;
        }

        if(modelArrayList.size()==0)
            cutPos=-1;

        return modelArrayList.size();
    }

    public void addAll(List<Feed> mcList) {
        if(mcList!=null)
            modelArrayList.addAll(mcList);
        notifyDataSetChanged();

    }



    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            modelArrayList.clear();
            notifyDataSetChanged();
        }
    }

    public boolean isEmpty() { return getItemCount() == 0; }

    public void addLoadingFooter() {
        isLoadingAdded = true;
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
    }



    @Override
    public void viewed() {
        if(cutPos>=0){
         if(clickListener!=null)
             if(modelArrayList.get(cutPos).getViewstatus().equalsIgnoreCase("0"))
             {
                 clickListener.onView(modelArrayList.get(cutPos));
             }
      }
    }

    @Nullable
    @Override
    public Object getKeyForOrder(int order) {
        return getItem(order);
    }

    private Object getItem(int order) {
        if(order<modelArrayList.size()){
            return modelArrayList.get(order);
        }else
            return null;
    }

    @Nullable
    @Override
    public Integer getOrderForKey(@NonNull Object key) {
        return key instanceof Feed ? modelArrayList.indexOf(key) : null;
    }

    public interface FeedClickListener{
        void onClick(View view,Feed feed);
        void onView(Feed feed);
  }

}