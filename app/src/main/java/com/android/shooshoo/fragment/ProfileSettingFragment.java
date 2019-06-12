package com.android.shooshoo.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.CategorySelectionAdapter;
import com.android.shooshoo.models.Brand;
import com.android.shooshoo.models.Category;
import com.android.shooshoo.models.City;
import com.android.shooshoo.models.Country;
import com.android.shooshoo.presenters.DataLoadPresenter;
import com.android.shooshoo.utils.ApiUrls;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.CustomListFragmentDialog;
import com.android.shooshoo.views.BaseView;
import com.android.shooshoo.views.DataLoadView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 * Use the {@link ProfileSettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 * this is Profile screen to show general profile settings options
 */
public class ProfileSettingFragment extends Fragment implements View.OnClickListener,DataLoadView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    BaseView baseView;
    DataLoadPresenter dataLoadPresenter;
    List<Category> categoryArrayList=new ArrayList<Category>();
    CategorySelectionAdapter categorySelectionAdapter;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
        @BindView(R.id.edt_user_name)
    EditText edt_user_name;
    @BindView(R.id.edt_user_email)
    EditText edt_user_email;
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
    @BindView(R.id.btn_save)
    TextView btn_save;
    @BindView(R.id.btn_more_categories)
    TextView btn_more_categories;
    ConnectionDetector connectionDetector;

    final String genders[]=new String[]{"Male","Female","Others"};
    Country country=new Country();
    String gender="male";
    City city=new City();
    int country_pos=-1,city_pos=-1;

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
        ProfileSettingFragment fragment = new ProfileSettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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
        setFoucusChange(edt_user_name,view,R.id.user_name_line);
        setFoucusChange(edt_user_email,view,R.id.user_email_line);
        setFoucusChange(edt_first_name,view,R.id.first_name_line);
        setFoucusChange(edt_last_name,view,R.id.last_name_line);
        setFoucusChange(edt_street_name,view,R.id.street_name_line);
        setFoucusChange(edt_street_number,view,R.id.street_number_line);
        setFoucusChange(edt_zipcode,view,R.id.zipcode_line);
        setFoucusChange(edt_mobile_number,view,R.id.mobile_number_line);
        setFoucusChange(edt_iban,view,R.id.iban_line);
        setFoucusChange(edt_bic_swift,view,R.id.bic_swift_line);
        setFoucusChange(edt_acc_owner,view,R.id.acc_owner_line);
        setFoucusChange(edt_bank_name,view,R.id.bank_name_line);
        btn_save.setOnClickListener(this);
        btn_more_categories.setOnClickListener(this);
        btn_save_bank.setOnClickListener(this);
        dataLoadPresenter=new DataLoadPresenter();
        dataLoadPresenter.attachView(this);
        connectionDetector=new ConnectionDetector(getActivity());
        this.categorySelectionAdapter = new CategorySelectionAdapter(getContext(), this.categoryArrayList);
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


    }

    private void loadData() {
        dataLoadPresenter.loadCountryData();
        dataLoadPresenter.loadCites("99");
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
            case R.id.btn_save:
               if(validateSave()){
                   showMessage("Saved successfully");
               }
                break;
            case R.id.btn_save_bank:
                if(validateBank()){
                    showMessage("Bank data Saved successfully");
                }
                break;
            case R.id.btn_more_categories:
                if(categorySelectionAdapter!=null)
                categorySelectionAdapter.add();
                break;
        }

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

    @Override
    public void onCitiesData(List<City> cities) {
        displayListCity(cities,edt_city);
    }

    @Override
    public void onCountryData(List<Country> countries) {
        displayList(countries,edt_country);
    }

    @Override
    public void onAllCategories(List<Category> categories) {
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
    }

    @Override
    public void showMessage(int stringId) {
baseView.showMessage(stringId);
    }

    @Override
    public void showMessage(String message) {
        baseView.showMessage(message);

    }

    @Override
    public void showProgressIndicator(Boolean show) {
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
}
