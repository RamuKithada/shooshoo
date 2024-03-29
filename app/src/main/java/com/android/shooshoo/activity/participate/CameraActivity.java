package com.android.shooshoo.activity.participate;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.opengl.GLException;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.shooshoo.R;
import com.android.shooshoo.activity.BaseActivity;
import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.utils.ApiUrls;
import com.android.shooshoo.widget.SampleGLView;
import com.daasuu.camerarecorder.CameraRecordListener;
import com.daasuu.camerarecorder.CameraRecorder;
import com.daasuu.camerarecorder.CameraRecorderBuilder;
import com.daasuu.camerarecorder.LensFacing;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.IntBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.opengles.GL10;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.android.shooshoo.activity.jackpot.JackpotChallengeFormActivity.RESULT_LOAD_IMAGE;
import static com.android.shooshoo.activity.jackpot.JackpotChallengeFormActivity.RESULT_LOAD_VIDEO;

public class CameraActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.layout_camera)
    RelativeLayout layout_camera;
    @BindView(R.id.layout_frontback)
    RelativeLayout layout_frontback;
    @BindView(R.id.layout_video)
    RelativeLayout layout_video;
    @BindView(R.id.layout_flash)
    RelativeLayout layout_flash;
    @BindView(R.id.layout_gallery)
    RelativeLayout layout_gallery;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.camera_icon)
    CircleImageView camera_icon;
    @BindView(R.id.image_view)
    ImageView image_view;

    @BindView(R.id.ok_btn)
    CircleImageView ok_btn;

    @BindView(R.id.cancel_btn)
    CircleImageView ok_cancel;

    @BindView(R.id.result_layout)
    RelativeLayout result_layout;

    private SampleGLView sampleGLView;
    protected CameraRecorder cameraRecorder;
    private String filepath;
    protected LensFacing lensFacing = LensFacing.BACK;
    protected int cameraWidth = 1280;
    protected int cameraHeight = 720;
    protected int videoWidth = 720;
    protected int videoHeight = 720;
    private boolean toggleClick = false;
    private boolean isRecordingvideo=false;
    private boolean isImageChallenge=false;
    private boolean wantToSave=false;


Challenge mChallenge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);
        init();
       mChallenge=getIntent().getParcelableExtra("challenge");
       isImageChallenge=getIntent().getBooleanExtra("image",false);

    }

    private void init()
    {
        videoWidth = 720;
        videoHeight = 1280;
        cameraWidth = 1280;
        cameraHeight = 720;

        layout_camera.setOnClickListener(this);
        layout_frontback.setOnClickListener(this);
        layout_video.setOnClickListener(this);
        layout_flash.setOnClickListener(this);
        layout_gallery.setOnClickListener(this);
        ok_btn.setOnClickListener(this);
        ok_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.layout_camera:
                tackPicture();

                break;
            case R.id.layout_frontback:
                releaseCamera();
                if (lensFacing == LensFacing.BACK) {
                    lensFacing = LensFacing.FRONT;
                } else {
                    lensFacing = LensFacing.BACK;
                }
                toggleClick = true;
                break;
            case R.id.layout_video:
                if(!isImageChallenge){
                if (!isRecordingvideo) {
                    filepath = getVideoFilePath();
                    cameraRecorder.start(filepath);
                    isRecordingvideo=true;
                    camera_icon.setImageResource(R.drawable.ic_stop_black_24dp);
                    if(mChallenge!=null)
                    {
                      String length=mChallenge.getMaxLength();
                      if(length!=null)
                      if(length.equalsIgnoreCase("1"))
                          startTimer(60000);
                      else if(length.equalsIgnoreCase("50"))
                          startTimer(50000);
                      else if(length.equalsIgnoreCase("40"))
                          startTimer(40000);
                      else if(length.equalsIgnoreCase("30"))
                          startTimer(30000);
                      else if(length.equalsIgnoreCase("20"))
                          startTimer(20000);
                      else startTimer(10000);

                    }
                } else {
                    cameraRecorder.stop();
                    if(isRecordingvideo)
                    {
                        mCountDownTimer.cancel();
                        mProgressBar.setProgress(0);
                    }

                    isRecordingvideo=false;
                    camera_icon.setImageResource(R.drawable.ic_camera_black_24dp);

                }}else {
                    tackPicture();
                }
                break;
            case R.id.layout_flash:
                if (cameraRecorder != null && cameraRecorder.isFlashSupport()) {
                    cameraRecorder.switchFlashMode();
                    cameraRecorder.changeAutoFocus();
                }
                break;
            case R.id.layout_gallery:
                releaseCamera();
              if(isImageChallenge)
                  getGalleryImages();
              else
                  getGalleryVideos();
                break;
            case R.id.ok_btn:
                Intent intent = new Intent(this, PostVideoActivity.class);
                intent.putExtra("post", getIntent().getStringExtra("post"));
                if(isImageChallenge)
                intent.putExtra("mpost", imageUri);
                else
                    intent.putExtra("mpost", videoFileUri);
                    intent.putExtra("thumb",thumb);
                intent.putExtra("challenge",getIntent().getParcelableExtra("challenge"));
                intent.putExtra("type",isImageChallenge);
                startActivity(intent);
//                finish();

                break;
            case R.id.cancel_btn:
                mProgressBar.setProgress(0);
                result_layout.setVisibility(View.GONE);
                image_view.setImageBitmap(null);
                break;

        }
    }

    private void tackPicture() {
        captureBitmap(new BitmapReadyCallbacks() {
            @Override
            public void onBitmapReady(final Bitmap bitmap) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        String imagePath = getImageFilePath();
                        saveAsPngImage(bitmap, imagePath);
                        exportPngToGallery(getApplicationContext(), imagePath);

                    }
                });
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();

    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseCamera();

    }

    private void releaseCamera() {
        if (sampleGLView != null) {
            sampleGLView.onPause();
        }

        if (cameraRecorder != null) {
            cameraRecorder.stop();
            cameraRecorder.release();
            cameraRecorder = null;
        }

        if (sampleGLView != null) {
            ((FrameLayout) findViewById(R.id.wrap_view)).removeView(sampleGLView);
            sampleGLView = null;
        }
    }

    private void setUpCameraView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FrameLayout frameLayout = findViewById(R.id.wrap_view);
                frameLayout.removeAllViews();
                sampleGLView = null;
                sampleGLView = new SampleGLView(getApplicationContext());
                sampleGLView.setTouchListener(new SampleGLView.TouchListener() {
                    @Override
                    public void onTouch(MotionEvent event, int width, int height) {
                        if (cameraRecorder == null) return;
                        cameraRecorder.changeManualFocusPoint(event.getX(), event.getY(), width, height);
                    }
                });
                frameLayout.addView(sampleGLView);
            }
        });
    }


    private void setUpCamera() {
        setUpCameraView();

        cameraRecorder = new CameraRecorderBuilder(this, sampleGLView)
                //.recordNoFilter(true)
                .cameraRecordListener(new CameraRecordListener() {
                    @Override
                    public void onGetFlashSupport(final boolean flashSupport) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            }
                        });
                    }

                    @Override
                    public void onRecordComplete() {
                        if(wantToSave)
                             exportMp4ToGallery(getApplicationContext(), filepath);

                        Log.e("onRecordComplete",""+filepath);
                    }

                    @Override
                    public void onRecordStart() {

                    }

                    @Override
                    public void onError(Exception exception) {
                        Log.e("CameraRecorder", exception.toString());
                    }

                    @Override
                    public void onCameraThreadFinish() {
                        if (toggleClick) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setUpCamera();
                                }
                            });
                        }
                        toggleClick = false;
                    }
                })
                .videoSize(videoWidth, videoHeight)
                .cameraSize(cameraWidth, cameraHeight)
                .lensFacing(lensFacing)
                .build();
    }

    private interface BitmapReadyCallbacks {
        void onBitmapReady(Bitmap bitmap);
    }

    private void captureBitmap(final BitmapReadyCallbacks bitmapReadyCallbacks) {

        sampleGLView.queueEvent(new Runnable() {
            @Override
            public void run() {
                EGL10 egl = (EGL10) EGLContext.getEGL();
                GL10 gl = (GL10) egl.eglGetCurrentContext().getGL();
                final Bitmap snapshotBitmap = createBitmapFromGLSurface(sampleGLView.getMeasuredWidth(), sampleGLView.getMeasuredHeight(), gl);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bitmapReadyCallbacks.onBitmapReady(snapshotBitmap);
                    }
                });
            }
        });
    }

    private Bitmap createBitmapFromGLSurface(int w, int h, GL10 gl) {

        int bitmapBuffer[] = new int[w * h];
        int bitmapSource[] = new int[w * h];
        IntBuffer intBuffer = IntBuffer.wrap(bitmapBuffer);
        intBuffer.position(0);

        try {
            gl.glReadPixels(0, 0, w, h, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, intBuffer);
            int offset1, offset2, texturePixel, blue, red, pixel;
            for (int i = 0; i < h; i++) {
                offset1 = i * w;
                offset2 = (h - i - 1) * w;
                for (int j = 0; j < w; j++) {
                    texturePixel = bitmapBuffer[offset1 + j];
                    blue = (texturePixel >> 16) & 0xff;
                    red = (texturePixel << 16) & 0x00ff0000;
                    pixel = (texturePixel & 0xff00ff00) | red | blue;
                    bitmapSource[offset2 + j] = pixel;
                }
            }
        } catch (GLException e) {
            Log.e("CreateBitmap", "createBitmapFromGLSurface: " + e.getMessage(), e);
            return null;
        }

        return Bitmap.createBitmap(bitmapSource, w, h, Bitmap.Config.ARGB_8888);
    }

    public void saveAsPngImage(Bitmap bitmap, String filePath) {
        try {
            File file = new File(filePath);
            FileOutputStream outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public  void exportMp4ToGallery(Context context,  String filePath) {
        wantToSave=false;
        final ContentValues values = new ContentValues(2);
        values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
        values.put(MediaStore.Video.Media.DATA, filePath);
        context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                values);
        final Uri contentUri= Uri.parse("file://" + filePath);
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
               contentUri));

        showProgressIndicator(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showProgressIndicator(false);
                setVideo(contentUri);

            }
        },2000);


    }

    public static String getVideoFilePath() {
        return getAndroidMoviesFolder().getAbsolutePath() + "/" + new SimpleDateFormat("yyyyMM_dd-HHmmss").format(new Date()) + "shooshoo.mp4";
    }

    public static File getAndroidMoviesFolder() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
    }

    private  void exportPngToGallery(Context context, String filePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(filePath);
        final Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setImage(contentUri);
                showProgressIndicator(false);
            }
        },2000);
    }

    public static String getImageFilePath() {
        return getAndroidImageFolder().getAbsolutePath() + "/" + new SimpleDateFormat("yyyyMM_dd-HHmmss").format(new Date()) + "shooshoo.png";
    }

    public static File getAndroidImageFolder() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    }

    private void getGalleryImages() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    private void getGalleryVideos() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Select a Video"), RESULT_LOAD_VIDEO);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                setImage(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

        if (requestCode == RESULT_LOAD_VIDEO && resultCode == RESULT_OK && null != data) {
            Uri videoUri = data.getData();
               setVideo(videoUri);


            /*CropImage.activity(imageUri).setAspectRatio(4,3)
                    .start(this);*/
        }
    }

    private void setImage(Uri resultUri) {
        this.imageUri=resultUri;
        image_view.setImageURI(resultUri);
        image_view.setVisibility(View.VISIBLE);
        result_layout.setVisibility(View.VISIBLE);
    }
    Uri videoFileUri,imageUri;
    Bitmap thumb=null;
    private void setVideo(Uri videoUri) {
        try {
           String challengeVideoUri= ApiUrls.getFilePath(this,videoUri);
            videoFileUri=videoUri;
            Log.e("Video path",""+challengeVideoUri);
            if(challengeVideoUri!=null) {
                Bitmap bMap = ThumbnailUtils.createVideoThumbnail(challengeVideoUri, MediaStore.Video.Thumbnails.MICRO_KIND);
                if(bMap!=null){
                    {
                        image_view.setImageBitmap(bMap);
                        thumb=bMap;
                    }
                }else
                    setVideo(videoUri);
//                    image_view.setImageResource(R.mipmap.ic_launcher_round);
                image_view.setVisibility(View.VISIBLE);
                result_layout.setVisibility(View.VISIBLE);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        if(image_view.getVisibility()==View.VISIBLE)
        {
            image_view.setVisibility(View.GONE);
            image_view.setImageBitmap(null);
            result_layout.setVisibility(View.GONE);
            return;
        }
        if(isRecordingvideo){
            cameraRecorder.stop();
            isRecordingvideo=false;
            camera_icon.setImageResource(R.drawable.ic_camera_black_24dp);

            if(wantToSave){
                mProgressBar.setProgress(0);
                if(mCountDownTimer!=null)
                    mCountDownTimer.cancel();
                return;
            }
        }

        super.onBackPressed();
    }
    // TODO Here doing timer task to stop video recording
    CountDownTimer mCountDownTimer;
    int i=0;
public  void startTimer(final long duration){
    Log.e("Log_tag", "Tick of Progress"+ i+ duration);
    i=0;
    final int interval=1000;
    wantToSave=true;
    mProgressBar.setProgress(i);
    mCountDownTimer=new CountDownTimer(duration,interval) {
        @Override
        public void onTick(long millisUntilFinished) {
            i++;
            long  completedTime=i*interval;
            int percentage=(int)(completedTime*100/duration);
            Log.v("Log_tag", "percentage"+percentage);
            mProgressBar.setProgress(percentage);
        }

        @Override
        public void onFinish() {
            //Do what you want
            i++;
            mProgressBar.setProgress(100);
            if(isRecordingvideo){
                cameraRecorder.stop();
                isRecordingvideo=false;
                camera_icon.setImageResource(R.drawable.ic_camera_black_24dp);
            }
        }
    };
    mCountDownTimer.start();
}













}
