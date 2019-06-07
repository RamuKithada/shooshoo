package com.android.shooshoo.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeBrandAdapter extends RecyclerView.Adapter<HomeBrandAdapter.CatViewHolder> {
    int[] brandimgs;
    String[] brandnames;
    public HomeBrandAdapter(int[] brandimgs,String[] brandnames) {
        this.brandimgs=brandimgs;
        this.brandnames=brandnames;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_brand_item,null);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CatViewHolder catViewHolder,final int i) {
        Picasso.with(catViewHolder.itemView.getContext()).load(brandimgs[i]).into(catViewHolder.image);
  catViewHolder.brandName.setText(brandnames[i]);


    }

    @Override
    public int getItemCount() {
        return (brandimgs.length<brandnames.length)?brandimgs.length:brandnames.length;
    }

    public class CatViewHolder extends RecyclerView.ViewHolder{
        CircleImageView image;
        AppCompatTextView brandName;
        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            brandName=itemView.findViewById(R.id.name);
        }
    }
}
