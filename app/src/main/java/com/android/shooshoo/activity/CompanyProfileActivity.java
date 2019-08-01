package com.android.shooshoo.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.android.shooshoo.models.Language;
import com.android.shooshoo.models.Region;
import com.android.shooshoo.presenters.DataLoadPresenter;
import com.android.shooshoo.presenters.SponsorChallengePresenter;
import com.android.shooshoo.sponsor.SponsorChallengeFormActivity;
import com.android.shooshoo.utils.ApiUrls;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.FragmentListDialogListener;
import com.android.shooshoo.utils.CustomListFragmentDialog;
import com.android.shooshoo.views.DataLoadView;
import com.android.shooshoo.views.SponsorChallengeView;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class CompanyProfileActivity extends BaseActivity implements View.OnClickListener, DataLoadView , SponsorChallengeView , FragmentListDialogListener {

    /**
     *{@link CompanyProfileActivity} is used to create a company profile to sponsor challenge in the process of the   sponsor challenge
     * registration
     *
     *
     */

    @BindView(R.id.edt_company_name)
    AppCompatEditText edt_company_name;



    @BindView(R.id.edt_user_email)
    AppCompatEditText edt_user_email;

    @BindView(R.id.edt_zipcode)
    AppCompatEditText edt_zipcode;

    @BindView(R.id.edt_Street)
    AppCompatEditText edt_Street;

    @BindView(R.id.edt_street_number)
    AppCompatEditText edt_number;

    @BindView(R.id.edt_mobile)
    AppCompatEditText edt_mobile;

    @BindView(R.id.edt_country_code)
    AppCompatEditText edt_country_code;

    @BindView(R.id.edt_tax_number)
    AppCompatEditText edt_tax_number;

    @BindView(R.id.edt_first_name)
    AppCompatEditText edt_first_name;

    @BindView(R.id.edt_last_name)
    AppCompatEditText edt_last_name;

    @BindView(R.id.btn_next)
    AppCompatTextView btn_next;

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
    AppCompatTextView btn_more_companies;

    @BindView(R.id.btn_preview)
    AppCompatTextView btn_preview;

    @BindView(R.id.edt_category)
    AppCompatEditText edt_category;

    @BindView(R.id.edt_country)
    AppCompatEditText edt_country;

    @BindView(R.id.edt_city)
    AppCompatEditText edt_city;

    @BindView(R.id.edt_company_business_email)
    AppCompatEditText edt_company_business_email;
    DataLoadPresenter dataLoadPresenter;
    SponsorChallengePresenter sponsorChallengePresenter;
    ConnectionDetector connectionDetector;
    ArrayList<String> sponsorIds=new ArrayList<>();
    List<Category> categories=null;
    CustomListFragmentDialog showFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_company_profile);
       ButterKnife.bind(this);
  dataLoadPresenter=new DataLoadPresenter();
  dataLoadPresenter.attachView(this);
  sponsorChallengePresenter =new SponsorChallengePresenter();
  sponsorChallengePresenter.attachView(this);
  connectionDetector=new ConnectionDetector(this);
  if(connectionDetector.isConnectingToInternet())
      loadData();
        init();
    }

    private void loadData() {
        dataLoadPresenter.loadCountryData();
        dataLoadPresenter.loadAllcategoriesList();

    }

    private void init() {
        btn_next.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        ll_upload_photo_layout.setOnClickListener(this);
        btn_more_companies.setOnClickListener(this);
        title.setText("COMPANY INFORMATION");
        setStage(0);
        edt_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showFragment!=null)
                    if(showFragment.isVisible())
                    return;

                if(categories==null)
                {
                    showMessage("There no categories to select");
                    return;
                }

                    final String[] lables=new String[categories.size()];
                for(int index=0;index<categories.size();index++)
                {
                    lables[index]=categories.get(index).getCategoryName();
                }

                 showFragment=new CustomListFragmentDialog();
                Bundle args = new Bundle();
                args.putStringArray("list",lables);
                args.putInt("view",edt_category.getId());
                showFragment.setArguments(args);
                showFragment.show(getSupportFragmentManager(),"category");


            }
        });

  }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_next:
                if(validate()){
//                int sponsorType=0;
//                if(private_sponsor.isChecked())
//                    sponsorType=1;
            if(connectionDetector.isConnectingToInternet()) {
                this.view=view;
                sponsorChallengePresenter.createCompany(newsImage,userSession.getUserId(), edt_company_name.getText().toString(), edt_user_email.getText().toString(),
                edt_first_name.getText().toString(), edt_last_name.getText().toString(), country.getCountryId(),
                    city.getCityId(), edt_zipcode.getText().toString(), edt_Street.getText().toString(), edt_number.getText().toString(),
                        edt_country_code.getText().toString() , edt_mobile.getText().toString(), edt_tax_number.getText().toString(),
                            edt_company_business_email.getText().toString(),categories.get(category_pos).getCategoryId());
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
//                    int sponsorType=0;
//                    if(private_sponsor.isChecked())
//                        sponsorType=1;
                    if(connectionDetector.isConnectingToInternet()) {
                        this.view=view;
                        sponsorChallengePresenter.createCompany(newsImage,userSession.getUserId(), edt_company_name.getText().toString(), edt_user_email.getText().toString(),
                                edt_first_name.getText().toString(), edt_last_name.getText().toString(), country.getCountryId(),
                                city.getCityId(), edt_zipcode.getText().toString(), edt_Street.getText().toString(), edt_number.getText().toString(),
                                edt_country_code.getText().toString() , edt_mobile.getText().toString(), edt_tax_number.getText().toString(),
                                 edt_company_business_email.getText().toString(),categories.get(category_pos).getCategoryId());

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
        edt_company_business_email.setText(null);
        iv_profile_pic.setImageResource(R.drawable.photo_upload);
        newsImage=null;
        edt_country.setText(null);
        edt_city.setText(null);
        country_pos=-1;
        city_pos=-1;
        category_pos=-1;
        city=null;
        country=null;
    }

    private boolean validate() {
        if(newsImage==null){
            showMessage("Please upload your company logo");
            return false;
        }

        if(!ApiUrls.validateString(edt_company_name.getText().toString()))
        {
            showMessage("Please enter Company Name");
        return false;
        }
        if(edt_company_name.getText().toString().length()<2)
        {
            showMessage("Company Name contain at least 2 characters and maximum 70 characters");
            return false;
        }
        if(edt_company_name.getText().toString().length()>70)
        {
            showMessage("Company Name contain at least 2 characters and maximum 70 characters");
            return false;
        }

        if(category_pos<0)
        {
            showMessage("Please select your a Category");
            return false;
        }

        if(country_pos<0)
        {

            showMessage("Please select  Country");

            return false;
        }
        if(city_pos<0)
        {
            showMessage("Please select  City");
            return false;
        }
        if(!ApiUrls.validateString(edt_zipcode.getText().toString()))
        {
            showMessage("Please enter Zip Code");
            return false;
        }

        if(edt_zipcode.getText().toString().length()<3)
        {
            showMessage("Zip code have at least 3 characters");
            return false;
        }
        if(edt_zipcode.getText().toString().length()>10)
        {
            showMessage("Zip code have maximum 10 characters");
            return false;
        }
        if(!ApiUrls.validateString(edt_Street.getText().toString()))
        {
            showMessage("Please enter Street");
            return false;
        }
        if(edt_Street.getText().toString().length()<3)
        {
            showMessage(" Street  have at least 3 characters");
            return false;
        }
        if(edt_Street.getText().toString().length()>225)
        {
            showMessage(" Street  have maximum 255 characters");

            return false;
        }


        if(!ApiUrls.validateString(edt_mobile.getText().toString()))
        {
            showMessage("Please enter Mobile Number");
            return false;
        }
       if (edt_mobile.getText().toString().length()<4)
       {
           showMessage("Please enter valid Mobile Number");
           return false;
       }

        if (edt_mobile.getText().toString().length()>15)
        {
            showMessage("Please enter valid Mobile Number");
            return false;
        }

        if(!ApiUrls.validateString(edt_tax_number.getText().toString()))
        {
            showMessage("Please enter Tax Number");
            return false;
        }
        if(edt_tax_number.getText().toString().length()<5)
        {
            showMessage("Tax Number is Too Short");
            return false;
        }
        if(edt_tax_number.getText().toString().length()>20)
        {
            showMessage("Tax Number is too Long");
            return false;
        }



        if(!ApiUrls.validateString(edt_first_name.getText().toString()))
        {
            showMessage("Please enter First Name");
            return false;
        }

        if(edt_first_name.getText().toString().length()<2)
        {
            showMessage("First Name is Too Short");
            return false;
        }
        if(edt_first_name.getText().toString().length()>70)
        {
            showMessage("First Name is too Long");
            return false;
        }

        if(!ApiUrls.validateString(edt_last_name.getText().toString()))
        {
            showMessage("Please enter Last Name");
            return false;
        }
        if(edt_last_name.getText().toString().length()<2)
        {
            showMessage("Last Name is Too Short");
            return false;
        }
        if(edt_last_name.getText().toString().length()>70)
        {
            showMessage("Last Name is too Long");
            return false;
        }


        if(!ApiUrls.validateString(edt_user_email.getText().toString()))
        {
            showMessage("Please enter email");
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(edt_user_email.getText().toString()).matches()){
            showMessage("Please enter valid email");
            return false;
        }

        if(edt_user_email.getText().toString().length()<6)
        {
            showMessage("Email is Too Short");
            return false;
        }
        if(edt_user_email.getText().toString().length()>70)
        {
            showMessage("Email is too Long");
            return false;
        }

        if(!ApiUrls.validateString(edt_company_business_email.getText().toString()))
        {
            showMessage("Please enter company  email");
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(edt_company_business_email.getText().toString()).matches()){
            showMessage("Please enter valid email");
            return false;
        }

        if(edt_company_business_email.getText().toString().length()<6)
        {
            showMessage("Email is Too Short");
            return false;
        }
        if(edt_company_business_email.getText().toString().length()>70)
        {
            showMessage("Email is too Long");
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
    /**
     * active,inactive are the color codes of the highlighting colors of input fields
     */
    String active="#FFFFFF",inactive="#CCCCCC";
    /**
     *
     * @param editText is the input field
     * @param id is the viewid of underline view
     * @param imageView is the image view
     * @param res are the image references of drawables
     */
    void setFoucusChange(AppCompatEditText editText, int id, final ImageView imageView, final int[] res){
        final View view=findViewById(id);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    view.setBackgroundColor(Color.parseColor(active));
                    imageView.setImageResource(res[0]);
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
    /** Checking runtime permission related to this screen
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
    public final static int RESULT_LOAD_IMAGE = 100,PERMISSION_REQUEST_CODE=2;

    /**
     * getGalleryImages is used to call the gallery to select the images to select logo of the company .
     */
    boolean isclicked=false;
    private void getGalleryImages() {
if(isclicked)return;

isclicked=true;
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
//
//        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(i, RESULT_LOAD_IMAGE);
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
        isclicked=false;
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

    int country_pos=-1,city_pos=-1,category_pos=-1;

    /**
     *  country_pos is used to hold the selected country position and city_pos is used to hold the selected country position
     *  country is used to hold selected country object and   city is used to hold selected city object
     * @param dropDownItems are the list of countries to show the user
     * @param editText is the country field to fill after selection of one country
     */
    private void displayList(final List<Country> dropDownItems, final AppCompatEditText editText)
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
                if(showFragment!=null)
                    if(showFragment.isVisible())
                    return;
                 showFragment=new CustomListFragmentDialog();
                Bundle args = new Bundle();
                args.putStringArray("list",lables);
                args.putInt("view",editText.getId());
                showFragment.setArguments(args);

                showFragment.show(getSupportFragmentManager(),"country");

            }
        });



    }/**
     * @param dropDownItems are the list of cites to show the user
     * @param editText is the country field to fill after selection of one city
     *                 if there is no city under contry selection is shows no cities in the country
     */
    private void displayListCity(final List<City> dropDownItems, final AppCompatEditText editText)
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

                if(showFragment!=null)
                    if(showFragment.isVisible())
                    return;
                 showFragment=new CustomListFragmentDialog();
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
               this.categories=categories;

    }

    @Override
    public void onRegionList(List<Region> regions) {

    }

    @Override
    public void onLanguages(List<Language> languages) {

    }

    View view=null;

    /**
     * the view is use  to  hold whether user is clicked on next or more company button
     * for prepare for next step or create more companies to sponsor the challenge
      *
     * @param company is after successful registration of company
     */
    @Override
    public void onCompanyRegister(Company company) {

        sponsorIds.add(company.getCompanyId());


        if(view!=null){
            if(view.getId()==R.id.btn_more_companies){
                clearAll();

                iv_profile_pic.requestFocus();
                if(sponsorIds.size()==2){
                    view.setVisibility(View.GONE);
                }
            }else if(view.getId()==R.id.btn_next){
//                intent.putExtra("challenge_type",1);
                StringBuilder ids=new StringBuilder();
                for(int index=0;index<sponsorIds.size();index++)
                {
                    if(index>0)
                        ids.append(',').append(sponsorIds.get(index));
                      else
                        ids.append(sponsorIds.get(index));

                }
                userSession.setSponsorIds(ids.toString());
                Intent privateSponsor=new Intent(this, SponsorChallengeFormActivity.class);
                privateSponsor.putExtra("privateSponsor",0);
                startActivity(privateSponsor);
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
//                    edt_city.setError(null);
                    country_pos=position;
                    country=countries.get(position);
                }
                break;
            case R.id.edt_city:
                if(cities!=null)
                {
                    edt_city.setText(cities.get(position).getCityName());
//                    edt_city.setError(null);
                    city=cities.get(position);
                    city_pos=position;
                }
                break;
            case R.id.edt_category:
                if(categories!=null)
                {
                    edt_category.setText(categories.get(position).getCategoryName());
//                    edt_category.setError(null);
                    category_pos=position;
                }
                break;
        }
showFragment=null;
    }
}
