package com.android.shooshoo.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.shooshoo.R;
import com.android.shooshoo.activity.FeedCommentsActivity;
import com.android.shooshoo.activity.LoginActivity;
import com.android.shooshoo.models.VideoModel;
import com.android.shooshoo.utils.SimplePlayerViewHolder;

import java.util.ArrayList;


public class FullVideoAdapter extends RecyclerView.Adapter<SimplePlayerViewHolder> {

    private ArrayList<VideoModel> modelArrayList;
    private Context context;
    View.OnClickListener clickListener;


    public FullVideoAdapter(Context context, ArrayList<VideoModel> modelArrayList,View.OnClickListener clickListener)
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

    @Override public void onBindViewHolder(SimplePlayerViewHolder holder, final int position) {
        holder.bind(Uri.parse(modelArrayList.get(position).getVideo()) );
        holder.comment_view.setOnClickListener(clickListener);
    }

    @Override public int getItemCount() {
        return modelArrayList.size();
    }



}