package com.android.shooshoo.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.android.shooshoo.R;

public class SplashActivity extends BaseActivity {
Runnable runnable=new Runnable() {
    @Override
    public void run() {
//        if(userSession.isLogin()){
        /**
        * intent is used to open FeedsActivity i.e. List of feeds shown to users
        * */
            Intent intent = new Intent(SplashActivity.this, FeedsActivity.class);
            startActivity(intent);
//        }else {
//            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//            startActivity(intent);
//        }
        finish();
    }
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msplash);
        /**
         *  Handler used to show the splash for 2 seconds after 2 sec it opens Feeds list
         * */
        new Handler().postDelayed(runnable,2000);
    }
}
