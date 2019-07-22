package com.android.shooshoo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.shooshoo.R;
import com.android.shooshoo.models.Region;

import java.util.ArrayList;
import java.util.List;

public class RegionsAdapter extends RecyclerView.Adapter<RegionsAdapter.RegionsHolder> {

    List<Region> regions=new ArrayList<Region>();

    Context context;

    public RegionsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RegionsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.region_item,null);
        return new RegionsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegionsHolder regionsHolder, int pos) {
        regionsHolder.appCompatTextView.setText(regions.get(pos).getRegName());
    }

    @Override
    public int getItemCount() {

        if(regions==null)
            return 0;
        return regions.size();
    }

    public List<Region> selectedRegions() {
        return regions;
    }

    public class RegionsHolder extends RecyclerView.ViewHolder{
       AppCompatEditText appCompatTextView;

       public RegionsHolder(@NonNull View itemView) {
           super(itemView);
           appCompatTextView=itemView.findViewById(R.id.text);

       }
   }

   public void add(Region region){
        if(region==null)
            return;
        if(regions==null)
            regions=new ArrayList<Region>();

            regions.add(region);
            notifyDataSetChanged();

   }



}
