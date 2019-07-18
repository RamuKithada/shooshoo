package com.android.shooshoo.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.shooshoo.R;
import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.Company;
import com.android.shooshoo.presenters.SponsorChallengePresenter;
import com.android.shooshoo.utils.ApiUrls;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.FragmentListDialogListener;
import com.android.shooshoo.utils.CustomListFragmentDialog;
import com.android.shooshoo.views.SponsorChallengeView;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SponsorChallengeFormActivity extends BaseActivity implements View.OnClickListener , SponsorChallengeView , FragmentListDialogListener {
    @BindView(R.id.btn_next)
    AppCompatTextView btn_next;


    @BindView(R.id.btn_preview)
    AppCompatTextView btn_preview;

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_help)
    ImageView iv_help;

    @BindViews({R.id.button1,R.id.button2,R.id.button3,R.id.button4})
    List<Button> buttons;

    @BindView(R.id.tv_title)
    TextView title;

    @BindView(R.id.edt_challenge_name)
    AppCompatEditText edt_challenge_name;

    @BindView(R.id.edt_startdate)
    AppCompatEditText edt_startdate;

    @BindView(R.id.edt_start_time)
    AppCompatEditText edt_start_time;

    @BindView(R.id.edt_enddate)
    AppCompatEditText edt_enddate;

    @BindView(R.id.edt_end_time)
    AppCompatEditText edt_end_time;

    @BindView(R.id.edt_challenge_des)
    AppCompatEditText edt_challenge_des;



    @BindView(R.id.edit_video_sizes)
    AppCompatEditText edt_video_sizes;

    @BindView(R.id.banner_card)
    CardView bannerCard;

    @BindView(R.id.video_card)
    CardView videoCard;
    @BindView(R.id.iv_banner)
    CircleImageView iv_banner;

    @BindView(R.id.ch_photo_entites)
    CheckBox ch_photo_entites;

    @BindView(R.id.ch_video_entites)
    CheckBox ch_video_entites;



    ConnectionDetector connectionDetector;
    SponsorChallengePresenter sponsorChallengePresenter;
    String  challengeImageUri=null;
    String challengeVideoUri=null;
    private int sizePos=-1;
    final String[] lables=new String[]{"1 min","50 sec","40 sec","30 sec","20 sec"};
    private int privateSponsor=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor_challenge_form);
        ButterKnife.bind(this);
        btn_next.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        bannerCard.setOnClickListener(this);
        videoCard.setOnClickListener(this);
        edt_start_time.setOnClickListener(this);
        edt_startdate.setOnClickListener(this);
        edt_enddate.setOnClickListener(this);
        edt_end_time.setOnClickListener(this);
        iv_help.setOnClickListener(this);

        connectionDetector=new ConnectionDetector(this);
        sponsorChallengePresenter =new SponsorChallengePresenter();
        sponsorChallengePresenter.attachView(this);
        title.setText("The Challenge");
        setStage(1);
        viewInitilization();
        if(getIntent().hasExtra("privateSponsor")){
            privateSponsor=getIntent().getIntExtra("privateSponsor",0);
        }



    }

    private void viewInitilization() {
//        setFocusChange(edt_challenge_name,R.id.edt_challenge_line);
//        setFocusChange(edt_startdate,R.id.startdate_line);
//        setFocusChange(edt_start_time,R.id.starttime_line);
//        setFocusChange(edt_enddate,R.id.enddate_line);
//        setFocusChange(edt_end_time,R.id.endtime_line);
//        setFocusChange(edt_challenge_des,R.id.edt_challenge_des_line);
        edt_video_sizes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomListFragmentDialog showFragment=new CustomListFragmentDialog();
                Bundle args = new Bundle();
                args.putStringArray("list",lables);
                args.putInt("view",edt_video_sizes.getId());
                showFragment.setArguments(args);
                showFragment.show(getSupportFragmentManager(),"size");
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_next:
                if(validate()) {
                    if (connectionDetector.isConnectingToInternet()) {
                        String videos ="no";
                        String photos = "no";
                        if(ch_photo_entites.isChecked())
                            photos="yes";

                        if(ch_video_entites.isChecked())
                            videos="yes";

                        String sponsorIds=userSession.getSponsorIds();

                        if(sponsorIds!=null&&sponsorIds.trim().length()==0){
                            showMessage("Please save a company");
                            return;
                        }
                        sponsorChallengePresenter.createChallenge(userSession.getUserId(),sponsorIds,privateSponsor,edt_challenge_name.getText().toString(),
                                edt_startdate.getText().toString(),edt_start_time.getText().toString(),edt_enddate.getText().toString()
                                , edt_end_time.getText().toString(), edt_challenge_des.getText().toString(), photos,
                                videos, lables[sizePos], challengeImageUri, challengeVideoUri);

                    } else showMessage("Please Check Internet connection");
                }
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_help:
            showMessage("Help");
                break;
            case R.id.edt_start_time:
            case R.id.edt_end_time:
                setTime((AppCompatEditText) view);
                break;
            case R.id.edt_startdate:
            case R.id.edt_enddate:
                setDate((AppCompatEditText) view);
                break;
            case R.id.banner_card:
                if (checkPermission(WRITE_EXTERNAL_STORAGE))
                    getGalleryImages();
                else requestPermission(WRITE_EXTERNAL_STORAGE);
                break;
            case R.id.video_card:
                if (checkPermission(WRITE_EXTERNAL_STORAGE))
                    getGalleryVideos();
                else requestPermission(WRITE_EXTERNAL_STORAGE);
                break;

        }
    }



    private boolean validate() {
        if(!ApiUrls.validateString(edt_challenge_name.getText().toString())){
            edt_challenge_name.setError("Please enter Challenge Name");
            edt_challenge_name.requestFocus();
            return false;
        }
        if(!ApiUrls.validateString(edt_startdate.getText().toString())){
            edt_startdate.setError("Select  Date");
            edt_startdate.requestFocus();
            return false;
        }
        if(!ApiUrls.validateString(edt_start_time.getText().toString())){
            edt_start_time.setError("Select Time");
            edt_start_time.requestFocus();
            return false;
        }
        if(!ApiUrls.validateString(edt_enddate.getText().toString())){
            edt_enddate.setError("Select Date");
            edt_enddate.requestFocus();
            return false;
        }
        if(!ApiUrls.validateString(edt_end_time.getText().toString())){
            edt_end_time.setError("Select Time");
            edt_end_time.requestFocus();
            return false;
        }


        try
        {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String dateStr=edt_startdate.getText().toString()+" "+edt_start_time.getText().toString();
            String dateEnd=edt_enddate.getText().toString()+" "+edt_end_time.getText().toString();
            Date date1 = format.parse(dateStr);
            Date date2 = format.parse(dateEnd);

            if(date2.before(date1)){
                showMessage("Please select End time  after the Start time");
                return false;
            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



        if(!ApiUrls.validateString(edt_challenge_des.getText().toString())){
            edt_challenge_des.setError("Provide Description");
            edt_challenge_des.requestFocus();
            return false;
        }


        if(sizePos<0){
            edt_video_sizes.requestFocus();
            edt_video_sizes.setError("Please set Limit?");
            return false;
        }


        if(!ch_video_entites.isChecked()&&!ch_photo_entites.isChecked()){
            showMessage("Select atleast one type from videos Entries and Photo Entries");
            return false;
        }


        if(challengeImageUri==null)
        {
            bannerCard.requestFocus();
            showMessage("Please upload Banner image");
            return false;

        }

        if(challengeVideoUri==null)
        {
            videoCard.requestFocus();
            showMessage("Please upload Video");
            return false;

        }

        return true;
    }

   /* String active="#FFFFFF",inactive="#CCCCCC";
    void setFocusChange(AppCompatEditText editText, int id){
        final View view=findViewById(id);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    view.setBackgroundColor(Color.parseColor(active));
                else
                    view.setBackgroundColor(Color.parseColor(inactive));

            }
        });

    }*/
    ///////////image Picker tool//////////
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
    public final static int RESULT_LOAD_IMAGE = 100,RESULT_LOAD_VIDEO = 200,PERMISSION_REQUEST_CODE=2;
    private void getGalleryImages() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    private void getGalleryVideos() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
//        intent.setType("video/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select a Video"), RESULT_LOAD_VIDEO);

    }
    private void requestPermission(String permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            Toast.makeText(this, "Get account permission allows us to get your email",
                    Toast.LENGTH_LONG).show();
        }
        ActivityCompat.requestPermissions(this, new String[]{permission}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if(permissions[0].equalsIgnoreCase(WRITE_EXTERNAL_STORAGE)){
                    if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                        getGalleryImages();
                    else
                        showMessage("Please Accept the Storage Permission to Load Photo");
                }

                break;
        }
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

    private void setVideo(Uri videoUri) {
        try {
            challengeVideoUri=ApiUrls.getFilePath(this,videoUri);
            Log.e("Video path",""+challengeVideoUri);
            if(challengeVideoUri!=null) {
                Bitmap bMap = ThumbnailUtils.createVideoThumbnail(challengeVideoUri, MediaStore.Video.Thumbnails.MICRO_KIND);
                if(bMap!=null){
                    CircleImageView iv_video = findViewById(R.id.iv_video);
                    iv_video.setImageBitmap(bMap);
                }


            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    private void setImage(Uri picturePath) {
        try {
            challengeImageUri=picturePath.getPath();
            Log.e("Image path",picturePath.getPath());
            Picasso.with(this).load(picturePath).into(iv_banner);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void setStage(int i) {
        for(int index=0;index<buttons.size();index++){
            if(index<=i){
                {
                    buttons.get(index).setBackgroundResource(R.drawable.selected);
                }
            }else buttons.get(index).setBackgroundResource(R.drawable.unselected);

        }
    }

    int startDate=0,startYear=0,startMonth=0;
    DatePickerDialog datePickerDialog;
    private void setDate(final AppCompatEditText edt_dob) {

        Calendar c = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, R.style.datepicker, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                startYear=year;
                startDate=dayOfMonth;
                startMonth=month;
                edt_dob.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                edt_dob.clearFocus();
                edt_dob.setError(null);

            }
        },
                c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));
        if(edt_dob.getId()==R.id.edt_enddate)
        {
            if(startDate>0&&startMonth>0&&startYear>0) {
                c.set(startYear, startMonth, startDate);
                edt_startdate.getText();
                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            }else {
                showMessage("Please select first Starting date and time");
                return;
            }
        }
        datePickerDialog.show();
    }

    TimePickerDialog timePickerDialog;
    private void setTime(final AppCompatEditText time) {

        Calendar c = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(this, R.style.datepicker, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time.clearFocus();
                time.setError(null);
                time.setText(hourOfDay+"-"+minute);
                try {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                    Date date = calendar.getTime();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
                    simpleDateFormat.applyPattern("HH:mm:ss");
                    time.setText(simpleDateFormat.format(date));
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        },
                c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),
                true);
        timePickerDialog.show();
    }

    @Override
    public void onCompanyRegister(Company company) {

    }

    @Override
    public void onChallengeResponse(Challenge company) {
        userSession.setSponsorChallenge(company.getChallengeId());
        userSession.savaChallenge(company);
        userSession.setSponsorIds(null);
        Intent intent=new Intent(this, SponsorAudienceActivity.class);
        startActivity(intent);
//        intent.putExtra("challenge_type",1);
//        startActivity(intent);

    }

    @Override
    public void onEditView(int view, int pos) {
               sizePos=pos;
               edt_video_sizes.setText(lables[pos]);
    }

}
