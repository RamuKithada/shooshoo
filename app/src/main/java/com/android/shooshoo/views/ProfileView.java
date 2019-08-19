package com.android.shooshoo.views;

import com.android.shooshoo.models.Company;
import com.android.shooshoo.models.UserInfo;

import java.util.List;

public interface ProfileView extends BaseView {
    void onProfileData(UserInfo userInfo);
    void onBrands(List<Company> brands);
}
