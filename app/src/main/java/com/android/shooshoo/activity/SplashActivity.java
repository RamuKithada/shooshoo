package com.android.shooshoo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import com.android.shooshoo.R;
import com.android.shooshoo.utils.ConnectionDetector;
public class SplashActivity extends BaseActivity {
    ConnectionDetector detector;
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
         *  Handler used to show the splash for 2 seconds after 2 sec it opens Feed list
         * */
        detector=new ConnectionDetector(this);
        if(detector.isConnectingToInternet())
        new Handler().postDelayed(runnable,2000);
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.datepicker);
            // Add the buttons
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                    finish();
                }
            });
           builder.setMessage("Please check your Internet connection");


// Create the AlertDialog
            AlertDialog dialog = builder.create();
            dialog.show();
        }


    }
}
