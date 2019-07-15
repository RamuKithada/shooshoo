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

        if(feeds!=null)
        Picasso.with(context).load(feeds.get(pos).baseUrl()+feeds.get(pos).getThumbnail()).error(R.drawable.logo_pink).into(catViewHolder.imageView);
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
