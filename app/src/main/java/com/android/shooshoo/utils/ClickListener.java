package com.android.shooshoo.utils;

import android.view.View;

public interface ClickListener {
     void onClick(View view, final int position);
     void onLongClick(View view, int position);
}
