package com.android.shooshoo.views;

import com.android.shooshoo.models.Notification;

import java.util.List;

public interface NotificationView extends BaseView {
    void onNotificationList(List<Notification> list,int total);
}
