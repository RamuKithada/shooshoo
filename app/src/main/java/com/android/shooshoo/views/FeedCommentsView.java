package com.android.shooshoo.views;

import com.android.shooshoo.models.Comment;

import java.util.List;

public interface FeedCommentsView extends BaseView {

    void onCommentPosted(int status,String msg);
    void onReplyPosted(int status,String msg);
    void onAllComments(List<Comment> comments,int total);
}
