package com.android.shooshoo.utils;

import android.view.View;

/**
 *  this is listener for the {@link android.support.v7.widget.RecyclerView} to get selection of item it works like on item click
 */
public interface ClickListener {
     /**
      *  called then the itemview is clicked
      * @param view itemview
      * @param position position of item
      */
     void onClick(View view, final int position);
     /**
      *  called then the itemview is longPressed
      * @param view itemview
      * @param position position of item
      */
     void onLongClick(View view, int position);
}
