package com.android.shooshoo.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.shooshoo.R;

public class JackpotChallengersAdapter extends RecyclerView.Adapter<JackpotChallengersAdapter.CatViewHolder> {


    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.challengers_item,null);
        return new CatViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final CatViewHolder catViewHolder,final int i) {


catViewHolder.itemView.setOnClickListener(onClickListener);

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
