package com.android.shooshoo.views;

import android.content.Context;

public interface BaseView {
    /**
     * this is used to give context of the activity
     * @return Activity context
     */
    Context getContext();

    /**
     * show the toast
     * @param stringId is String Resources id
     */
    void showMessage(int stringId);

    /**
     * show the toast
     * @param message is Message of the string
     */
    void showMessage(String message);

    /**Show or Hide the ProgressDialog
     *
     * @param show if true show the ProgressDialog else dismisses the ProgressDialog
     */
    void showProgressIndicator(Boolean show);

}