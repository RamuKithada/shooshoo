package com.android.shooshoo.views;

import android.content.Context;

public interface BaseView {

    Context getContext();

    void showMessage(int stringId);

    void showMessage(String message);

    void showProgressIndicator(Boolean show);

}