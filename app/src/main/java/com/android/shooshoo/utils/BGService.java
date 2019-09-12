package com.android.shooshoo.utils;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.os.IBinder;
import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.ViewAllChallengesResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class BGService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_WINNERS = "com.android.shooshoo.Utils.action.DECLARE";
    private static final String ACTION_CHECK = "com.android.shooshoo.Utils.action.CHECK";

    // TODO: Rename parameters
    private static final String EXTRA_ID = "com.android.shooshoo.Utils.extra.CHALLENGE_ID";
    private static final String EXTRA_TYPE = "com.android.shooshoo.Utils.extra.CHALLENGE_TYPE";

    public BGService() {
        super("BGService");
    }


    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent,flags,startId);
        return START_REDELIVER_INTENT;
    }

    public static void startActionDeclare(Context context, String param1, String param2) {
        Intent intent = new Intent(context, BGService.class);
        intent.setAction(ACTION_WINNERS);
        intent.putExtra(EXTRA_ID, param1);
        intent.putExtra(EXTRA_TYPE, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionCheck(Context context, String param1, String param2) {
        Intent intent = new Intent(context, BGService.class);
        intent.setAction(ACTION_CHECK);
        intent.putExtra(EXTRA_ID, param1);
        intent.putExtra(EXTRA_TYPE, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_WINNERS.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_ID);
                final String param2 = intent.getStringExtra(EXTRA_TYPE);
                handleActionDeclare(param1, param2);
            } else if (ACTION_CHECK.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_ID);
                final String param2 = intent.getStringExtra(EXTRA_TYPE);
                handleActionCheck(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionDeclare(String param1, String param2) {

//        Log.e("Testing", param1+" : "+param2);
        Date date=new Date(System.currentTimeMillis());
//        Log.d("Testing", "Calender time:"+date.toString());
        if(detector.isConnectingToInternet()){
            RetrofitApis.Factory.create(this).declareWinners(param1,param2).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    Log.e("Testing", "onResponse" );
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    Log.e("Testing", "onFailure" );
                }
            });

        }


    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionCheck(String param1, String param2) {

        if(detector.isConnectingToInternet()){
            Call<ViewAllChallengesResponse> call=RetrofitApis.Factory.create(this).viewAllChallenges(ApiUrls.SPONSERS,"",String.valueOf(100),String.valueOf(0));
//            Log.e(param1,param2);
            call.enqueue(new Callback<ViewAllChallengesResponse>() {
                @Override
                public void onResponse(Call<ViewAllChallengesResponse> call, Response<ViewAllChallengesResponse> response) {
                    if(response.isSuccessful()){
                        ViewAllChallengesResponse finalChallengesResponse=  response.body();
                        if(finalChallengesResponse!=null){
                            if(finalChallengesResponse.getChallenges().size()>0){
                                for (Challenge challenge:finalChallengesResponse.getChallenges()) {
                                    if(challenge.getDeclaredWinners().equalsIgnoreCase("0")){
                                        setTask(challenge);
                                    }

                                }


                            }


                        }

                    }

                }

                @Override
                public void onFailure(Call<ViewAllChallengesResponse> call, Throwable t) {

                }
            });


        }


    }

    private void setTask(Challenge challenge) {
//        Log.e("Testing Challenge",challenge.getChallengeName());
        Date date=null;
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
             date=simpleDateFormat.parse(challenge.getEndDate()+" "+challenge.getEndTime());
             if(date.before(new Date())){
                 handleActionDeclare(challenge.getChallengeId(),challenge.getType());
                 return;
             }


        }catch (ParseException e){
            e.printStackTrace();
        }
        if(date!=null) {
            Calendar cur_cal = Calendar.getInstance();
            cur_cal.setTimeInMillis(date.getTime());
            cur_cal.add(Calendar.SECOND, 30);
//            Log.d("Testing", "Calender Set time:" + cur_cal.getTime());
            Intent intent = new Intent(BGService.this, BGService.class);
            intent.setAction(ACTION_WINNERS);
            intent.putExtra(EXTRA_ID, challenge.getChallengeId());
            intent.putExtra(EXTRA_TYPE, challenge.getType());
//            Log.d("Testing", "Intent created");
            PendingIntent pendingIntent = PendingIntent.getService(BGService.this, 0, intent, 0);
            AlarmManager alarm_manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarm_manager.set(AlarmManager.RTC, cur_cal.getTimeInMillis(), pendingIntent);
        }
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
//        Log.d("Testing", "Service got created");
//        Toast.makeText(this, "ServiceClass.onCreate()", Toast.LENGTH_LONG).show();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
//        Log.d("Testing", "Service got onBind");
        return null;
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
//        Log.d("Testing", "Service got onDestroy");
        super.onDestroy();
    }
ConnectionDetector detector=null;
//    UserSession userSession=null;
    @Override
    public void onStart(Intent intent, int startId) {
        detector=new ConnectionDetector(this);
//         userSession=new UserSession(this);
        super.onStart(intent, startId);
//        Toast.makeText(this, "ServiceClass.onStart()", Toast.LENGTH_LONG).show();
//        Log.d("Testing", "Service got started");
    }
}
