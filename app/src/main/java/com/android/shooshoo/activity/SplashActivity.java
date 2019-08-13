package com.android.shooshoo.activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import com.android.shooshoo.R;
import com.android.shooshoo.activity.feeds.FeedsActivity;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.UserSession;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class SplashActivity extends BaseActivity{
    UserSession userSession;
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
        userSession=new UserSession(this);
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.notifications_admin_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_HIGH));
        }
getInstanceId();

    }



    public void getInstanceId(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("onComplete", "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        final String token = task.getResult().getToken();
                        if (token != null)
                            userSession.storeToken(token);

                        Log.w("onComplete", "InstanceId "+token);

                    }
                });
    }




}
