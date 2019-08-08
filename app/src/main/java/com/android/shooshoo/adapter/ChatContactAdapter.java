package com.android.shooshoo.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.chat.ChattingActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatContactAdapter extends RecyclerView.Adapter<ChatContactAdapter.CatViewHolder> {
  Context context;
int[] images=new int[]{R.drawable.portrait,R.drawable.profile_1,R.drawable.profile_2,R.drawable.portrait,
        R.drawable.profile_1,R.drawable.profile_2,
        R.drawable.portrait,R.drawable.profile_1,R.drawable.profile_2};
    public ChatContactAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_brand_item,null);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CatViewHolder catViewHolder,final int i) {
        catViewHolder.image.setImageResource(images[i]);
        catViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,ChattingActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class CatViewHolder extends RecyclerView.ViewHolder{
        CircleImageView image;
        TextView brandName;
        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            image.setPadding(10,10,10,10);
            image.setBorderWidth(1);
            image.setBorderColor(Color.parseColor("#FFFFFF"));
            brandName=itemView.findViewById(R.id.name);
        }
    }
}
