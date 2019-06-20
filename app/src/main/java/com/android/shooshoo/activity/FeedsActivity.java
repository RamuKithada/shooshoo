package com.android.shooshoo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.shooshoo.BuildConfig;
import com.android.shooshoo.R;
import com.android.shooshoo.adapter.FullVideoAdapter;
import com.android.shooshoo.models.Feed;
import com.android.shooshoo.presenters.FeedsPresenter;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.utils.UserSession;
import com.android.shooshoo.views.FeedsView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import im.ene.toro.widget.Container;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.android.shooshoo.utils.ApiUrls.SPONSOR_FEEDS_VIDEO_URL;

/**
 * Feed activity is used to show the list new feeds
 * container is used to show list of videos,it is subclass if RecyclerView,it is imported  from Toro player
 * please check Toro player library
 * adapter is recyclerview adapter for container
 *
 *
 *
 */

public class FeedsActivity extends BaseActivity implements FullVideoAdapter.FeedClickListener, FeedsView {
    Container container;
    FullVideoAdapter adapter;
    LinearLayoutManager layoutManager;
    RelativeLayout bottom_view;
    private ArrayList<Feed> modelArrayList=new ArrayList<>();



    @BindView(R.id.navigation_home)
    LinearLayout navigation_home;
    @BindView(R.id.navigation_challengers)
    LinearLayout navigation_challengers;
    @BindView(R.id.navigation_feed)
    LinearLayout navigation_feed;
    @BindView(R.id.navigation_winners)
    LinearLayout navigation_winners;
    @BindView(R.id.navigation_radar)
    LinearLayout navigation_radar;
    /**
     * feedsPresenter is used to load the data;
     */
    FeedsPresenter feedsPresenter;

    ConnectionDetector detector;
    View view=null;
    private View.OnClickListener bottomNavigationOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           view=v;
            Intent intent=new Intent(FeedsActivity.this,HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            switch (v.getId()) {
                case R.id.navigation_home:
                    intent.putExtra("icon",0);
                    break;
                case R.id.navigation_challengers:
                    intent.putExtra("icon",1);
                    break;
                case R.id.navigation_feed:
                   ImageView imageView= navigation_feed.findViewById(R.id.iv_navigation_feed);
                    TextView textView= navigation_feed.findViewById(R.id.tv_navigation_feed);
                    imageView.setImageResource(R.mipmap.feed_active);
                    textView.setTextColor(getResources().getColor(R.color.pink_drak));
                    return;
                case R.id.navigation_winners:
                    intent.putExtra("icon",3);
                    break;
                case R.id.navigation_radar:
                    intent.putExtra("icon",4);
                    break;
            }
            if(!userSession.isLogin())
            {
                Intent loginIntent=new Intent(FeedsActivity.this,LoginActivity.class);
                  startActivityForResult(loginIntent,150);
            }
            else
            {
                Log.e("user id",""+userSession.getUserId());
                startActivity(intent);
                finish();
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);
        ButterKnife.bind(this);
        container = findViewById(R.id.player_container);
        bottom_view=findViewById(R.id.bottom_view);
        userSession=new UserSession(this);
        detector=new ConnectionDetector(this);
        feedsPresenter=new FeedsPresenter();
        feedsPresenter.attachView(this);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        SnapHelper snapHelper = new PagerSnapHelper();
        container.setLayoutManager(layoutManager);
        snapHelper.attachToRecyclerView(container);
        adapter = new FullVideoAdapter(this,modelArrayList,this);
        container.setAdapter(adapter);
        if(detector.isConnectingToInternet())
            feedsPresenter.loadFeeds();
        else {
            showMessage("Check internet connection");
        }
//        setDataToContainer();
        navigation_home.setOnClickListener(bottomNavigationOnClickListener);
        navigation_challengers.setOnClickListener(bottomNavigationOnClickListener);
        navigation_feed.setOnClickListener(bottomNavigationOnClickListener);
        navigation_winners.setOnClickListener(bottomNavigationOnClickListener);
        navigation_radar.setOnClickListener(bottomNavigationOnClickListener);
        bottomNavigationOnClickListener.onClick(navigation_feed);
    }
/*    private void setDataToContainer()
    {
        for (String s:urls) {
            VideoModel videoModel = new VideoModel();
            videoModel.setVideo(s);
            modelArrayList.add(videoModel);
            adapter.notifyDataSetChanged();
        }
    }*/

    @Override
    public void onClick(View v,Feed feed) {
        this.feed=feed;
        Intent intent=new Intent(this, LoginActivity.class);

        switch (v.getId()){
            case R.id.comment_view:
                if(!userSession.isLogin())
                    startActivityForResult(intent,100);
                else {
                       onActivityResult(100,RESULT_OK,null);
                      }

                break;
            case R.id.donation_view:
                if(!userSession.isLogin())
                    startActivityForResult(intent,101);
                else {
                    onActivityResult(101,RESULT_OK,null);
                }
                break;
            case R.id.likes_view:
                if(!userSession.isLogin())
                    startActivityForResult(intent,102);
                else {


                  /*  ImageView imageView=v.findViewById(R.id.iv_like);
                    if(feed.isLike())
                        imageView.setImageResource(R.drawable.like_active);
                    else
                        imageView.setImageResource(R.drawable.like_normal);*/
                    feedsPresenter.likeFeed(userSession.getUserId(),feed.getId());


//                   adapter.notifyDataSetChanged();
//                     onActivityResult(102,RESULT_OK,null);
                }
                break;
            case R.id.share_view:
                String url=SPONSOR_FEEDS_VIDEO_URL+feed.getType()+"/"+feed.getChallengeId()+"/"+feed.getUrl();

                     onDownloadImage(url,null);
                      break;
            case R.id.plus_mark:
                if(!userSession.isLogin())
                    startActivityForResult(intent,103);
                else {
                    feedsPresenter.followUser(userSession.getUserId(),feed.getUserId());
                }
                break;
                case R.id.profile_lay:
                    Intent userProfileIntent=new Intent(FeedsActivity.this,UserProfileActivity.class);
                    userProfileIntent.putExtra("userId",feed.getUserId());
                      startActivity(userProfileIntent);
                    break;



        }
    }

    @Override
    public void onView(Feed feed) {
        this.feed=feed;
        feedsPresenter.viewFeed(userSession.getUserId(),feed.getId());
    }

    /***
     * Feed to hold the reference of the selected
     */
   public Feed feed=null;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Intent intent;
                switch (requestCode){
                    case 100:
                        intent=new Intent(this,FeedCommentsActivity.class);
                        intent.putExtra("feedId",feed.getId());
                        startActivity(intent);
                        break;
                    case 101:

                        break;
                    case 102:

                        break;
                    case 103:

                        break;
                    case 150:
                        if(bottomNavigationOnClickListener!=null&&view!=null)
                           bottomNavigationOnClickListener.onClick(view);
                        break;
                }
        }
    }


    @Override
    public void onFeedsLoaded(List<Feed> feeds) {
        modelArrayList.addAll(feeds);
        adapter.notifyDataSetChanged();

    }
    @Override
    public void onFeedLike(int status,String message){
        if(status==1) {
            try {
                int likes = Integer.parseInt(feed.getLikes());
                if(feed.isLike())
                        likes--;
                    else
                        likes++;
                feed.setLike(!feed.isLike());
                feed.setLikes(String.valueOf(likes));
                adapter.notifyDataSetChanged();
            } catch (Exception e) {

            }
        }

    }

    @Override
    public void onFeedViewed(int status, String message) {
        if(status==1) {
            try {
                int views = Integer.parseInt(feed.getViews());
                views++;
                feed.setViews(String.valueOf(views));


            } catch (Exception e) {

            }
        }
    }

    @Override
    public void onFollowed(int status, String message) {
        showMessage(message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        feedsPresenter.detachView();
    }


    /***
     * Share Related Functionality here
     */
    private boolean checkPermission(String permission) {
        if (Build.VERSION.SDK_INT >= 23) {
            int result = ContextCompat.checkSelfPermission(this, permission);
            if (result == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    String TAG="FeedsActivity";
    public void onDownloadImage(String fileName, final String packageName) {

        File file1=new File(fileName);
        final String filename=file1.getName();
        if(!checkPermission(WRITE_EXTERNAL_STORAGE)){
            requestPermission(WRITE_EXTERNAL_STORAGE);
            return;
        }else {
            RetrofitApis.Factory.create(FeedsActivity.this).downloadFileWithDynamicUrlSync(fileName).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Log.d(TAG, "server contacted and has file");
                        new AsyncTask<Void, Integer, String>() {
                            ProgressDialog progressDialog=new ProgressDialog(FeedsActivity.this,R.style.datepicker);
                            @Override
                            protected void onPreExecute() {
                                super.onPreExecute();

                                progressDialog.setMessage("Downloading file. Please wait...");
//                                progressDialog.setIndeterminate(false);
//                                progressDialog.setMax(100);
                                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                progressDialog.setCancelable(false);
                                progressDialog.show();
                            }

                            @Override
                            protected void onPostExecute(String s) {
                                super.onPostExecute(s);
                                showMessage(s);
                                if(s!=null)
                                    shareFile(packageName,s);
                                progressDialog.dismiss();
                            }

                            @Override
                            protected void onProgressUpdate(Integer... values) {
                                super.onProgressUpdate(values);
                                Log.e("progress is",""+values[0]);
//                                progressDialog.setProgress(values[0]);
                            }


                            @Override
                            protected String doInBackground(Void... voids) {
                                ResponseBody body=response.body();
                                try {
                                    // todo change the file location/name according to your needs
                                    String rootDir = Environment.getExternalStorageDirectory()
                                            + File.separator + "ShooShoo";
                                    File rootFile = new File(rootDir);
                                    rootFile.mkdir();

                                    File futureStudioIconFile = new File(rootFile,filename);

                                    InputStream inputStream = null;
                                    OutputStream outputStream = null;

                                    try {
                                        byte[] fileReader = new byte[4096];
//                                        Log.e("total size is",""+fileSize);
                                        //long fileSize = body.contentLength();
                                        long fileSizeDownloaded = 0;

                                        inputStream = body.byteStream();
                                        outputStream = new FileOutputStream(futureStudioIconFile);
                                        int size=0;
//                                        int progress = 0;
                                        while ((size = inputStream.read(fileReader)) != -1) {
                                            outputStream.write(fileReader, 0, size);
//                                            progress += size;
//                                            float percentage=((float) progress / fileSize)*100;
//                                            int percent=(int) percentage;
//                                            publishProgress(percent);
//                                            Log.e("progress is", "Progress: " + progress + "/" + fileSize + " >>>> " + percent);
                                        }
                                        outputStream.flush();
                                        return futureStudioIconFile.getAbsolutePath();
                                    } catch (IOException e) {
                                        return null;
                                    } finally {
                                        if (inputStream != null) {
                                            inputStream.close();
                                        }

                                        if (outputStream != null) {
                                            outputStream.close();
                                        }
                                    }
                                } catch (IOException e) {
                                    return null;
                                }
                            }
                        }.execute();
                    } else {
                        Log.d(TAG, "server contact failed");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });

        }
    }
    String videoShareText="\\nLet me recommend you this application";
    public void shareFile(String pak_name,String fileName){
        File photoFile=new File(fileName);
        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/jpg");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkURI = FileProvider.getUriForFile(getApplicationContext(), getPackageName() + ".provider", photoFile);
            shareIntent.putExtra(Intent.EXTRA_STREAM, apkURI);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }else
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(photoFile));
        String shareMessage ="\n\n"+videoShareText+"\n\uD83D\uDC49 https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        if(pak_name==null)
        {
            startActivity(Intent.createChooser(shareIntent, "Share image using"));
        }
        else if(pak_name.equalsIgnoreCase("com.whatsapp"))
        {
            shareIntent.setPackage("com.whatsapp");
            try {
                startActivity(shareIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                showMessage("Whatsapp have not been installed.");
            }
        }else if(pak_name.equalsIgnoreCase("com.twitter"))
        {
            shareIntent.setPackage("com.twitter.android");
            try {
                startActivity(shareIntent);
            } catch (android.content.ActivityNotFoundException ex) {
//                showMessage("Twitter have not been installed.");
            }

            shareIntent.setPackage("com.twitter.android.lite");
            try {
                startActivity(shareIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                showMessage("Twitter have not been installed.");
            }
        }else if(pak_name.contentEquals("com.facebook")){

            shareIntent.setPackage("com.facebook.katana");
            try {
                startActivity(shareIntent);
            } catch (android.content.ActivityNotFoundException ex) {
//                    showMessage("Facebook have not been installed.");
            }

            shareIntent.setPackage("com.facebook.lite");
            try {
                startActivity(shareIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                showMessage("Facebook have not been installed.");
            }
            shareIntent.setPackage("com.facebook");
            try {
                startActivity(shareIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                showMessage("Facebook have not been installed.");
            }
        }
    }

    private void requestPermission(String permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            Toast.makeText(this, "Get account permission allows us to get your email",
                    Toast.LENGTH_LONG).show();
        }
        ActivityCompat.requestPermissions(this, new String[]{permission}, PERMISSION_REQUEST_CODE);
    }

    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
             if(permissions.length>0&&permissions[0].equalsIgnoreCase(WRITE_EXTERNAL_STORAGE)){
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    {
                        String url=SPONSOR_FEEDS_VIDEO_URL+feed.getType()+"/"+feed.getChallengeId()+"/"+feed.getUrl();
                        onDownloadImage(url,null);
                    }
                    else
                        showMessage("Please Accept the Storage Permission to share");
                }

                break;
        }
    }





}
