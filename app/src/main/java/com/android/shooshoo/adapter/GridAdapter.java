package com.android.shooshoo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.shooshoo.R;
import com.android.shooshoo.models.Comment;
import com.android.shooshoo.models.Feed;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

import static com.android.shooshoo.utils.ApiUrls.SPONSOR_FEEDS_VIDEO_URL;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridViewHolder> {
    List<Feed> feeds;
    Context context;
    private boolean isLoadingAdded=false;
int width,height;
    public GridAdapter(Context context,List<Feed> feeds) {
        this.context=context;
        this.feeds = feeds;
        height=context.getResources().getDisplayMetrics().heightPixels;
        width=context.getResources().getDisplayMetrics().widthPixels;
    }

    @NonNull
    @Override
    public GridAdapter.GridViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.image_model,viewGroup,false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridAdapter.GridViewHolder viewHolder, int pos) {
        int mpos=pos * 6;
        String url=null;
        Log.e("mpos",""+mpos);
        Log.e("total",""+total);
        Log.e("reminder",""+reminder);
        if(pos<getItemCount()-1){
            setImages(6,mpos,viewHolder);
        }else if(pos==getItemCount()-1){
            if(reminder>0){
                setImages(reminder,mpos,viewHolder);
            }else if(reminder==0){
                setImages(6,mpos,viewHolder );
            }
        }
    }

    private void setImages(int totalImages, int mpos, GridViewHolder viewHolder) {
//        if(totalImages==0)
//            totalImages=5;
        for(int index=0;index<6;index++){
            if(index<totalImages) {
                if(feeds.get(mpos + index).getUrl().endsWith(".jpg")||feeds.get(mpos + index).getUrl().endsWith(".JPG")||feeds.get(mpos + index).getUrl().endsWith(".jpeg")||feeds.get(mpos + index).getUrl().endsWith(".png")||feeds.get(mpos + index).getUrl().endsWith(".JPEG")||feeds.get(mpos + index).getUrl().endsWith(".PNG"))
                {
                    String url = feeds.get(mpos + index).baseUrl() + "/" + feeds.get(mpos + index).getUrl();
                    Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher).into(viewHolder.imageViews.get(index));
                }else {
                    String url = feeds.get(mpos + index).baseUrl() + "/" + feeds.get(mpos + index).getThumbnail();

                    Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher).into(viewHolder.imageViews.get(index));
                }
            }else {
                viewHolder.imageViews.get(index).setImageBitmap(null);
            }

        }

    }


    int total=-1,reminder=-1;
    @Override
    public int getItemCount() {
        reminder=-1;
        if(feeds ==null)
            return 0;
        if(feeds.isEmpty())
            return 0;

        total=(int)(feeds.size()/6);
        reminder=(int)(feeds.size()%6);
        if(reminder>0){
            total=total+1;
        }
        return total;
//        return feeds.size();
    }




    public void addAll(List<Feed> mcList) {
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
   /*     ImageView iv_image1;
        ImageView iv_image2;
        ImageView iv_image3;
        ImageView iv_image4;
        ImageView iv_image5;
        ImageView iv_image6;*/
        @BindViews({R.id.iv_image1,R.id.iv_image2,R.id.iv_image3,R.id.iv_image4,R.id.iv_image5,R.id.iv_image6})
        List<ImageView> imageViews;

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
//            itemView.getLayoutParams().width=width;
            itemView.getLayoutParams().height=height;
            ButterKnife.bind(this,itemView);

//            iv_image1=itemView.findViewById(R.id.iv_image1);
//            iv_image2=itemView.findViewById(R.id.iv_image2);
//            iv_image3=itemView.findViewById(R.id.iv_image3);
//            iv_image4=itemView.findViewById(R.id.iv_image4);
//            iv_image5=itemView.findViewById(R.id.iv_image5);
//            iv_image6=itemView.findViewById(R.id.iv_image6);
        }
    }

}
