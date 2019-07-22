package com.android.shooshoo.utils;

import com.android.shooshoo.models.CircleEntity;

public interface OnRadarListener {
    void onItemClicked(float x, float y, CircleEntity entity);
    void dismissPopup();
}
