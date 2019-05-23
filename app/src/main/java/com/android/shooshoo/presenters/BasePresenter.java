package com.android.shooshoo.presenters;

public interface BasePresenter<V> {
        void attachView(V view);

        void detachView();
        }