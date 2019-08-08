package com.android.shooshoo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.models.Feed;
import com.android.shooshoo.models.Notification;
import com.android.shooshoo.utils.ApiUrls;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecentChatMsgAdapter extends RecyclerView.Adapter<RecentChatMsgAdapter.CatViewHolder> {

    private List<Notification> notifications;
    Context context;
    boolean isLoadingAdded;

    public RecentChatMsgAdapter(List<Notification> notifications, Context context) {
        this.notifications = notifications;
        this.context = context;
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_notification_list_item,null);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CatViewHolder catViewHolder,final int pos) {
        Notification notification=getItem(pos);
        if(notification!=null){
        catViewHolder.msgText.setText(Html.fromHtml(notification.getMessage()));
        catViewHolder.timeText.setText(ApiUrls.getDurationTimeStamp2(notification.getCreatedOn()));
            Picasso.with(context).load(ApiUrls.PROFILE_IMAGE_URL+notification.getUserImage()).noPlaceholder().into(catViewHolder.imageProfile);
            if(notification.getFeed()!=null)
            {
                Feed feed=notification.getFeed();
                if(feed.getUrl().endsWith(".jpg")||feed.getUrl().endsWith(".JPG")||feed.getUrl().endsWith(".jpeg")||feed.getUrl().endsWith(".png")||feed.getUrl().endsWith(".JPEG")||feed.getUrl().endsWith(".PNG"))
                {
                    Picasso.with(context).load(feed.baseUrl()+feed.getUrl()).noPlaceholder().into(catViewHolder.msgImage);
                }else {
                    Picasso.with(context).load(feed.baseUrl()+feed.getThumbnail()).noPlaceholder().into(catViewHolder.msgImage);
                }

            }else {
                catViewHolder.msgImage.setImageBitmap(null);
            }
        }
    }

    @Override
    public int getItemCount() {
        if(notifications==null)
            return 0;

        return notifications.size();
    }
    public Notification getItem(int position)
    {
        if(notifications!=null)
            return notifications.get(position);
        else
            return null;
    }
    public void addLoadingFooter() {
        isLoadingAdded = true;
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

    }


    public class CatViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imageProfile;
        TextView msgText;
        TextView timeText;
        ImageView msgImage;
        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProfile=itemView.findViewById(R.id.image_profile);
            msgImage=itemView.findViewById(R.id.image_pic);
            msgText=itemView.findViewById(R.id.msg_text);
            timeText=itemView.findViewById(R.id.time_text);
            msgImage.setImageBitmap(null);
        }
    }

    public void addNotifications(List<Notification> notifications) {
           if(notifications!=null)
            this.notifications.addAll(notifications);
        notifyDataSetChanged();
    }
}
