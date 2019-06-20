package com.android.shooshoo.views;

import com.android.shooshoo.models.Feed;

import java.util.List;

public interface FeedsView extends BaseView{
    void onFeedsLoaded(List<Feed> feeds);
    void onFeedLike(int status,String message);
    void onFeedViewed(int status,String message);
    void onFollowed(int status,String message);
}
