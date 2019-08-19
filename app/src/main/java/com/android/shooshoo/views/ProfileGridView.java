package com.android.shooshoo.views;

import com.android.shooshoo.models.Feed;

import java.util.List;

public interface ProfileGridView extends BaseView {
    void onPosts(List<Feed> feeds,int total);
}
