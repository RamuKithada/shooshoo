package com.android.shooshoo.views;

import com.android.shooshoo.models.Feed;
import java.util.List;

import okhttp3.ResponseBody;

public interface PostChallengeView extends BaseView{
    void onSuccessfulUpload(String msg);
    void  onRecentPosts(List<Feed> posts);
    void onRules(List<String> rules);
    void onChallengeInfo(ResponseBody responseBody);
}
