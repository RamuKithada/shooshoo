package com.android.shooshoo.views;

import com.android.shooshoo.models.Post;

import java.util.List;

public interface PostChallengeView extends BaseView{
    void onSuccessfulUpload(String msg);
    void  onRecentPosts(List<Post> posts);
}
