package com.android.shooshoo.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.shooshoo.R;
import com.android.shooshoo.models.Category;
import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.City;
import com.android.shooshoo.models.Company;
import com.android.shooshoo.models.Country;
import com.android.shooshoo.presenters.DataLoadPresenter;
import com.android.shooshoo.presenters.SponcerChallengePresenter;
import com.android.shooshoo.utils.ApiUrls;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.FragmentListDialogListener;
import com.android.shooshoo.utils.TVShowFragment;
import com.android.shooshoo.views.DataLoadView;
import com.android.shooshoo.views.SponsorChallengeView;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class CompanyProfileActivity extends BaseActivity implements View.OnClickListener, DataLoadView , SponsorChallengeView , FragmentListDialogListener {


    @BindView(R.id.edt_company_name)
    EditText edt_company_name;



    @BindView(R.id.edt_user_email)
    EditText edt_user_email;

    @BindView(R.id.edt_zipcode)
    EditText edt_zipcode;

    @BindView(R.id.edt_Street)
    EditText edt_Street;

    @BindView(R.id.edt_number)
    EditText edt_number;

    @BindView(R.id.edt_mobile)
    EditText edt_mobile;

    @BindView(R.id.edt_country_code)
    EditText edt_country_code;

    @BindView(R.id.edt_tax_number)
    EditText edt_tax_number;

    @BindView(R.id.edt_first_name)
    EditText edt_first_name;

    @BindView(R.id.edt_last_name)
    EditText edt_last_name;

    @BindView(R.id.btn_next)
    TextView btn_next;

    @BindView(R.id.iv_back)
    ImageView iv_back;

    @BindViews({R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5})
    List<Button> buttons;

    @BindView(R.id.tv_title)
    TextView title;

    @BindView(R.id.ll_upload_photo_layout)
    LinearLayout ll_upload_photo_layout;

    @BindView(R.id.iv_profile_pic)
    CircleImageView iv_profile_pic;

    @BindView(R.id.private_sponsor)
    CheckBox private_sponsor;

    @BindView(R.id.btn_more_companies)
    TextView btn_more_companies;

    @BindView(R.id.iv_user_fname)
    ImageView iv_user_fname;

    @BindView(R.id.iv_user_lname)
    ImageView iv_user_lname;


    @BindView(R.id.iv_country)
    ImageView iv_country;

    @BindView(R.id.iv_city)
    ImageView iv_city;

    @BindView(R.id.iv_company_name)
    ImageView iv_company_name;

    @BindView(R.id.iv_zip_code)
    ImageView iv_zip_code;

    @BindView(R.id.iv_street_name)
    ImageView iv_street_name;

    @BindView(R.id.iv_street_no)
    ImageView iv_street_no;

    @BindView(R.id.iv_mobile)
    ImageView iv_mobile;

    @BindView(R.id.iv_email_icon)
    ImageView iv_email_icon;


    @BindView(R.id.iv_tax_no)
    ImageView iv_tax_no;

    @BindView(R.id.edt_country)
    EditText edt_country;

    @BindView(R.id.edt_city)
    EditText edt_city;



    DataLoadPresenter dataLoadPresenter;
    SponcerChallengePresenter sponcerChallengePresenter;
    ConnectionDetector connectionDetector;
    int no_of_companies=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);
        ButterKnife.bind(this);
  dataLoadPresenter=new DataLoadPresenter();
  dataLoadPresenter.attachView(this);
  sponcerChallengePresenter=new SponcerChallengePresenter();
  sponcerChallengePresenter.attachView(this);
  connectionDetector=new ConnectionDetector(this);
  if(connectionDetector.isConnectingToInternet())
      loadData();
        init();
    }

    private void loadData() {
        dataLoadPresenter.loadCountryData();

    }

    private void init() {
        btn_next.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        ll_upload_photo_layout.setOnClickListener(this);
        btn_more_companies.setOnClickListener(this);
        title.setText("Company Profile");
        setStage(0);
        setFoucusChange(edt_company_name,R.id.company_name_line,iv_company_name,new int[]{R.drawable.company_name_active,R.drawable.company_name_normal});
        setFoucusChange(edt_first_name,R.id.firstname_line,iv_user_fname,new int[]{R.drawable.username_active,R.drawable.username_normal});
        setFoucusChange(edt_last_name,R.id.lastname_line,iv_user_lname,new int[]{R.drawable.lastname_active,R.drawable.lastname_normal});
        setFoucusChange(edt_zipcode,R.id.zipcode_line,iv_zip_code,new int[]{R.drawable.zipcode_active,R.drawable.zipcode_normal});
        setFoucusChange(edt_country,R.id.country_line,iv_country,new int[]{R.drawable.country_active,R.drawable.country_normal});
        setFoucusChange(edt_city,R.id.city_line,iv_city,new int[]{R.drawable.city_active,R.drawable.city_normal});
        setFoucusChange(edt_Street,R.id.street_line,iv_street_name,new int[]{R.drawable.street_active,R.drawable.street_normal});
        setFoucusChange(edt_number,R.id.number_line,iv_street_no,new int[]{R.drawable.streetno_active,R.drawable.streetno_normal});
        setFoucusChange(edt_mobile,R.id.country_code_line,iv_mobile,new int[]{R.drawable.mobile_active,R.drawable.mobile_normal});
        setFoucusChange(edt_user_email,R.id.user_mail_line,iv_email_icon,new int[]{R.drawable.email_active,R.drawable.email_normal});
        setFoucusChange(edt_tax_number,R.id.tax_number_line,iv_tax_no,new int[]{R.drawable.tax_active,R.drawable.tax_normal});
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_next:
                if(validate()){
                int sponsorType=0;
                if(private_sponsor.isChecked())
                    sponsorType=1;
            if(connectionDetector.isConnectingToInternet()) {
                this.view=view;
                sponcerChallengePresenter.createCompany(newsImage,userSession.getUserId(), edt_company_name.getText().toString(), edt_user_email.getText().toString(),
                edt_first_name.getText().toString(), edt_last_name.getText().toString(), country.getCountryId(),
                    city.getCityId(), edt_zipcode.getText().toString(), edt_Street.getText().toString(), edt_number.getText().toString(),
                        edt_country_code.getText().toString() + edt_mobile.getText().toString(), edt_tax_number.getText().toString(),
                            String.valueOf(sponsorType));
                            }
                            else
                                showMessage("Please Check Internet connection");
                }

                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_upload_photo_layout:
                if (checkPermission(WRITE_EXTERNAL_STORAGE))
                    getGalleryImages();
                else requestPermission(WRITE_EXTERNAL_STORAGE);
                break;
            case R.id.btn_more_companies:
                if(validate()){
                    int sponsorType=0;
                    if(private_sponsor.isChecked())
                        sponsorType=1;
                    if(connectionDetector.isConnectingToInternet()) {
                        this.view=view;
                        sponcerChallengePresenter.createCompany(newsImage,userSession.getUserId(), edt_company_name.getText().toString(), edt_user_email.getText().toString(),
                                edt_first_name.getText().toString(), edt_last_name.getText().toString(), country.getCountryId(),
                                city.getCityId(), edt_zipcode.getText().toString(), edt_Street.getText().toString(), edt_number.getText().toString(),
                                edt_country_code.getText().toString() + edt_mobile.getText().toString(), edt_tax_number.getText().toString(),
                                String.valueOf(sponsorType));

                    }
                    else
                        showMessage("Please Check Internet connection");
                }

                break;
        }

    }

    private void clearAll() {
        edt_company_name.setText(null);
        edt_last_name.setText(null);
        edt_first_name.setText(null);
        edt_tax_number.setText(null);
        edt_mobile.setText(null);
        edt_country_code.setText(null);
        edt_Street.setText(null);
        edt_zipcode.setText(null);
        edt_number.setText(null);
        edt_user_email.setText(null);
        iv_profile_pic.setImageResource(R.drawable.photo_upload);
        newsImage=null;
        edt_country.setText(null);
        edt_city.setText(null);
        country_pos=-1;
        city_pos=-1;
        city=null;
        country=null;
    }

    private boolean validate() {
        if(newsImage==null){
            showMessage("Please Select logo for your company");
            return false;
        }

        if(!ApiUrls.validateString(edt_company_name.getText().toString()))
        {
            edt_company_name.requestFocus();
            edt_company_name.setError("Please enter Company Name");
        return false;
        }
        if(edt_company_name.getText().toString().length()<2)
        {
            edt_company_name.setError("Company Name is too Short");
            return false;
        }
        if(edt_company_name.getText().toString().length()>70)
        {
            edt_company_name.setError("Company Name is too Long");
            return false;
        }

        if(country_pos<0)
        {
            edt_country.requestFocus();
            edt_country.setError("Please select your Country");
            return false;
        }
        if(city_pos<0)
        {
            edt_city.requestFocus();
            edt_city.setError("Please select your City");
            return false;
        }
        if(!ApiUrls.validateString(edt_zipcode.getText().toString()))
        {
            edt_zipcode.requestFocus();
            edt_zipcode.setError("Please enter Zipcode");
            return false;
        }

        if(edt_zipcode.getText().toString().length()<3)
        {
            edt_zipcode.setError("ZipCode is too Short");
            return false;
        }
        if(edt_zipcode.getText().toString().length()>10)
        {
            edt_zipcode.setError("ZipCode is too Long");
            return false;
        }
        if(!ApiUrls.validateString(edt_Street.getText().toString()))
        {
            edt_Street.requestFocus();
            edt_Street.setError("Please enter Street Name");
            return false;
        }
        if(edt_Street.getText().toString().length()<3)
        {
            edt_Street.setError("Street Name is too Short");
            edt_Street.requestFocus();
            return false;
        }
        if(edt_Street.getText().toString().length()>225)
        {
            edt_Street.setError("Street Name  is too Long");
            edt_Street.requestFocus();
            return false;
        }


        if(!ApiUrls.validateString(edt_mobile.getText().toString()))
        {
            edt_mobile.requestFocus();
            edt_mobile.setError("Please enter Mobile Number");
            return false;
        }
       if (edt_mobile.getText().toString().length()<4)
       {
           edt_mobile.requestFocus();
           edt_mobile.setError("Please enter valid Mobile Number");
           return false;
       }

        if (edt_mobile.getText().toString().length()>15)
        {
            edt_mobile.requestFocus();
            edt_mobile.setError("Please enter valid Mobile Number");
            return false;
        }

        if(!ApiUrls.validateString(edt_tax_number.getText().toString()))
        {
            edt_tax_number.requestFocus();
            edt_tax_number.setError("Please enter Tax Number");
            return false;
        }
        if(edt_tax_number.getText().toString().length()<5)
        {
            edt_tax_number.requestFocus();
            edt_tax_number.setError("Tax Number is Too Short");
            return false;
        }
        if(edt_tax_number.getText().toString().length()>20)
        {
            edt_tax_number.requestFocus();
            edt_tax_number.setError("Tax Number is too Long");
            return false;
        }



        if(!ApiUrls.validateString(edt_first_name.getText().toString()))
        {
            edt_first_name.requestFocus();
            edt_first_name.setError("Please enter First Name");
            return false;
        }

        if(edt_first_name.getText().toString().length()<2)
        {
            edt_first_name.requestFocus();
            edt_first_name.setError("First Name is Too Short");
            return false;
        }
        if(edt_first_name.getText().toString().length()>70)
        {
            edt_first_name.requestFocus();
            edt_first_name.setError("First Name is too Long");
            return false;
        }

        if(!ApiUrls.validateString(edt_last_name.getText().toString()))
        {
            edt_last_name.requestFocus();
            edt_last_name.setError("Please enter Last Name");
            return false;
        }
        if(edt_last_name.getText().toString().length()<2)
        {
            edt_last_name.requestFocus();
            edt_last_name.setError("Last Name is Too Short");
            return false;
        }
        if(edt_last_name.getText().toString().length()>70)
        {
            edt_last_name.requestFocus();
            edt_last_name.setError("Last Name is too Long");
            return false;
        }


        if(!ApiUrls.validateString(edt_user_email.getText().toString()))
        {
            edt_user_email.requestFocus();
            edt_user_email.setError("Please enter email");
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(edt_user_email.getText().toString()).matches()){
            edt_user_email.requestFocus();
            edt_user_email.setError("Please enter valid email");
            return false;
        }

        if(edt_user_email.getText().toString().length()<6)
        {
            edt_user_email.requestFocus();
            edt_user_email.setError("Email is Too Short");
            return false;
        }
        if(edt_user_email.getText().toString().length()>70)
        {
            edt_user_email.requestFocus();
            edt_user_email.setError("Email is too Long");
            return false;
        }

        return true;
    }

    private void setStage(int i) {
        for(int index=0;index<buttons.size();index++){
            if(index==i){
                {
                    buttons.get(index).setBackgroundResource(R.drawable.selected);
                    buttons.get(index).setText(String.valueOf(i+1));
                }
            }else buttons.get(index).setBackgroundResource(R.drawable.unselected);

        }
    }

    String active="#FFFFFF",inactive="#CCCCCC";
    void setFoucusChange(EditText editText, int id, final ImageView imageView, final int[] res){
        final View view=findViewById(id);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    view.setBackgroundColor(Color.parseColor(active));
                    imageView.setImageResource(res[0]);
//                    if(v.getId()==R.id.edt_dob){
//                        edt_dob.setRawInputType(InputType.TYPE_CLASS_TEXT);
//                        setDate(edt_dob);
//                    }

                }
                else
                {
                    view.setBackgroundColor(Color.parseColor(inactive));
                    imageView.setImageResource(res[1]);
                }
            }
        });

    }
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
    public final static int RESULT_LOAD_IMAGE = 100,PERMISSION_REQUEST_CODE=2;
    private void getGalleryImages() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }
    private void requestPermission(String permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            Toast.makeText(this, "Get Storage permission allows us to get images",
                    Toast.LENGTH_LONG).show();
        }
        ActivityCompat.requestPermissions(this, new String[]{permission}, PERMISSION_REQUEST_CODE);
    }

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

    Country country=new Country();

    City city=new City();

    int country_pos=-1,city_pos=-1;
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
                TVShowFragment showFragment=new TVShowFragment();
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
                TVShowFragment showFragment=new TVShowFragment();
                Bundle args = new Bundle();
                args.putStringArray("list",lables);
                args.putInt("view",editText.getId());
                showFragment.setArguments(args);
                showFragment.show(getSupportFragmentManager(),"city");
            }
        });


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
View view=null;
    @Override
    public void onCompanyRegister(Company company) {

        userSession.addSponsor(company.getCompanyId());
        if(view!=null){
            if(view.getId()==R.id.btn_more_companies){
                no_of_companies++;
                clearAll();

                iv_profile_pic.requestFocus();
                if(no_of_companies==2){
                    view.setVisibility(View.GONE);
                }
            }else if(view.getId()==R.id.btn_next){
                Intent intent=new Intent(this,TheChallengeActivity.class);
                intent.putExtra("challenge_type",1);
                startActivity(intent);
            }
            }

        }

    @Override
    public void onChallengeResponse(Challenge challenge) {

    }

    @Override
    public void onEditView(int view, int position) {
        switch (view){
            case R.id.edt_country:
                if(countries!=null)
                {
                    dataLoadPresenter.loadCites(countries.get(position).getCountryId());
                    edt_country_code.setText("+"+countries.get(position).getPhoneCode());
                    edt_country.setText(countries.get(position).getCountryName());
                    edt_city.setError(null);
                    country_pos=position;
                    country=countries.get(position);
                }
                break;
            case R.id.edt_city:
                if(cities!=null)
                {
                    edt_city.setText(cities.get(position).getCityName());
                    edt_city.setError(null);
                    city=cities.get(position);
                    city_pos=position;}
                break;
        }

    }
}
