package com.android.shooshoo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.shooshoo.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchChatFriendAdapter extends RecyclerView.Adapter<SearchChatFriendAdapter.CatViewHolder> {

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chats_search_item,null);
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
        CircleImageView imageProfile;
        TextView name;
        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProfile=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.name);
        }
    }
}
