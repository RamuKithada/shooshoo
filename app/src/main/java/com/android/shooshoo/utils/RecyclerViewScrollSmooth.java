package com.android.shooshoo.utils;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

public class RecyclerViewScrollSmooth implements RecyclerView.OnItemTouchListener{

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                rv.getParent().requestDisallowInterceptTouchEvent(true);
                break;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}

