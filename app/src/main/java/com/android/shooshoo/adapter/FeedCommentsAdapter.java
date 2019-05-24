package com.android.shooshoo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.shooshoo.R;

public class FeedCommentsAdapter extends RecyclerView.Adapter<FeedCommentsAdapter.CatViewHolder> {

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feed_comment_listitem,null);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CatViewHolder catViewHolder,final int i) {

    }

    @Override
    public int getItemCount() {
        return 16;
    }

    public class CatViewHolder extends RecyclerView.ViewHolder{
        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
