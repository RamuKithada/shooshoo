package com.android.shooshoo.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.squareup.picasso.Picasso;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.CatViewHolder> {
    int[] catimgs;
    String[] catNames;
    public HomeCategoryAdapter(int[] catimgs,String[] catNames) {
    this.catimgs=catimgs;
    this.catNames=catNames;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_cat_item,null);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CatViewHolder catViewHolder,final int i) {
        catViewHolder.name.setText(catNames[i]);
        Picasso.with(catViewHolder.itemView.getContext()).load(catimgs[i]).into(catViewHolder.image);
    }

    @Override
    public int getItemCount() {
        return catimgs.length;
    }

    public class CatViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;
        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.name);
        }
    }
}
