package com.android.shooshoo.utils;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
public class BottomNavigationBehavior extends CoordinatorLayout.Behavior<RelativeLayout> {

    public BottomNavigationBehavior() {
        super();
    }

    public BottomNavigationBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, RelativeLayout child, View dependency) {
        boolean dependsOn = dependency instanceof FrameLayout;

        return dependsOn;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, RelativeLayout child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, RelativeLayout child, View target, int dx, int dy, int[] consumed) {
        if (dy < 0) {
            showBottomNavigationView(child);
        } else if (dy > 0) {
            hideBottomNavigationView(child);
        }
    }

    private void hideBottomNavigationView(RelativeLayout view) {
        view.animate().translationY(view.getHeight());
    }

    private void showBottomNavigationView(RelativeLayout view) {
        view.animate().translationY(0);
    }
}