package com.android.shooshoo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.shooshoo.R;
import com.android.shooshoo.models.Feed;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecentPostAdapter extends RecyclerView.Adapter<RecentPostAdapter.CatViewHolder> {
    Context context;
    List<Feed> feeds;
    public RecentPostAdapter(Context context, List<Feed> feeds) {
        this.context =context;
        this.feeds=feeds;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profile_feed_list_item,viewGroup,false);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CatViewHolder catViewHolder,final int pos) {
        Feed feed=feeds.get(pos);
        if(feed!=null)
        {
            if(feed.getUrl().endsWith(".jpg")||feed.getUrl().endsWith(".JPG")||feed.getUrl().endsWith(".jpeg")||feed.getUrl().endsWith(".png")||feed.getUrl().endsWith(".JPEG")||feed.getUrl().endsWith(".PNG"))
            {
                Picasso.with(context).load(feed.baseUrl()+feed.getUrl()).noPlaceholder().into(catViewHolder.imageView);

            }
            else
            Picasso.with(context).load(feed.baseUrl()+feed.getThumbnail()).error(R.drawable.error).into(catViewHolder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        if(feeds==null)
            return 0;
        return feeds.size();
    }

    public class CatViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;

        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
           imageView= itemView.findViewById(R.id.image);
        }
    }
}
