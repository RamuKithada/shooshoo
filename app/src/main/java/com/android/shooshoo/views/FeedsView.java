package com.android.shooshoo.views;

import com.android.shooshoo.models.Feed;

import java.util.List;

public interface FeedsView extends BaseView{
    void onFeedsLoaded(List<Feed> feeds);
}
