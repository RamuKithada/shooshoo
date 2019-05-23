package com.android.shooshoo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.models.ContactsModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 4/20/2017.
 */

public class FeedsImagesAdapter extends RecyclerView.Adapter<FeedsImagesAdapter.MyViewHolder>
{
    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public FeedsImagesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.full_image_item, parent, false);
        return new FeedsImagesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FeedsImagesAdapter.MyViewHolder holder, final int position)
    {
        if(listener!=null)
        holder.itemView.setOnClickListener(listener);

    }

    @Override
    public int getItemCount()
    {
        return 16;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder
    {


        public MyViewHolder(View itemView)
        {
            super(itemView);
        }
    }


}
