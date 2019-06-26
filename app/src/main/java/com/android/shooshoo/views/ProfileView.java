package com.android.shooshoo.views;

import com.android.shooshoo.models.Brand;
import com.android.shooshoo.models.Post;
import com.android.shooshoo.models.UserBankDetails;
import com.android.shooshoo.models.UserInfo;

import java.util.List;

public interface ProfileView extends BaseView {
    void onProfileData(UserInfo userInfo);
    void onBrands(List<Brand> brands);
    void onPosts(List<Post> posts);
    void onBankDetails(UserBankDetails bankDetails);
}
