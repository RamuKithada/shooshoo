package com.android.shooshoo.utils;

import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class FirebaseService extends  FirebaseMessagingService {
    private static final String TAG = FirebaseService.class.getName();
    UserSession session;
    RetrofitApis apis;

    @Override
    public void onCreate() {
        super.onCreate();
        session =new UserSession(getApplicationContext());
        apis=RetrofitApis.Factory.create(getApplicationContext());
    }

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
            Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
 /*       if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            loadData(remoteMessage.getData());
                return;
        }
        */








//        // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
//            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
//            Log.d(TAG, "Message Notification Title: " + remoteMessage.getNotification().getTitle());
//            Log.d(TAG, "Message Notification BodyLocalizationKey: " + remoteMessage.getNotification().getBodyLocalizationKey());
//            Log.d(TAG, "Message Notification ClickAction: " + remoteMessage.getNotification().getClickAction());
//            sendNotification(remoteMessage.getNotification().getBody());
//        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]


    // [START on_new_token]
    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        storeRegIdInPref(token);
//        sendRegistrationToServer(token);
    }

    private void storeRegIdInPref(String token) {
        session.storeToken(token);
        Log.e("storeRegIdInPref",token);
    }
    // [END on_new_token]

    /**
     * Schedule a job using FirebaseJobDispatcher.
     */
    private void scheduleJob() {
        // [START dispatch_job]
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Job myJob = dispatcher.newJobBuilder()
                .setService(MyJobService.class)
                .setTag("my-job-tag")
                .build();
        dispatcher.schedule(myJob);
        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
//    private void sendRegistrationToServer(final String token) {
//        session.storeToken(token);
//        ConnectionDetector detector=new ConnectionDetector(this);
//             if(!detector.isConnectingToInternet())
//              return;
//
//            apis.OnOffNotificaion(DEVICE_TYPE,token,session.getNotificationState(),session.getUserId())
//                    .enqueue(new Callback<RequestResult>() {
//                        @Override
//                        public void onResponse(Call<RequestResult> call, Response<RequestResult> response) {
//                            if(response!=null){
//                                if(response.isSuccessful()){
//                                    if(response.body()!=null)
//                                    if(response.body().getStatus()==1){
//                                        session.storeToken(token);
//                                    }
//
//
//                                }
//
//                            }
//
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<RequestResult> call, Throwable t) {
//                        }
//                    });
//
//
//
//
//
//        /*MyService service= MyService.Factory.create(getApplicationContext());
//        JsonObject  object=new JsonObject();
//        object.addProperty("a_u_id",session.getSingleField(SessionManager.KEY_ID));
//        object.addProperty("token",token);
//
//        Call<RegResult> call=service.updateToken(object, ApiUrl.context_type);
//        call.enqueue(new Callback<RegResult>() {
//            @Override
//            public void onResponse(Call<RegResult> call, Response<RegResult> response) {
//                RegResult regResult=response.body();
//                Log.e("message",regResult.getMessage());
//            }
//
//            @Override
//            public void onFailure(Call<RegResult> call, Throwable t) {
//                Log.e("message","service failed");
//            }
//        });*/
//        // TODO: Implement this method to send token to your app server.
//    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
//    private void sendNotification(String messageBody) {
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        String channelId = getString(R.string.default_notification_channel_id);
//        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder =
//                new NotificationCompat.Builder(this, channelId)
//                        .setSmallIcon(R.mipmap.appicon)
//                        .setContentTitle(getString(R.string.app_name))
//                        .setContentText(messageBody)
//                        .setAutoCancel(true)
//                        .setSound(defaultSoundUri)
//                        .setContentIntent(pendingIntent);
//        if(messageBody.length()>20){
//            notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody));
//        }else {
//            notificationBuilder.setContentText(messageBody);
//
//        }
//
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        // Since android Oreo notification channel is needed.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(channelId,
//                    getString(R.string.notifications_admin_channel_name),
//                    NotificationManager.IMPORTANCE_DEFAULT);
//            notificationManager.createNotificationChannel(channel);
//        }
//
//        notificationManager.notify(2 /* ID of notification */, notificationBuilder.build());
//    }
//    private void loadData(Map<String,String> map){
//        String   textContent=(String) map.get("text");
//        String image_utl=(String) map.get("image");
//        String newsid=(String) map.get("newsId");
//        sendNotification(this,textContent,image_utl,newsid);
//
//
////        try {
////            URL url = new URL(IMAGE_URL+image_utl);
////            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
////             sendDataNotificatin(image,textContent);
////        } catch (IOException e) {
////            e.printStackTrace();
////             Bitmap image = BitmapFactory.decodeResource(getResources(),R.mipmap.appicon);
////             sendDataNotificatin(image,textContent);
////        }
//    }
//    private void sendDataNotificatin(Bitmap bitmap,String textContent){
//
//        try {
//            if(bitmap==null)
//                   bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.appicon);
//               else
//                   bitmap=getResizedBitmap(bitmap,100,100);
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//        String channelId = getString(R.string.default_notification_channel_id);
//        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channelId)
//                .setSmallIcon(R.mipmap.appicon)
//                .setLargeIcon(bitmap)
//                .setContentTitle(getString(R.string.app_name))
//                .setContentText(textContent)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent)
//                .setPriority(NotificationCompat.BADGE_ICON_LARGE);
//
//        // Add as notification
//        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(0, mBuilder.build());
//        }catch (Exception e){
//        }
//
//    }


//    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
//        int width = bm.getWidth();
//        int height = bm.getHeight();
//        float scaleWidth = ((float) newWidth) / width;
//        float scaleHeight = ((float) newHeight) / height;
//        // CREATE A MATRIX FOR THE MANIPULATION
//        Matrix matrix = new Matrix();
//        // RESIZE THE BIT MAP
//        matrix.postScale(scaleWidth, scaleHeight);
//
//        // "RECREATE" THE NEW BITMAP
//        Bitmap resizedBitmap = Bitmap.createBitmap(
//                bm, 0, 0, width, height, matrix, false);
//        return resizedBitmap;
//    }


    NotificationCompat.Builder notificationBuilder;
    NotificationManager notificationManager;
    Bitmap bitmap = null;
//    private void sendNotification(Context context, String title, String image,String newsid) {
//        String imageUrl=IMAGE_URL+image;
//        Intent intent = new Intent(context, SplashActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra("newsid",newsid);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        String channelId = context.getString(R.string.default_notification_channel_id);
//        Bitmap icon_bitmap=BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher);
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        notificationBuilder =
//                new NotificationCompat.Builder(context, channelId)
//                        .setSmallIcon(R.drawable.notification_status_icon)
////                        .setContentTitle(title)
//                        .setContentText(title)
//                        .setLargeIcon(icon_bitmap)
//                        .setAutoCancel(true)
//                        .setSound(defaultSoundUri)
//                        .setContentIntent(pendingIntent);
//
//
//        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(channelId,
//                    getString(R.string.notifications_admin_channel_name),
//                    NotificationManager.IMPORTANCE_DEFAULT);
//            notificationManager.createNotificationChannel(channel);
//        }
//        if (!TextUtils.isEmpty(imageUrl)) {
//            getImageBitmap(imageUrl,newsid);
//        }
//    }
     int id=12;
//    private Bitmap getImageBitmap(final String shareImageUrl ,final  String newsid) {
//        if (shareImageUrl == null) return null;
//        Random random=new Random();
//        try {
//            id=Integer.valueOf(newsid);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
////           id=random.nextInt();
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                try {
//                    bitmap = BitmapFactory.decodeStream((new URL(shareImageUrl)).openConnection().getInputStream());
//
//                    notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap));
//                    notificationManager.notify(id/* ID of notification */, notificationBuilder.build());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    bitmap = null;
//                    notificationManager.notify(id /* ID of notification */, notificationBuilder.build());
//                }
//            }
//        }).start();
//        return bitmap;
//    }



}