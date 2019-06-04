package com.android.shooshoo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.shooshoo.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileBrandAdapter extends RecyclerView.Adapter<ProfileBrandAdapter.CatViewHolder> {
    int[] images;

    public ProfileBrandAdapter(int[] images) {
        this.images = images;
    }
    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.circle_image_item,null);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CatViewHolder catViewHolder,final int i) {

        catViewHolder.image.setImageResource(images[i]);

    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class CatViewHolder extends RecyclerView.ViewHolder{
        CircleImageView image;
        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
        }
    }
}
