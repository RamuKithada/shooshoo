package com.android.shooshoo.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.shooshoo.R;
import com.android.shooshoo.models.Feed;
import com.android.shooshoo.models.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.android.shooshoo.utils.ApiUrls.SPONSOR_FEEDS_VIDEO_URL;

/**
 * Created by admin on 4/20/2017.
 */

public class PostsGridListAdapter  extends RecyclerView.Adapter<PostsGridListAdapter.GridViewHolder> {
    List<Post> feeds;
    Context context;
    private boolean isLoadingAdded=false;
    int width,height;
    public PostsGridListAdapter(Context context,List<Post> feeds) {
        this.context=context;
        this.feeds = feeds;
        height=context.getResources().getDisplayMetrics().heightPixels;
        width=context.getResources().getDisplayMetrics().widthPixels;
    }

    @NonNull
    @Override
    public PostsGridListAdapter.GridViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.image_model,viewGroup,false);
        return new PostsGridListAdapter.GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsGridListAdapter.GridViewHolder viewHolder, int pos) {
        /*
        int mpos=pos * 6;
        String url=null;
        Log.e("mpos",""+mpos);
        if(pos<total-1) {

//            url=SPONSOR_FEEDS_VIDEO_URL+feeds.get(mpos).getType()+"/"+feeds.get(mpos).getChallengeId()+"/"+feeds.get(mpos).getThumbnail();
            Picasso.with(context).load(feeds.get(mpos).getBaseUrl()).placeholder(R.mipmap.ic_launcher).into(viewHolder.iv_image1);
                Log.e("url",feeds.get(mpos).getBaseUrl());
//            url=SPONSOR_FEEDS_VIDEO_URL+feeds.get(mpos+1).getType()+"/"+feeds.get(mpos+1).getChallengeId()+"/"+feeds.get(mpos+1).getThumbnail();
            Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher).into(viewHolder.iv_image2);

            url=SPONSOR_FEEDS_VIDEO_URL+feeds.get(mpos+2).getType()+"/"+feeds.get(mpos+2).getChallengeId()+"/"+feeds.get(mpos+2).getThumbnail();
            Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher).into(viewHolder.iv_image3);

            url=SPONSOR_FEEDS_VIDEO_URL+feeds.get(mpos+3).getType()+"/"+feeds.get(mpos+3).getChallengeId()+"/"+feeds.get(mpos+3).getThumbnail();
            Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher).into(viewHolder.iv_image4);

            url=SPONSOR_FEEDS_VIDEO_URL+feeds.get(mpos+4).getType()+"/"+feeds.get(mpos+4).getChallengeId()+"/"+feeds.get(mpos+4).getThumbnail();
            Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher).into(viewHolder.iv_image5);

            url=SPONSOR_FEEDS_VIDEO_URL+feeds.get(mpos+5).getType()+"/"+feeds.get(mpos+5).getChallengeId()+"/"+feeds.get(mpos+5).getThumbnail();
            Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher).into(viewHolder.iv_image6);

        }else if(pos==total-1&&reminder>0){
            switch (reminder)
            {
                case 1:
                    url=SPONSOR_FEEDS_VIDEO_URL+feeds.get(mpos).getType()+"/"+feeds.get(mpos).getChallengeId()+"/"+feeds.get(mpos).getThumbnail();
                    Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher).into(viewHolder.iv_image1);


                    viewHolder.iv_image2.setImageBitmap(null);
                    viewHolder.iv_image3.setImageBitmap(null);
                    viewHolder.iv_image4.setImageBitmap(null);
                    viewHolder.iv_image5.setImageBitmap(null);
                    viewHolder.iv_image6.setImageBitmap(null);
                    break;
                case 2:
                    url=SPONSOR_FEEDS_VIDEO_URL+feeds.get(mpos).getType()+"/"+feeds.get(mpos).getChallengeId()+"/"+feeds.get(mpos).getThumbnail();
                    Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher).into(viewHolder.iv_image1);

                    url=SPONSOR_FEEDS_VIDEO_URL+feeds.get(mpos+1).getType()+"/"+feeds.get(mpos+1).getChallengeId()+"/"+feeds.get(mpos+1).getThumbnail();
                    Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher).into(viewHolder.iv_image2);

                    viewHolder.iv_image3.setImageBitmap(null);
                    viewHolder.iv_image4.setImageBitmap(null);
                    viewHolder.iv_image5.setImageBitmap(null);
                    viewHolder.iv_image6.setImageBitmap(null);

                    break;
                case 3:
                    url=SPONSOR_FEEDS_VIDEO_URL+feeds.get(mpos).getType()+"/"+feeds.get(mpos).getChallengeId()+"/"+feeds.get(mpos).getThumbnail();
                    Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher).into(viewHolder.iv_image1);

                    url=SPONSOR_FEEDS_VIDEO_URL+feeds.get(mpos+1).getType()+"/"+feeds.get(mpos+1).getChallengeId()+"/"+feeds.get(mpos+1).getThumbnail();
                    Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher).into(viewHolder.iv_image2);

                    url=SPONSOR_FEEDS_VIDEO_URL+feeds.get(mpos+2).getType()+"/"+feeds.get(mpos+2).getChallengeId()+"/"+feeds.get(mpos+2).getThumbnail();
                    Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher).into(viewHolder.iv_image3);

                    viewHolder.iv_image4.setImageBitmap(null);
                    viewHolder.iv_image5.setImageBitmap(null);
                    viewHolder.iv_image6.setImageBitmap(null);
                    break;
                case 4:
                    url=SPONSOR_FEEDS_VIDEO_URL+feeds.get(mpos).getType()+"/"+feeds.get(mpos).getChallengeId()+"/"+feeds.get(mpos).getThumbnail();
                    Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher).into(viewHolder.iv_image1);

                    url=SPONSOR_FEEDS_VIDEO_URL+feeds.get(mpos+1).getType()+"/"+feeds.get(mpos+1).getChallengeId()+"/"+feeds.get(mpos+1).getThumbnail();
                    Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher).into(viewHolder.iv_image2);

                    url=SPONSOR_FEEDS_VIDEO_URL+feeds.get(mpos+2).getType()+"/"+feeds.get(mpos+2).getChallengeId()+"/"+feeds.get(mpos+2).getThumbnail();
                    Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher).into(viewHolder.iv_image3);

                    url=SPONSOR_FEEDS_VIDEO_URL+feeds.get(mpos+3).getType()+"/"+feeds.get(mpos+3).getChallengeId()+"/"+feeds.get(mpos+3).getThumbnail();
                    Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher).into(viewHolder.iv_image4);
                    viewHolder.iv_image5.setImageBitmap(null);
                    viewHolder.iv_image6.setImageBitmap(null);

                    break;
                case 5:
                    url=SPONSOR_FEEDS_VIDEO_URL+feeds.get(mpos).getType()+"/"+feeds.get(mpos).getChallengeId()+"/"+feeds.get(mpos).getThumbnail();
                    Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher).into(viewHolder.iv_image1);

                    url=SPONSOR_FEEDS_VIDEO_URL+feeds.get(mpos+1).getType()+"/"+feeds.get(mpos+1).getChallengeId()+"/"+feeds.get(mpos+1).getThumbnail();
                    Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher).into(viewHolder.iv_image2);

                    url=SPONSOR_FEEDS_VIDEO_URL+feeds.get(mpos+2).getType()+"/"+feeds.get(mpos+2).getChallengeId()+"/"+feeds.get(mpos+2).getThumbnail();
                    Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher).into(viewHolder.iv_image3);

                    url=SPONSOR_FEEDS_VIDEO_URL+feeds.get(mpos+3).getType()+"/"+feeds.get(mpos+3).getChallengeId()+"/"+feeds.get(mpos+3).getThumbnail();
                    Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher).into(viewHolder.iv_image4);

                    url=SPONSOR_FEEDS_VIDEO_URL+feeds.get(mpos+4).getType()+"/"+feeds.get(mpos+4).getChallengeId()+"/"+feeds.get(mpos+4).getThumbnail();
                    Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher).into(viewHolder.iv_image5);
                    viewHolder.iv_image6.setImageBitmap(null);
                    break;
            }

        }



    */}



    int total,reminder;
    @Override
    public int getItemCount() {
        if(feeds ==null&&feeds.isEmpty())
            return 10;
        total=(int)(feeds.size()/6);
        reminder=(int)(feeds.size()%6);
        if(reminder>0){
            total=total+1;
        }
        Log.e("total",""+total);
        return total;
//        return feeds.size();
    }




    public void addAll(List<Post> mcList) {
        if(mcList!=null)
            feeds.addAll(mcList);
        notifyDataSetChanged();


    }



    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            feeds.clear();
            notifyDataSetChanged();
        }
    }

    public boolean isEmpty() { return getItemCount() == 0; }

    public void addLoadingFooter() {
        isLoadingAdded = true;
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
    }

    public class GridViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_image1;
        ImageView iv_image2;
        ImageView iv_image3;
        ImageView iv_image4;
        ImageView iv_image5;
        ImageView iv_image6;

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setBackgroundColor(Color.parseColor("#00FF00"));
//            itemView.getLayoutParams().width=width;
//            itemView.getLayoutParams().height=height*3/4;

            iv_image1=itemView.findViewById(R.id.iv_image1);
            iv_image2=itemView.findViewById(R.id.iv_image2);
            iv_image3=itemView.findViewById(R.id.iv_image3);
            iv_image4=itemView.findViewById(R.id.iv_image4);
            iv_image5=itemView.findViewById(R.id.iv_image5);
            iv_image6=itemView.findViewById(R.id.iv_image6);
        }
    }

}
