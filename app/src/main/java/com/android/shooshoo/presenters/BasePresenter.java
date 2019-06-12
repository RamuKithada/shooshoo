package com.android.shooshoo.presenters;

public interface BasePresenter<V> {

        /**
         * to attach the view to presenter
         * @param view is the view object that has to bind with this presenter
         */
        void attachView(V view);
        /**
         * to detach the view from presenter
         *
         */
        void detachView();
        }