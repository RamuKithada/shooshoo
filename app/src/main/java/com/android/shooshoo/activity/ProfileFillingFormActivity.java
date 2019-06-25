package com.android.shooshoo.activity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.shooshoo.BuildConfig;
import com.android.shooshoo.R;
import com.android.shooshoo.models.Category;
import com.android.shooshoo.presenters.DataLoadPresenter;
import com.android.shooshoo.presenters.UpdateUserInfoPresenter;
import com.android.shooshoo.utils.ApiUrls;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.FragmentListDialogListener;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.models.City;
import com.android.shooshoo.models.Country;
import com.android.shooshoo.models.LoginSuccess;
import com.android.shooshoo.utils.CustomListFragmentDialog;
import com.android.shooshoo.views.DataLoadView;
import com.android.shooshoo.views.UpdateUserInfoView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**{@link ProfileFillingFormActivity} is used to show the
 *
 *
 */
public class ProfileFillingFormActivity extends BaseActivity implements UpdateUserInfoView,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener, View.OnClickListener, DataLoadView, FragmentListDialogListener {


    private static final String TAG = ProfileFillingFormActivity.class.getSimpleName();

    @BindView(R.id.button1)
    Button button1;

    @BindView(R.id.button2)
    Button button2;

    @BindView(R.id.button3)
    Button button3;

    @BindView(R.id.button4)
    Button button4;

    @BindView(R.id.edt_first_name)
    EditText edt_first_name;

    @BindView(R.id.edt_last_name)
    EditText edt_last_name;

    @BindView(R.id.edt_dob)
    EditText edt_dob;


    @BindView(R.id.edt_zipcode)
    EditText edt_zipcode;

    @BindView(R.id.edt_Street)
    EditText edt_Street;

    @BindView(R.id.edt_number)
    EditText edt_number;


    @BindView(R.id.edt_mobile)
    EditText edt_mobile;


    @BindView(R.id.iv_dropdown_city)
    ImageView iv_dropdown_city;

    @BindView(R.id.iv_dropdown_county)
    ImageView iv_dropdown_county;

    @BindView(R.id.iv_dropdown_gender)
    ImageView iv_dropdown_gender;
    @BindView(R.id.next_lay)
    RelativeLayout next_lay;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.iv_user_fname)
    ImageView iv_user_fname;

    @BindView(R.id.iv_user_lname)
    ImageView iv_user_lname;


    @BindView(R.id.iv_dob)
    ImageView iv_dob;

    @BindView(R.id.iv_country)
    ImageView iv_country;

    @BindView(R.id.iv_city)
    ImageView iv_city;

    @BindView(R.id.iv_zip_code)
    ImageView iv_zip_code;

    @BindView(R.id.iv_street_name)
    ImageView iv_street_name;

    @BindView(R.id.iv_street_no)
    ImageView iv_street_no;

    @BindView(R.id.iv_mobile)
    ImageView iv_mobile;

    @BindView(R.id.iv_gender)
    ImageView iv_gender;

    @BindView(R.id.tv_skip)
    TextView tv_skip;

    @BindView(R.id.iv_back)
    ImageView iv_back;

    @BindView(R.id.ll_upload_photo_layout)
    LinearLayout ll_upload_photo_layout;

    @BindView(R.id.iv_profile_pic)
    CircleImageView iv_profile_pic;

    @BindView(R.id.country_lay)
    RelativeLayout country_lay;

    ConnectionDetector connectionDetector;

    @BindView(R.id.edt_country)
    EditText edt_country;

    @BindView(R.id.edt_city)
    EditText edt_city;

    @BindView(R.id.edt_gender)
    EditText edt_gender;
    @BindView(R.id.edt_country_code)
    EditText edt_country_code;


    View.OnClickListener dropdownOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.country_lay:

                    break;
            }

        }
    };

    DataLoadPresenter dataLoadPresenter;
    UpdateUserInfoPresenter updateUserInfoPresenter;
    final String genders[] = new String[]{"Male", "Female", "Others"};


    String lat = null;
    String lng = null;

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    // FIXME: 5/16/17
    private static final long UPDATE_INTERVAL = 10 * 1000;

    /**
     * The fastest rate for active location updates. Updates will never be more frequent
     * than this value, but they may be less frequent.
     */
    // FIXME: 5/14/17
    private static final long FASTEST_UPDATE_INTERVAL = UPDATE_INTERVAL / 2;

    /**
     * The max time before batched results are delivered by location services. Results may be
     * delivered sooner than this interval.
     */
    private static final long MAX_WAIT_TIME = UPDATE_INTERVAL * 3;

    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    private LocationRequest mLocationRequest;

    /**
     * The entry point to Google Play Services.
     */
    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_filling_form);
        ButterKnife.bind(this);
        connectionDetector = new ConnectionDetector(this);
        dataLoadPresenter = new DataLoadPresenter();
        dataLoadPresenter.attachView(this);
        updateUserInfoPresenter=new UpdateUserInfoPresenter();
        updateUserInfoPresenter.attachView(this);
        tv_title.setText("Personal Info");
        setState();
        next_lay.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        ll_upload_photo_layout.setOnClickListener(this);
        setFocusChange(edt_first_name, R.id.firstname_line, iv_user_fname, new int[]{R.drawable.lastname_active, R.drawable.lastname_normal});
        setFocusChange(edt_last_name, R.id.lastname_line, iv_user_lname, new int[]{R.drawable.lastname_active, R.drawable.lastname_normal});
        setFocusChange(edt_dob, R.id.dob_line, iv_dob, new int[]{R.drawable.date_birth_active, R.drawable.date_birth_normal});
        setFocusChange(edt_city, R.id.city_line, iv_city, new int[]{R.drawable.city_active, R.drawable.city_normal});
        setFocusChange(edt_country, R.id.country_line, iv_country, new int[]{R.drawable.country_active, R.drawable.country_normal});
        setFocusChange(edt_gender, R.id.gender_line, iv_gender, new int[]{R.drawable.gender_active, R.drawable.gender_normal});
        setFocusChange(edt_zipcode, R.id.zipcode_line, iv_zip_code, new int[]{R.drawable.zipcode_active, R.drawable.zipcode_normal});
        setFocusChange(edt_Street, R.id.street_line, iv_street_name, new int[]{R.drawable.street_active, R.drawable.street_normal});
        setFocusChange(edt_number, R.id.number_line, iv_street_no, new int[]{R.drawable.streetno_active, R.drawable.streetno_normal});
        setFocusChange(edt_mobile, R.id.country_code_line, iv_mobile, new int[]{R.drawable.mobile_active, R.drawable.mobile_normal});
        iv_dropdown_city.setOnClickListener(dropdownOnClickListener);
        country_lay.setOnClickListener(dropdownOnClickListener);
        iv_dropdown_gender.setOnClickListener(dropdownOnClickListener);
        tv_skip.setOnClickListener(this);
        edt_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(edt_dob);
            }
        });
        edt_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomListFragmentDialog tv = new CustomListFragmentDialog();
                Bundle args = new Bundle();
                args.putStringArray("list", genders);
                args.putInt("view", R.id.edt_gender);
                tv.setArguments(args);
                tv.show(getSupportFragmentManager(), "ha");
            }
        });

        if (connectionDetector.isConnectingToInternet())
            loadData();
            buildGoogleApiClient();


    }

    private void loadData() {
        dataLoadPresenter.loadCountryData();
    }

    String active = "#FFFFFF", inactive = "#CCCCCC";

    void setFocusChange(EditText editText, int id, final ImageView imageView, final int[] res) {
        final View view = findViewById(id);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    view.setBackgroundColor(Color.parseColor(active));
                    imageView.setImageResource(res[0]);
                    if (v.getId() == R.id.edt_dob) {
                        edt_dob.setRawInputType(InputType.TYPE_CLASS_TEXT);
                        setDate(edt_dob);
                    }

                } else {
                    view.setBackgroundColor(Color.parseColor(inactive));
                    imageView.setImageResource(res[1]);
                }
            }
        });

    }

    DatePickerDialog datePickerDialog;

    private void setDate(final EditText edt_dob) {

        Calendar c = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, R.style.datepicker, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                edt_dob.setText(year + "-" + (month + 1) + "-" + dayOfMonth);

            }
        },
                c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();
        hideKeyboard(this, edt_dob);

    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_lay:
                if (connectionDetector.isConnectingToInternet()) {
                    if (validateInputs()) {
                        updateProfile();

                    }
                } else showMessage("Please check internet connection");
                break;
            case R.id.tv_skip:
                tv_skip.setOnClickListener(null);
                Intent homeIntent = new Intent(this, HomeActivity.class);
                startActivity(homeIntent);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_upload_photo_layout:
                if (checkPermission(WRITE_EXTERNAL_STORAGE))
                    getGalleryImages();
                else
                    requestPermission(WRITE_EXTERNAL_STORAGE);
                break;
        }
    }

    Country country = new Country();
    String gender = "male";
    City city = new City();

    private void updateProfile() {
        if (newsImage != null)
            updateUserInfoPresenter.updateUserProfile(newsImage, userSession.getUserId(),
                    edt_first_name.getText().toString(), edt_last_name.getText().toString(),
                    edt_dob.getText().toString(), country.getCountryId(),
                    city.getCityId(), edt_zipcode.getText().toString(),
                    edt_Street.getText().toString(), edt_number.getText().toString(),
                    edt_mobile.getText().toString(), gender.toLowerCase(),
                    lat, lng, userSession.getToken());
    }



    private void setState() {
        button1.setBackgroundResource(R.drawable.unselected);
        button2.setBackgroundResource(R.drawable.selected);
        button3.setBackgroundResource(R.drawable.unselected);
        button4.setBackgroundResource(R.drawable.unselected);
    }


    /**
     * Sets up the location request. Android has two location request settings:
     * {@code ACCESS_COARSE_LOCATION} and {@code ACCESS_FINE_LOCATION}. These settings control
     * the accuracy of the current location. This sample uses ACCESS_FINE_LOCATION, as defined in
     * the AndroidManifest.xml.
     * <p/>
     * When the ACCESS_FINE_LOCATION setting is specified, combined with a fast update
     * interval (5 seconds), the Fused Location Provider API returns location updates that are
     * accurate to within a few feet.
     * <p/>
     * These settings are appropriate for mapping applications that show real-time location
     * updates.
     */
    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        mLocationRequest.setInterval(UPDATE_INTERVAL);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // Sets the maximum time when batched location updates are delivered. Updates may be
        // delivered sooner than this interval.
        mLocationRequest.setMaxWaitTime(MAX_WAIT_TIME);
    }

    private void buildGoogleApiClient() {
        if (mGoogleApiClient != null) {
            return;
        }
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .enableAutoManage(this, this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "GoogleApiClient connected");
        mGoogleApiClient.connect();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }


    @Override
    public void onConnectionSuspended(int i) {
        final String text = "Connection suspended";
        Log.w(TAG, text + ": Error code: " + i);
        showSnackbar("Connection suspended");
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,  this);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        final String text = "Exception while connecting to Google Play services";
        Log.w(TAG, text + ": " + connectionResult.getErrorMessage());
        showSnackbar(text);
    }

    /**
     * Shows a {@link Snackbar} using {@code text}.
     *
     * @param text The Snackbar text.
     */
    private void showSnackbar(final String text) {
        View container = findViewById(R.id.activity_main);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }

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
    public final static int RESULT_LOAD_IMAGE = 100,PERMISSION_REQUEST_CODE=2;
    private void getGalleryImages() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }
    private void requestPermission(String permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            Toast.makeText(this, "Get account permission allows us to get your email",
                    Toast.LENGTH_LONG).show();
        }
        ActivityCompat.requestPermissions(this, new String[]{permission}, PERMISSION_REQUEST_CODE);
    }
    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if(permissions[0].equalsIgnoreCase(WRITE_EXTERNAL_STORAGE)){
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                        getGalleryImages();
                    else
                        showMessage("Please Accept the Storage Permission to Load Photo");
                }

                break;
            case REQUEST_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length <= 0) {
                    // If user interaction was interrupted, the permission request is cancelled and you
                    // receive empty arrays.
                    Log.i(TAG, "User interaction was cancelled.");
                } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted. Kick off the process of building and connecting
                    // GoogleApiClient.
                    buildGoogleApiClient();
                } else {
                    // Permission denied.

                    // Notify the user via a SnackBar that they have rejected a core permission for the
                    // app, which makes the Activity useless. In a real app, core permissions would
                    // typically be best requested during a welcome-screen flow.

                    // Additionally, it is important to remember that a permission might have been
                    // rejected without asking the user for permission (device policy or "Never ask
                    // again" prompts). Therefore, a user interface affordance is typically implemented
                    // when permissions are denied. Otherwise, your app could appear unresponsive to
                    // touches or interactions which have required permissions.
                    Snackbar.make(
                            findViewById(R.id.activity_main),
                            R.string.permission_denied_explanation,
                            Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.settings, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // Build intent that displays the App settings screen.
                                    Intent intent = new Intent();
                                    intent.setAction(
                                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package",
                                            BuildConfig.APPLICATION_ID, null);
                                    intent.setData(uri);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            })
                            .show();
                }

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri imageUri = data.getData();
            CropImage.activity(imageUri).setAspectRatio(4,3)
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
    }

    Uri newsImage=null;
    private void setImage(Uri picturePath) {
        newsImage=picturePath;
        Picasso.with(this).load(picturePath).into(iv_profile_pic);
    }
    int country_pos=-1,city_pos=-1,gender_pos=-1;
    private void displayList(final List<Country> dropDownItems, final EditText editText)
    {
        if(dropDownItems==null)
           return;

        final String[] lables=new String[dropDownItems.size()];
        for(int index=0;index<dropDownItems.size();index++)
            {
            lables[index]=dropDownItems.get(index).getCountryName();
        }
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomListFragmentDialog showFragment=new CustomListFragmentDialog();
                Bundle args = new Bundle();
                args.putStringArray("list",lables);
                args.putInt("view",editText.getId());
                showFragment.setArguments(args);
                showFragment.show(getSupportFragmentManager(),"country");

            }
        });



    }
    private void displayListCity(final List<City> dropDownItems, final EditText editText)
    {
        if(dropDownItems==null)
        {
            city_pos=-1;
            editText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMessage("No Cites");
                }
            });
            return;
        }

        final String[] lables=new String[dropDownItems.size()];
        for(int index=0;index<dropDownItems.size();index++)
        {
            lables[index]=dropDownItems.get(index).getCityName();
        }
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomListFragmentDialog showFragment=new CustomListFragmentDialog();
                Bundle args = new Bundle();
                args.putStringArray("list",lables);
                args.putInt("view",editText.getId());
                showFragment.setArguments(args);
                showFragment.show(getSupportFragmentManager(),"city");
            }
        });


    }
   public  boolean validateInputs(){
        if(!ApiUrls.validateString(edt_first_name.getText().toString())){
            edt_first_name.setError("Enter First Name");
            edt_first_name.requestFocus();
            return false;
        }
        if(edt_first_name.getText().toString().length()<2){

            edt_first_name.setError("First Name have at least 2 letters");
            edt_first_name.requestFocus();
            return false;

        }
       if(edt_first_name.getText().toString().length()>70){

           edt_first_name.setError("First Name have maximum 70 letters");
           edt_first_name.requestFocus();
           return false;

       }


       if(!ApiUrls.validateString(edt_last_name.getText().toString())){
           edt_last_name.setError("Enter Last Name");
           edt_last_name.requestFocus();
           return false;
       }


       if(edt_last_name.getText().toString().length()<2){

           edt_last_name.setError("Last Name have at least 2 letters");
           edt_last_name.requestFocus();
           return false;

       }
       if(edt_last_name.getText().toString().length()>70){

           edt_last_name.setError("Last Name have maximum 70 letters");
           edt_last_name.requestFocus();
           return false;

       }
       if(country_pos<0){
           edt_country.setError("Please Select Country");
           edt_country.requestFocus();
           return false;
       }
       if(city_pos<0){
           edt_city.setError("Please Select City");
           edt_city.requestFocus();
           return false;
       }
       if(gender_pos<0){
           edt_gender.setError("Please Select Gender");
           edt_gender.requestFocus();
           return false;
       }


       if(!ApiUrls.validateString(edt_zipcode.getText().toString())){
           edt_zipcode.setError("Enter Zipcode");
           edt_zipcode.requestFocus();
           return false;
       }


       if(edt_zipcode.getText().toString().length()<3){

           edt_zipcode.setError("Zipcode have at least 3 letters");
           edt_zipcode.requestFocus();
           return false;

       }
       if(edt_zipcode.getText().toString().length()>10){

           edt_zipcode.setError("Zipcode have maximum 10 letters");
           edt_zipcode.requestFocus();
           return false;

       }

       if(!ApiUrls.validateString(edt_Street.getText().toString())){
           edt_Street.setError("Enter Street Name ");
           edt_Street.requestFocus();
           return false;
       }


       if(edt_Street.getText().toString().length()<3){

           edt_Street.setError(" Street Name have at least 3 letters");
           edt_Street.requestFocus();
           return false;

       }
       if(edt_Street.getText().toString().length()>255){

           edt_Street.setError(" Street Name have maximum 255 letters");
           edt_Street.requestFocus();
           return false;

       }
       if(ApiUrls.validateString(edt_number.getText().toString())){

           if(edt_number.getText().toString().length()>255){

               edt_number.setError(" Street number have maximum 255 letters");
               edt_number.requestFocus();
               return false;

           }
       }


       if(!ApiUrls.validateString(edt_mobile.getText().toString())){
           edt_mobile.setError("Enter Mobile Number");
           edt_mobile.requestFocus();
           return false;
       }


       if(edt_mobile.getText().toString().length()<4){

           edt_mobile.setError(" Mobile Number have at least 4 digits");
           edt_mobile.requestFocus();
           return false;

       }
       if(!ApiUrls.validateString(edt_dob.getText().toString())){

           edt_dob.setError(" Select Birth date");
           edt_dob.requestFocus();
           return false;

       }
       if(edt_mobile.getText().toString().length()>15){
           edt_mobile.setError("Mobile Number have maximum 15 digits");
           edt_mobile.requestFocus();
           return false;

       }
       if(!Patterns.PHONE.matcher(edt_mobile.getText().toString()).matches()){
           edt_mobile.setError("Enter valid Mobile Number");
           edt_mobile.requestFocus();
           return false;

       }



        return true;

   }
    List<City> cities;
    @Override
    public void onCitiesData(List<City> cities) {
        this.cities=cities;
        edt_city.setText(null);
        city=null;
        displayListCity(cities,edt_city);
    }
    List<Country> countries;
    @Override
    public void onCountryData(List<Country> countries) {
        this.countries=countries;
        displayList(countries,edt_country);
    }

    @Override
    public void onAllCategories(List<Category> categories) {

    }

    @Override
    public void onEditView(int view, int position) {
        switch (view){
            case R.id.edt_gender:
                gender_pos=position;
                gender=genders[position];
                edt_gender.setText(gender);

                break;
            case R.id.edt_country:
                 if(countries!=null)
                 {
                     dataLoadPresenter.loadCites(countries.get(position).getCountryId());
                     edt_country_code.setText("+"+countries.get(position).getPhoneCode());
                     edt_country.setText(countries.get(position).getCountryName());
                     country_pos=position;
                     country=countries.get(position);
                 }
                break;
            case R.id.edt_city:
                if(cities!=null)
                {edt_city.setText(cities.get(position).getCityName());
                    city=cities.get(position);
                city_pos=position;}
                break;
        }

    }




    /**
     * Return the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            Snackbar.make(
                    findViewById(R.id.activity_main),
                    R.string.permission_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(ProfileFillingFormActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    })
                    .show();
        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(ProfileFillingFormActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        // You can now create a LatLng Object for use with maps
        if(location!=null)
        {
            lat=""+location.getLatitude();
            lng=""+location.getLongitude();
        }
    }

    @Override
    public void onUpdateUserInfo(ResponseBody responseBody) {
              if(responseBody!=null){
                  try {
                      String result=responseBody.string();
                      Gson gson=new Gson();
                     LoginSuccess loginSuccess= gson.fromJson(result,LoginSuccess.class);
                     if(loginSuccess.getStatus()==1){
                         Intent intent=new Intent(this,CategoryChooseActivity.class);
                         startActivity(intent);
                     }

                  } catch (IOException e) {
                      e.printStackTrace();
                  }
              }
    }
}
