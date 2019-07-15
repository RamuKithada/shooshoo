package com.android.shooshoo.adapter;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.shooshoo.R;
import com.android.shooshoo.models.ImagesModel;
import com.android.shooshoo.models.ImagesSublistModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Created by admin on 4/20/2017.
 */

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.MyViewHolder>
{
    private Activity context;
    private ArrayList<ImagesModel> imagesModelArrayList;
    private OnImageclicklistener onImageclicklistener;
    private int screenWidth,screenHeight;

    public ImageListAdapter(Activity context, ArrayList<ImagesModel> imagesModelArrayList)
    {
        this.context=context;
        this.imagesModelArrayList=imagesModelArrayList;
        DisplayMetrics metrics=context.getResources().getDisplayMetrics();
        this.screenWidth=metrics.widthPixels;
        this.screenHeight=metrics.heightPixels;
    }

    public void setOnImageclicklistener(OnImageclicklistener onImageclicklistener)
    {
        this.onImageclicklistener=onImageclicklistener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_model, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        final MyViewHolder myViewHolder=holder;
        ImagesModel model=imagesModelArrayList.get(position);

        ArrayList<ImagesSublistModel> sublistModels=model.getSublistModels();

        if(sublistModels.size()>0)
           Picasso.with(context).load(model.getSublistModels().get(0).getImage()).error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.iv_image1);
        if(sublistModels.size()>1)
            Picasso.with(context).load(model.getSublistModels().get(1).getImage()).error(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(holder.iv_image2);
        if(sublistModels.size()>2)
            Picasso.with(context).load(model.getSublistModels().get(2).getImage()).error(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(holder.iv_image3);
        if(sublistModels.size()>3)
            Picasso.with(context).load(model.getSublistModels().get(3).getImage()).error(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(holder.iv_image4);
        if(sublistModels.size()>4)
            Picasso.with(context).load(model.getSublistModels().get(4).getImage()).error(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(holder.iv_image5);
        if(sublistModels.size()>5)
            Picasso.with(context).load(model.getSublistModels().get(5).getImage()).error(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(holder.iv_image6);
    }

    @Override
    public int getItemCount()
    {
        return imagesModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.iv_image1)
        ImageView iv_image1;
        @BindView(R.id.iv_image2)
        ImageView iv_image2;
        @BindView(R.id.iv_image3)
        ImageView iv_image3;
        @BindView(R.id.iv_image4)
        ImageView iv_image4;
        @BindView(R.id.iv_image5)
        ImageView iv_image5;
        @BindView(R.id.iv_image6)
        ImageView iv_image6;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnImageclicklistener
    {
       void onImageclick();
    }
}
