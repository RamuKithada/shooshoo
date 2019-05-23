package com.android.shooshoo.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import im.ene.toro.widget.Container;

public class MyContainer extends Container {
    public MyContainer(Context context) {
        super(context);
    }

    public MyContainer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyContainer(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    public void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
    }
}
