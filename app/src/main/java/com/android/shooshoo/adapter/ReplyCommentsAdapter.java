package com.android.shooshoo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.models.Comment;
import com.android.shooshoo.models.CommentReply;
import com.android.shooshoo.utils.ApiUrls;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReplyCommentsAdapter extends RecyclerView.Adapter<ReplyCommentsAdapter.CommentHolder> {

    Context context;
    List<CommentReply> comments=new ArrayList<>();
    boolean isLoadingAdded;

    public ReplyCommentsAdapter(Context context, List<CommentReply> comments) {
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.replay_comment_listitem,null);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CommentHolder holder, final int position) {

         holder.tv_reply.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
//                 if(listener!=null)
//                     listener.onReply(comments.get(position));

             }
         });
         holder.iv_like.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
//                 if(listener!=null)
//                     listener.onReport(comments.get(position));
             }
         });
         if(comments.get(position).getImage()!=null)
        Picasso.with(context).load(ApiUrls.PROFILE_IMAGE_URL+comments.get(position).getImage()).noPlaceholder().into(holder.iv_profile_pic);
         String userName="<b>";
         if(comments.get(position).getUserName()!=null)
             userName=userName+comments.get(position).getUserName()+"</b> ";
         else
             userName=userName+comments.get(position).getUserId()+" </b>";
         holder.tv_comment.setText(Html.fromHtml(userName+" "+comments.get(position).getComment()));
         holder.tv_time.setText(ApiUrls.getDurationTimeStamp2(comments.get(position).getCreatedOn()));
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


        public CommentHolder(@NonNull View itemView) {
            super(itemView);
            iv_profile_pic=itemView.findViewById(R.id.iv_profile_pic);
            tv_comment=itemView.findViewById(R.id.tv_comment);
            tv_time=itemView.findViewById(R.id.tv_time);
            tv_reply=itemView.findViewById(R.id.tv_reply);
            iv_like=itemView.findViewById(R.id.iv_like);
        }
    }


    public void add(CommentReply mc) {
        comments.add(mc);
        notifyItemInserted(comments.size() - 1);
    }

    public void addAll(List<CommentReply> mcList) {
        for (CommentReply mc : mcList) { add(mc); }
    }

    public void remove(Comment city) {
        int position = comments.indexOf(city);
        if (position > -1) {
            comments.remove(position);
            notifyItemRemoved(position);
        }
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

    public CommentReply getItem(int position) {
        return comments.get(position);
    }

}
