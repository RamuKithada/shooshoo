package com.android.shooshoo.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.CategorySelectionAdapter;
import com.android.shooshoo.models.Brand;
import com.android.shooshoo.models.Category;
import com.android.shooshoo.models.CategoryModel;
import com.android.shooshoo.models.City;
import com.android.shooshoo.models.Country;
import com.android.shooshoo.models.Language;
import com.android.shooshoo.models.LoginSuccess;
import com.android.shooshoo.models.Region;
import com.android.shooshoo.models.UserInfo;
import com.android.shooshoo.presenters.DataLoadPresenter;
import com.android.shooshoo.presenters.ProfilePresenter;
import com.android.shooshoo.presenters.UpdateUserInfoPresenter;
import com.android.shooshoo.utils.ApiUrls;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.CustomListFragmentDialog;
import com.android.shooshoo.utils.FragmentListDialogListener;
import com.android.shooshoo.utils.UserSession;
import com.android.shooshoo.views.BaseView;
import com.android.shooshoo.views.DataLoadView;
import com.android.shooshoo.views.ProfileView;
import com.android.shooshoo.views.UpdateUserInfoView;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;

import static android.app.Activity.RESULT_OK;
import static com.android.shooshoo.activity.ProfileFillingFormActivity.RESULT_LOAD_IMAGE;
import static com.android.shooshoo.utils.ApiUrls.PROFILE_IMAGE_URL;

/**
 *
 * Use the {@link ProfileSettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 * this is Profile screen to show general profile settings options
 */
public class ProfileSettingFragment extends Fragment implements View.OnClickListener,DataLoadView, ProfileView,UpdateUserInfoView,FragmentListDialogListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    BaseView baseView;
    DataLoadPresenter dataLoadPresenter;
    ProfilePresenter profilePresenter;
    UpdateUserInfoPresenter updateUserInfoPresenter;
    List<Category> categoryArrayList=new ArrayList<Category>();
    CategorySelectionAdapter categorySelectionAdapter;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
        @BindView(R.id.edt_user_name)
    EditText edt_user_name;
    @BindView(R.id.edt_user_email)
    EditText edt_user_email;
    @BindView(R.id.edt_dob)
    AppCompatEditText edt_dob;
    @BindView(R.id.edt_first_name)
    EditText edt_first_name;
    @BindView(R.id.edt_last_name)
    EditText edt_last_name;
    @BindView(R.id.edt_street_name)
    EditText edt_street_name;
    @BindView(R.id.edt_street_number)
    EditText edt_street_number;
    @BindView(R.id.edt_zipcode)
    EditText edt_zipcode;
    @BindView(R.id.edt_mobile_number)
    EditText edt_mobile_number;
    @BindView(R.id.edt_iban)
    EditText edt_iban;
    @BindView(R.id.edt_bic_swift)
    EditText edt_bic_swift;
    @BindView(R.id.edt_acc_owner)
    EditText edt_acc_owner;
    @BindView(R.id.edt_bank_name)
    EditText edt_bank_name;

    @BindView(R.id.iv_profile_pic)
    CircleImageView iv_profile_pic;


    @BindView(R.id.edt_country)
    EditText edt_country;

    @BindView(R.id.edt_city)
    EditText edt_city;

    @BindView(R.id.edt_gender)
    EditText edt_gender;
    @BindView(R.id.cat_subcat_list)
    RecyclerView cat_subcat_list;
    @BindView(R.id.btn_save_bank)
    TextView btn_save_bank;
//    @BindView(R.id.btn_save)
//    TextView btn_save;
    @BindView(R.id.btn_more_categories)
    ImageView btn_more_categories;

    @BindView(R.id.ll_upload_photo_layout)
    LinearLayout ll_upload_photo_layout;
    ConnectionDetector connectionDetector;

    final String genders[]=new String[]{"Male","Female","Others"};
    Country country=new Country();
    String gender="male";
    City city=new City();
    int country_pos=-1,city_pos=-1,gender_pos=-1;
    static ProfileSettingFragment fragment;
    UserSession userSession;

    public ProfileSettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileSettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileSettingFragment newInstance(String param1, String param2) {
        if(fragment==null)
        {
            fragment = new ProfileSettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

//        btn_save.setOnClickListener(this);
        btn_more_categories.setOnClickListener(this);
        btn_save_bank.setOnClickListener(this);
        dataLoadPresenter=new DataLoadPresenter();
        dataLoadPresenter.attachView(this);
        profilePresenter=new ProfilePresenter();
        profilePresenter.attachView(this);
        updateUserInfoPresenter=new UpdateUserInfoPresenter();
        updateUserInfoPresenter.attachView(this);

        connectionDetector=new ConnectionDetector(getActivity());
        userSession=new UserSession(getActivity());
        this.categorySelectionAdapter = new CategorySelectionAdapter(getContext(), this.categoryArrayList);
        edt_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(edt_dob);
            }
        });
        edt_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomListFragmentDialog tv=new CustomListFragmentDialog();
                Bundle args = new Bundle();
                args.putStringArray("list",genders);
                args.putInt("view",R.id.edt_gender);
                tv.setArguments(args);
                tv.show(getChildFragmentManager(),"ha");
            }
        });

        if(connectionDetector.isConnectingToInternet())
            loadData();

        ll_upload_photo_layout.setOnClickListener(this);
    }

    private void loadData() {
          gender_pos=country_pos=city_pos=-1;
          isloadedFirst=3;
        profilePresenter.loadProfile(userSession.getUserId());

    }

    String active="#FFFFFF",inactive="#CCCCCC";
    void setFoucusChange(EditText editText, View view,int line_id){
        final View line=view.findViewById(line_id);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    line.setBackgroundColor(Color.parseColor(active));
               else
                    line.setBackgroundColor(Color.parseColor(inactive));

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save_bank:
               if(validateSave()){
                   showMessage("Saved successfully");
                   updateUserInfoPresenter.updateProfile(newsImage,userSession.getUserId(),edt_first_name.getText().toString(),
                           edt_last_name.getText().toString(),country.getCountryId(),city.getCityId(),edt_zipcode.getText().toString()
                   ,edt_street_name.getText().toString(),edt_street_number.getText().toString(),edt_mobile_number.getText().toString()
                   ,gender,ApiUrls.DEVICE_TOKEN);
                   StringBuilder cats=new StringBuilder();
//                   StringBuilder brands=new StringBuilder();
                   for (CategoryModel categoryModel: categorySelectionAdapter.getCategoryModels()) {
                       if(categoryArrayList.size()>categoryModel.getCategory()) {
                          Category mCategory= categoryArrayList.get(categoryModel.getCategory());
                           if (cats.length() > 0) {
                               if (mCategory.getCategoryId() != null && !cats.toString().contains(mCategory.getCategoryId()))
                                   cats.append(',').append(mCategory.getCategoryId());
                           } else {
                               if (mCategory.getCategoryId() != null && !cats.toString().contains(mCategory.getCategoryId()))
                                    cats.append(mCategory.getCategoryId());
                           }
                       }

//                           if(categoryArrayList.get(categoryModel.getCategory()).getBrands()!=null)
                         /*      if(categoryArrayList.get(categoryModel.getCategory()).getBrands().size()>categoryModel.getSubcategory()){
                                   Brand brand=categoryArrayList.get(categoryModel.getCategory()).getBrands().get(categoryModel.getSubcategory());
                                   if(cats.length()>0){
                                       if(brand.getCategoryId()!=null&&!cats.toString().contains(brand.getCategoryId()))
                                       cats.append(',').append(brand.getCategoryId());
                                   }else
                                   {
                                       cats.append(brand.getCategoryId());
                                   }

                                 *//*  if(brands.length()>0){
                                       if(brand.getBrandId()!=null&&!brands.toString().contains(brand.getBrandId()))
                                        brands.append(',').append(brand.getBrandId());
                                   }else{
                                          brands.append(brand.getBrandId());
                                   }*//*


                               }*/
                   }


                             Log.e("cats",cats.toString());
                             updateUserInfoPresenter.updateUserCat(userSession.getUserId(),cats.toString());

//                           Log.e("brands",brands.toString());
//                           updateUserInfoPresenter.updateUserCat(userSession.getUserId(),brands.toString());


               }

               /* if(validateBank()){
                    showMessage("Bank data Saved successfully");
                    updateUserInfoPresenter.saveBankDetails(userSession.getUserId(),edt_iban.getText().toString(),edt_bic_swift.getText().toString(),edt_acc_owner.getText().toString(),edt_bank_name.getText().toString());
                }*/
                break;
            case R.id.btn_more_categories:
                if(categorySelectionAdapter!=null)
                categorySelectionAdapter.add();
                break;
            case R.id.ll_upload_photo_layout:
                        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropMenuCropButtonTitle("Choose a Picture")
                .start(getActivity(),this);

//                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent,"Select Picture"), RESULT_LOAD_IMAGE);

//                startActivityForResult(i, RESULT_LOAD_IMAGE);
                break;
        }

    }
    DatePickerDialog datePickerDialog;

    private void setDate(final AppCompatEditText edt_dob) {

        Calendar c = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getContext(), R.style.datepicker, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                edt_dob.setText(year + "-" + (month + 1) + "-" + dayOfMonth);

            }
        },
                c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();
        hideKeyboard(getContext(), edt_dob);

    }
    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    private boolean validateBank() {
        if(!ApiUrls.validateString(edt_acc_owner.getText().toString())){

            edt_acc_owner.setError("Enter Account Holder Name");
            return false;
        }

        if(!ApiUrls.validateString(edt_iban.getText().toString())){

            edt_iban.setError("Enter IBN ");
            return false;
        }

        if(!ApiUrls.validateString(edt_bank_name.getText().toString())){

            edt_bank_name.setError("Enter Bank Name");
            return false;
        }

        if(!ApiUrls.validateString(edt_bic_swift.getText().toString())){

            edt_bic_swift.setError("Enter BIC SWIFT");
            return false;
        }

        return true;
    }

    private boolean validateSave() {

        if(!ApiUrls.validateString(edt_user_name.getText().toString())){
            edt_user_name.setError("Enter User Name");
            edt_user_name.requestFocus();
            return false;
        }
        if(edt_user_name.getText().toString().length()<2){
            edt_user_name.setError("User Name have at least 2 letters");
            edt_user_name.requestFocus();
            return false;

        }
        if(edt_user_name.getText().toString().length()>70){
            edt_user_name.setError("User Name have maximum 70 letters");
            edt_user_name.requestFocus();
            return false;

        }
            String email=edt_user_email.getText().toString();
        if(!ApiUrls.validateString(email))
        {
            edt_user_email.setError("Enter email");
            edt_user_email.requestFocus();
            return false;
        }

        if(edt_user_email.getText().toString().length()<6){
            edt_user_email.setError("Email  is minimum 6 letters");
            edt_user_email.requestFocus();
            return false;

        }


        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            edt_user_email.setError("Enter valid email");
            edt_user_email.requestFocus();
            return false;
        }


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
        if(country_pos<=0){
            showMessage("Please Select Country");
            return false;
        }
        if(city_pos<=0){
            showMessage("Please Select City");
            return false;
        }


        if(!ApiUrls.validateString(edt_zipcode.getText().toString())){
            edt_zipcode.setError("Enter Zipcode Name");
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

        if(!ApiUrls.validateString(edt_street_name.getText().toString())){
            edt_street_name.setError("Enter Street Name ");
            edt_street_name.requestFocus();
            return false;
        }


        if(edt_street_name.getText().toString().length()<3){

            edt_street_name.setError(" Street Name have at least 3 letters");
            edt_street_name.requestFocus();
            return false;

        }
        if(edt_street_name.getText().toString().length()>255){

            edt_street_name.setError(" Street Name have maximum 255 letters");
            edt_street_name.requestFocus();
            return false;

        }


        if(!ApiUrls.validateString(edt_mobile_number.getText().toString())){
            edt_mobile_number.setError("Enter Mobile Number");
            edt_mobile_number.requestFocus();
            return false;
        }


        if(edt_mobile_number.getText().toString().length()<4){

            edt_mobile_number.setError(" Mobile Number have at least 4 digits");
            edt_mobile_number.requestFocus();
            return false;

        }
        if(edt_mobile_number.getText().toString().length()>15){
            edt_mobile_number.setError("Mobile Number have maximum 15 digits");
            edt_mobile_number.requestFocus();
            return false;

        }
        if(!Patterns.PHONE.matcher(edt_mobile_number.getText().toString()).matches()){
            edt_mobile_number.setError("Enter valid Mobile Number");
            edt_mobile_number.requestFocus();
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
        if(city_pos<0&&isloadedFirst>0){
            isloadedFirst--;
            if(cities!=null&&userInfo!=null){
                for(int index=0;index<cities.size();index++)
                    if(cities.get(index).getCityId().equalsIgnoreCase(userInfo.getCity())){
//                        city_pos=index;
                        onEditView(R.id.edt_city,index);
                        return;
                    }

            }

        }
    }

    List<Country> countries;
    @Override
    public void onCountryData(List<Country> countries) {
        this.countries=countries;
        displayList(countries,edt_country);
        if(country_pos<0&&isloadedFirst>0){
               isloadedFirst--;
            if(countries!=null&&userInfo!=null){
                for(int index=0;index<countries.size();index++)
                    if(countries.get(index).getCountryId().equalsIgnoreCase(userInfo.getCountry())){
//                        country_pos=index;
                        onEditView(R.id.edt_country,index);
                        return;
                    }

            }
        }
    }

    @Override
    public void onAllCategories(List<Category> categories) {
        if(categories==null)
            return;
        Category category = new Category();
        category.setCategoryName("Category");
        Brand brand = new Brand();
        brand.setBrandName("Subcategory");
        List<Brand> brands = new ArrayList();
        brands.add(brand);
        category.setBrands(brands);
        categories.add(0, category);
        this.categoryArrayList = categories;
        this.categorySelectionAdapter = new CategorySelectionAdapter(getContext(), this.categoryArrayList);
        this.cat_subcat_list.setAdapter(this.categorySelectionAdapter);
        if(isloadedFirst>0&&userInfo!=null){
           isloadedFirst--;
           String s=userInfo.getCategories();
           if(s!=null){
               String[] strings=s.split(",");

               if(strings.length>=0){
                   categorySelectionAdapter.getCategoryModels().clear();
               }
               for(int index=1;index<categories.size();index++){
                  if(s.toLowerCase().contains(categories.get(index).getCategoryId().toLowerCase())){
                      for (String id:strings){
                          if(id.equalsIgnoreCase(categories.get(index).getCategoryId())){
                              categorySelectionAdapter.getCategoryModels().add(0,new CategoryModel(index,0));
                          }
                      }
                 /*     if(userInfo.getBrands()!=null) {
                          int noOfBrands=0;
                          String brandss = userInfo.getBrands();
                          String[] brandsList = brandss.split(",");
                          List<Brand> list = categories.get(index).getBrands();
                          for (int brand_pos = 0; brand_pos < list.size(); brand_pos++) {
                              if (brandss.toLowerCase().contains(list.get(brand_pos).getBrandId().toLowerCase())) {
                                  for (String id : brandsList) {
                                      if (id.equalsIgnoreCase(list.get(brand_pos).getBrandId())) {
                                         if(noOfBrands==0)
                                         {
                                             categorySelectionAdapter.getCategoryModels().get(0).setSubcategory(brand_pos);
                                             noOfBrands++;
                                         }else {
                                             categorySelectionAdapter.getCategoryModels().add(new CategoryModel(index,brand_pos));
                                             noOfBrands++;
                                         }

                                      }
                                  }
                              }
                          }
                      }*/

                   }

               }
               categorySelectionAdapter.notifyDataSetChanged();

           }



        }

    }

    @Override
    public void onRegionList(List<Region> regions) {

    }

    @Override
    public void onLanguages(List<Language> languages) {

    }

    @Override
    public void showMessage(int stringId) {
        if(baseView!=null )
            baseView.showMessage(stringId);

    }

    @Override
    public void showMessage(String message) {
        if(baseView!=null)
            baseView.showMessage(message);


    }

    @Override
    public void showProgressIndicator(Boolean show) {
        if(baseView!=null)
        baseView.showProgressIndicator(show);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof BaseView){
            baseView= (BaseView) context;
        }else {
            Log.e("ProfileSettingFragment","must implement Baseview");

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        baseView=null;
        dataLoadPresenter.detachView();
    }

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
                showFragment.show(getChildFragmentManager(),"country");

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
                showFragment.show(getChildFragmentManager(),"city");
            }
        });


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

    public UserInfo userInfo=null;
     int  isloadedFirst=3;
    @Override
    public void onProfileData(final UserInfo userInfo) {
        if(userInfo!=null) {
            this.userInfo = userInfo;
            edt_user_name.setText(userInfo.getUserName());
            edt_user_email.setText(userInfo.getEmail());
            edt_first_name.setText(userInfo.getFirstName());
            edt_last_name.setText(userInfo.getLastName());
            edt_city.setText(userInfo.getCity());
            edt_dob.setText(userInfo.getDob());
            edt_country.setText(userInfo.getCountry());
            edt_street_name.setText(userInfo.getStreet());
            edt_street_number.setText(userInfo.getStreetNum());
            edt_zipcode.setText(userInfo.getZipcode());
            edt_mobile_number.setText(userInfo.getMobileNumber());
            Picasso.with(getContext()).load(PROFILE_IMAGE_URL+userInfo.getImage()).error(R.drawable.profile_1).into(iv_profile_pic);
            for(int index=0;index<genders.length;index++)
                   if(genders[index].equalsIgnoreCase(userInfo.getGender())){
                        gender_pos=index;
            }
            edt_gender.setText(userInfo.getGender());
                   if(connectionDetector.isConnectingToInternet()) {
                       isloadedFirst=3;
                       dataLoadPresenter.loadCountryData();
                       dataLoadPresenter.loadAllcategoriesList();
                   }


        }

    }

    @Override
    public void onBrands(List<Brand> brands) {

    }



  /*  @Override
    public void onBankDetails(UserBankDetails bankDetails) {
      *//*  if(bankDetails!=null){
            edt_acc_owner.setText(bankDetails.getAccountOwner());
            edt_iban.setText(bankDetails.getIban());
            edt_bic_swift.setText(bankDetails.getBicSwift());
            edt_bank_name.setText(bankDetails.getBankName());
        }*//*
    }*/

    @Override
    public void onUpdateUserInfo(ResponseBody responseBody) {

    }

    @Override
    public void loginDetails(LoginSuccess loginSuccess) {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri imageUri = data.getData();
            setImage(imageUri);
            CropImage.activity(imageUri).setAspectRatio(4,3)
                    .start(getContext(), this);
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
        Picasso.with(getContext()).load(picturePath).into(iv_profile_pic);
    }


}
