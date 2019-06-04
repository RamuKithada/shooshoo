package com.android.shooshoo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.shooshoo.R;

public class RadarListAdapter extends RecyclerView.Adapter<RadarListAdapter.RadarViewHolder> {

    @NonNull
    @Override
    public RadarViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.radar_list_item,null);
        return new RadarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RadarViewHolder radarViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class RadarViewHolder  extends RecyclerView.ViewHolder {

        public RadarViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
