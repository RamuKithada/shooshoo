package com.android.shooshoo.views;

import com.android.shooshoo.presenters.BasePresenter;

import okhttp3.ResponseBody;

public interface UpdateUserInfoView extends BaseView {

    void onUpdateUserInfo(ResponseBody responseBody);
}
