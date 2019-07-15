package com.android.shooshoo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.models.Comment;
import com.android.shooshoo.utils.ApiUrls;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeedCommentsAdapter extends RecyclerView.Adapter<FeedCommentsAdapter.CommentHolder> {

    Context context;
    List<Comment> comments=new ArrayList<>();
    FeedCommentListener listener;
    boolean isLoadingAdded;

    public FeedCommentsAdapter(Context context,FeedCommentListener listener) {
        this.context = context;
//        this.comments = comments;
        this.listener=listener;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feed_comment_listitem,null);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CommentHolder holder, final int position) {

         holder.iv_replay.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(listener!=null)
                     listener.onReply(comments.get(position));

             }
         });
         holder.iv_report.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(listener!=null)
                     listener.onReport(comments.get(position));
             }
         });
         if(comments.get(position).getImage()!=null)
        Picasso.with(context).load(ApiUrls.PROFILE_IMAGE_URL+comments.get(position).getImage()).noPlaceholder().into(holder.iv_profile_pic);
         if(comments.get(position).getUserName()!=null)
             holder.tv_user_name.setText("@"+comments.get(position).getUserName());
         else
             holder.tv_user_name.setText("@user"+comments.get(position).getUserId());
         holder.tv_comment.setText(comments.get(position).getComment());
         holder.tv_time.setText(ApiUrls.getDurationTimeStamp(comments.get(position).getCreatedOn()));


    }

    @Override
    public int getItemCount() {
       if(comments==null)
        return 0;
       else
        return comments.size();
    }

    public class CommentHolder extends RecyclerView.ViewHolder{
        CircleImageView iv_profile_pic;
        TextView tv_user_name,tv_comment,tv_time;
        ImageView iv_replay,iv_report;


        public CommentHolder(@NonNull View itemView) {
            super(itemView);
            iv_profile_pic=itemView.findViewById(R.id.iv_profile_pic);
            tv_user_name=itemView.findViewById(R.id.tv_user_name);
            tv_comment=itemView.findViewById(R.id.tv_comment);
            tv_time=itemView.findViewById(R.id.tv_time);
            iv_replay=itemView.findViewById(R.id.iv_replay);
            iv_report=itemView.findViewById(R.id.iv_report);
        }
    }


    public void add(Comment mc) {
        comments.add(mc);
        notifyItemInserted(comments.size() - 1);
    }

    public void addAll(List<Comment> mcList) {
        for (Comment mc : mcList) { add(mc); }
    }

    public void remove(Comment city) {
        int position = comments.indexOf(city);
        if (position > -1) {
            comments.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) { remove(getItem(0)); }
    }

    public boolean isEmpty() { return getItemCount() == 0; }

    public void addLoadingFooter() {
        isLoadingAdded = true;
//        add(new Comment());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

      /*  int position = comments.size() - 1;
        Comment item = getItem(position);
        if (item != null) {
            comments.remove(position);
            notifyItemRemoved(position);
        }*/
    }

    public Comment getItem(int position) {
        return comments.get(position);
    }



    public interface FeedCommentListener{
        void onReply(Comment comment);
        void onReport(Comment comment);
    }
}
