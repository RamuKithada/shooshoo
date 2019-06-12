package com.android.shooshoo.views;

import okhttp3.ResponseBody;

/**
 * {@link UpdateUserInfoView} is implemented by {@link com.android.shooshoo.presenters.UpdateUserInfoPresenter}
 * to interact with activity
 */
public interface UpdateUserInfoView extends BaseView {
    /** if information of user is updated successfully then it gives the all information of the user
     *
     * @param responseBody is response of the service
     */
    void onUpdateUserInfo(ResponseBody responseBody);
}
