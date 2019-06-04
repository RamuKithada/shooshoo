package com.android.shooshoo.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.shooshoo.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    Context c;
    String[] tvshows;

    public MyAdapter(Context c, String[] tvshows) {
        this.c = c;
        this.tvshows = tvshows;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_item,parent,false);
        MyHolder holder=new MyHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.nameTxt.setText(tvshows[position]);
    }

    @Override
    public int getItemCount() {
        return tvshows.length;
    }
    public class MyHolder extends RecyclerView.ViewHolder {

        TextView nameTxt;

        public MyHolder(View itemView) {
            super(itemView);
            nameTxt= (TextView) itemView.findViewById(R.id.nameTxt);
        }
    }
}