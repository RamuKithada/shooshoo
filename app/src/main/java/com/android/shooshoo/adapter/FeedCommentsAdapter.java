package com.android.shooshoo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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

         holder.tv_reply.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(listener!=null)
                     listener.onReply(comments.get(position));

             }
         });
         holder.iv_like.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(listener!=null)
                     listener.onLike(comments.get(position));
             }
         });
         if(comments.get(position).getImage()!=null)
        Picasso.with(context).load(ApiUrls.PROFILE_IMAGE_URL+comments.get(position).getImage()).noPlaceholder().into(holder.iv_profile_pic);
         String userName="<b>";
         if(comments.get(position).getUserName()!=null)
             userName=userName+comments.get(position).getUserName()+"</b>";
         else
             userName=userName+comments.get(position).getUserId()+"</b>";
          holder.tv_comment.setText(Html.fromHtml(userName+" "+comments.get(position).getComment()));
          holder.tv_time.setText(ApiUrls.getDurationTimeStamp2(comments.get(position).getCreatedOn()));
          ReplyCommentsAdapter  replyCommentsAdapter=new ReplyCommentsAdapter(context,comments.get(position).getCommentReply());
          holder.replyList.setAdapter(replyCommentsAdapter);



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
        TextView tv_comment,tv_time,tv_reply;
        ImageView iv_like;
        RecyclerView replyList;
        public CommentHolder(@NonNull View itemView) {
            super(itemView);
            iv_profile_pic=itemView.findViewById(R.id.iv_profile_pic);
            tv_comment=itemView.findViewById(R.id.tv_comment);
            tv_time=itemView.findViewById(R.id.tv_time);
            tv_reply=itemView.findViewById(R.id.tv_reply);
            iv_like=itemView.findViewById(R.id.iv_like);
            replyList=itemView.findViewById(R.id.replay_list);
            replyList.setLayoutManager(new LinearLayoutManager(context));

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
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
    }

    public Comment getItem(int position) {
        return comments.get(position);
    }



    public interface FeedCommentListener{
        void onReply(Comment comment);
        void onLike(Comment comment);
    }
}
