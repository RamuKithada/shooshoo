package com.android.shooshoo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.shooshoo.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFeedsAdapter extends RecyclerView.Adapter<ProfileFeedsAdapter.CatViewHolder> {
    int[] images;

    public ProfileFeedsAdapter(int[] images) {
        this.images = images;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profile_feed_list_item,null);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CatViewHolder catViewHolder,final int pos) {

        Picasso.with(catViewHolder.itemView.getContext()).load(images[pos]).into(catViewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class CatViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;

        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
           imageView= itemView.findViewById(R.id.image);
        }
    }
}
