package com.android.shooshoo.jackpot;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.shooshoo.R;
import com.android.shooshoo.activity.BaseActivity;
import com.android.shooshoo.adapter.CategorySelectionAdapter;
import com.android.shooshoo.adapter.LanguagesAdapter;
import com.android.shooshoo.adapter.RegionsAdapter;
import com.android.shooshoo.models.Brand;
import com.android.shooshoo.models.Category;
import com.android.shooshoo.models.CategoryModel;
import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.City;
import com.android.shooshoo.models.Country;
import com.android.shooshoo.models.Currency;
import com.android.shooshoo.models.Language;
import com.android.shooshoo.models.Region;
import com.android.shooshoo.presenters.DataLoadPresenter;
import com.android.shooshoo.presenters.JackpotChallengePresenter;
import com.android.shooshoo.utils.ApiUrls;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.CustomListFragmentDialog;
import com.android.shooshoo.utils.FragmentListDialogListener;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.views.BaseView;
import com.android.shooshoo.views.DataLoadView;
import com.android.shooshoo.views.JackpotChallengeView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/***
 *   {@link JackpotAudienceActivity} is used to address the participants who are participates in this jacpot challenge
 *
 * cat_subcat_list show to select category and it brand .
 * categorySelectionAdapter is used to make this functionality.
 * minAgePos,maxAgePos are the positions of the spinner_min_age adapter and spinner_max_age adapter to get limits of the audience age
 *  spinner_miles is used to show surrounding area  in miles to participants can participate in this challenge.
 */
public class JackpotAudienceActivity extends BaseActivity implements DataLoadView, FragmentListDialogListener,JackpotChallengeView,CompoundButton.OnCheckedChangeListener,View.OnClickListener, BaseView, TextWatcher {

    @BindView(R.id.btn_next)
    TextView btn_next;

    @BindView(R.id.iv_back)
    ImageView iv_back;

    @BindViews({R.id.button1,R.id.button2,R.id.button3,R.id.button4})
    List<Button> buttons;

    @BindView(R.id.tv_title)
    TextView title;

    @BindView(R.id.edt_amount)
    AppCompatEditText edt_amount;

    @BindView(R.id.national_lay)
    RelativeLayout national_lay;

    @BindView(R.id.international_lay)
    RelativeLayout international_lay;

    @BindView(R.id.tv_national)
    TextView tv_national;

    @BindView(R.id.tv_international)
    TextView tv_international;

    @BindView(R.id.cat_subcat_list)
    RecyclerView cat_subcat_list;

    @BindView(R.id.btn_more_categories)
    ImageView btn_more_categories;

    @BindView(R.id.audience_size)
    AppCompatTextView audience_size;

   /* @BindView(R.id.edt_price_worth)
    AppCompatEditText edt_price_worth;*/

    @BindView(R.id.edt_limited_access)
    AppCompatEditText edt_limited_access;

    @BindView(R.id.no_of_winners)
    AppCompatEditText no_of_winners;

    @BindView(R.id.edt_country)
    AppCompatEditText edt_country;


    @BindView(R.id.edt_city)
    AppCompatEditText edt_city;

    @BindView(R.id.edt_zipcode)
    AppCompatEditText edt_zipcode;


    @BindView(R.id.edt_miles)
    AppCompatEditText edt_miles;

    @BindView(R.id.edt_currency)
    AppCompatEditText edt_currency;



    @BindView(R.id.checkbox_male)
    CheckBox checkbox_male;

    @BindView(R.id.checkbox_female)
    CheckBox checkbox_female;

    @BindView(R.id.edt_min_age)
    AppCompatEditText edt_min_age;


    @BindView(R.id.edt_max_age)
    AppCompatEditText edt_max_age;


    @BindView(R.id.region_lay)
    RelativeLayout region_lay;


    @BindView(R.id.btn_more_regions)
    ImageView btn_more_regions;

    @BindView(R.id.edt_region)
    AppCompatEditText edt_region;

    @BindView(R.id.regions_list)
    RecyclerView regions_list;


    @BindView(R.id.btn_more_languages)
    ImageView btn_more_languages;

    @BindView(R.id.edt_language)
    AppCompatEditText edt_language;

    @BindView(R.id.language_list)
    RecyclerView language_list;

    @BindView(R.id.country_lay)
    LinearLayout country_lay;



    DataLoadPresenter dataLoadPresenter;
    ConnectionDetector connectionDetector;
    CategorySelectionAdapter categorySelectionAdapter;

    JackpotChallengePresenter jackpotChallengePresenter;
    List<Category> categoryArrayList=new ArrayList<Category>();
    List<Country> countries=new ArrayList<Country>();
    List<City> cities=new ArrayList<City>();
    List<Language> languages=new ArrayList<Language>();
    List<Region> regions=new ArrayList<Region>();
    RegionsAdapter regionsAdapter;
    LanguagesAdapter languagesAdapter;

    Country country;
    City city;
    int city_pos=-1,country_pos=-1,region_pos=-1,languages_pos=-1,currency_pos=-1;
    List<Currency> currencies=new ArrayList<Currency>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jackpot_audience);
        ButterKnife.bind(this);
        btn_next.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        btn_more_categories.setOnClickListener(this);
        title.setText("OUTREACH");
        setStage(1);
        spinnersInti();
        categorySelectionAdapter=new CategorySelectionAdapter(this,categoryArrayList);
        dataLoadPresenter=new DataLoadPresenter();
        dataLoadPresenter.attachView(this);
        jackpotChallengePresenter =new JackpotChallengePresenter();
        jackpotChallengePresenter.attachView(this);
        connectionDetector=new ConnectionDetector(this);
        checkbox_male.setOnCheckedChangeListener(this);
        checkbox_female.setOnCheckedChangeListener(this);
        btn_more_regions.setOnClickListener(this);
        btn_more_languages.setOnClickListener(this);
        regionsAdapter=new RegionsAdapter(this);
        regions_list.setLayoutManager(new LinearLayoutManager(this));
        regions_list.setAdapter(regionsAdapter);
        edt_min_age.addTextChangedListener(this);
        edt_max_age.addTextChangedListener(this);

        languagesAdapter=new LanguagesAdapter(this);
        language_list.setLayoutManager(new LinearLayoutManager(this));
        language_list.setAdapter(languagesAdapter);

        if(connectionDetector.isConnectingToInternet()){
            dataLoadPresenter.loadAllcategoriesList();
            dataLoadPresenter.loadCountryData();
            dataLoadPresenter.loadLanguages();
            dataLoadPresenter.loadRegion();
        }



    }

    private void spinnersInti() {
        currencies.add(new Currency("Indian Rupee","INR","₹"));
        currencies.add(new Currency("US Dollar","USD","$"));
        currencies.add(new Currency("Euro","EUR","€"));

        national_lay.setOnClickListener(radioClickListener);
        international_lay.setOnClickListener(radioClickListener);
        tv_national.setTextColor(Color.parseColor("#FFFFFF"));
        tv_international.setTextColor(Color.parseColor("#85868A"));
        nationalization = 0;
        region_lay.setVisibility(View.GONE);
        country_lay.setVisibility(View.VISIBLE);
        edt_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countries==null)
                    return;

                final String[] lables=new String[countries.size()];
                for(int index=0;index<countries.size();index++)
                {
                    lables[index]=countries.get(index).getCountryName();
                }

                CustomListFragmentDialog showFragment=new CustomListFragmentDialog();
                Bundle args = new Bundle();
                args.putStringArray("list",lables);
                args.putInt("view",edt_country.getId());
                showFragment.setArguments(args);
                showFragment.show(getSupportFragmentManager(),"country");


            }
        });
        edt_currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] strings=new String[currencies.size()];
                for(int index=0;index<currencies.size();index++){
                    Currency currency=currencies.get(index);
                    strings[index]=currency.getName()+"  "+currency.getCode()+" ( "+currency.getSymbol()+" )";
                }
                CustomListFragmentDialog showFragment=new CustomListFragmentDialog();
                Bundle args = new Bundle();
                args.putStringArray("list",strings);
                args.putInt("view",edt_currency.getId());
                showFragment.setArguments(args);
                showFragment.show(getSupportFragmentManager(),"currency");



            }
        });
        edt_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cities==null)
                {
                    city_pos=-1;
                    showMessage("No Cites");
                    return;
                }

                final String[] lables=new String[cities.size()];
                for(int index=0;index<cities.size();index++)
                {
                    lables[index]=cities.get(index).getCityName();
                }
                CustomListFragmentDialog showFragment=new CustomListFragmentDialog();
                Bundle args = new Bundle();
                args.putStringArray("list",lables);
                args.putInt("view",edt_city.getId());
                showFragment.setArguments(args);
                showFragment.show(getSupportFragmentManager(),"city");
            }
        });
        edt_region.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(regions==null)
                    return;

                final String[] lables=new String[regions.size()];
                for(int index=0;index<regions.size();index++)
                {
                    lables[index]=regions.get(index).getRegName();
                }

                CustomListFragmentDialog showFragment=new CustomListFragmentDialog();
                Bundle args = new Bundle();
                args.putStringArray("list",lables);
                args.putInt("view",edt_region.getId());
                showFragment.setArguments(args);
                showFragment.show(getSupportFragmentManager(),"region");

            }
        });
        edt_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(languages==null)
                    return;

                final String[] lables=new String[languages.size()];
                for(int index=0;index<languages.size();index++)
                {
                    lables[index]=languages.get(index).getName();
                }

                CustomListFragmentDialog showFragment=new CustomListFragmentDialog();
                Bundle args = new Bundle();
                args.putStringArray("list",lables);
                args.putInt("view",edt_language.getId());
                showFragment.setArguments(args);
                showFragment.show(getSupportFragmentManager(),"language");

            }
        });

    }


    int nationalization=0;
    View.OnClickListener radioClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.national_lay:
                    tv_national.setTextColor(Color.parseColor("#F31F68"));
                    tv_international.setTextColor(Color.parseColor("#85868A"));
                    nationalization = 0;
                    region_lay.setVisibility(View.GONE);
                    country_lay.setVisibility(View.VISIBLE);
                    break;
                case R.id.international_lay:
                    tv_international.setTextColor(Color.parseColor("#F31F68"));
                    tv_national.setTextColor(Color.parseColor("#85868A"));
                    nationalization = 1;
                    region_lay.setVisibility(View.VISIBLE);
                    country_lay.setVisibility(View.GONE);
                    break;
            }
            getSizeofAudience();
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_next:


                if(validate())
                {
                    submitData();

}

//
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_more_categories:
                categorySelectionAdapter.add();
                break;
            case R.id.btn_more_regions:
                if(region_pos>0&&region_pos<regions.size()){
                    regionsAdapter.add(regions.remove(region_pos));
                    region_pos=-1;
                    edt_region.setText("Region");
                }
                break;
            case R.id.btn_more_languages:
                if(languages_pos>0&&languages_pos<languages.size()){
                    languagesAdapter.add(languages.remove(languages_pos));
                    languages_pos=-1;
                    edt_language.setText("Language");
                }
                break;

        }
    }

    private void submitData() {


        String radar="national";
        if(nationalization==1){
            radar="international";
        }
        StringBuilder  gender=new StringBuilder();
        if(checkbox_male.isChecked()){
            gender.append("Male");

        }
        if(checkbox_female.isChecked()){
            if(gender.length()>0){
                gender.append(',').append("Female");
            }else {
                gender.append("Female");
            }

        }

        StringBuilder cats=new StringBuilder();
        if(categorySelectionAdapter.getCategoryModels()!=null)
        for (CategoryModel categoryModel: categorySelectionAdapter.getCategoryModels()) {
            if(categoryArrayList.size()>categoryModel.getCategory()){
                Category mCategory= categoryArrayList.get(categoryModel.getCategory());
                if (cats.length() > 0) {
                    if (mCategory.getCategoryId() != null && !cats.toString().contains(mCategory.getCategoryId()))
                        cats.append(',').append(mCategory.getCategoryId());
                } else {
                    if (mCategory.getCategoryId() != null && !cats.toString().contains(mCategory.getCategoryId()))
                        cats.append(mCategory.getCategoryId());
                }
            }

        }

        StringBuilder regions=new StringBuilder();
        if(nationalization==1){
            if(regionsAdapter.selectedRegions()!=null)
            for (Region region:regionsAdapter.selectedRegions()){
                if(regions.length()<=0){
                    regions.append(region.getRegId());
                }else {
                    regions.append(',').append(region.getRegId());
                }

            }
            if(region_pos>0&&region_pos<this.regions.size()){
                if(regions.length()<=0){
                    regions.append( this.regions.get(region_pos).getRegId());
                }else {
                    regions.append(',').append( this.regions.get(region_pos).getRegId());
                }

            }

        }

        StringBuilder languageBuilder=new StringBuilder();

        if(languagesAdapter.selectedLanguages()!=null)
        for (Language language:languagesAdapter.selectedLanguages()) {
            if(languageBuilder.length()<=0){
                languageBuilder.append(language.getId());
            }else {
                languageBuilder.append(',').append(language.getId());
            }

        }
        if(languages_pos>0&&languages_pos<this.languages.size()){
            if(languageBuilder.length()<=0){
                languageBuilder.append( this.languages.get(languages_pos).getId());
            }else {
                languageBuilder.append(',').append( this.languages.get(languages_pos).getId());
            }

        }

        String cityId="",countryId="",zipCode="",miles="";
        if(nationalization==0){
            cityId= cities.get(city_pos).getCityId();
            countryId=countries.get(country_pos).getCountryId();
            zipCode=edt_zipcode.getText().toString();
            miles=edt_miles.getText().toString();
        }

        if(!connectionDetector.isConnectingToInternet())
        {
            showMessage("Please check internet connection !");
            return;
        }









        jackpotChallengePresenter.createAudience(userSession.getJackpot().getChallengeId(),
                userSession.getUserId(),edt_amount.getText().toString(),
                edt_limited_access.getText().toString(),no_of_winners.getText().toString(), radar,
                zipCode,miles, countryId,cityId,cats.toString(),regions.toString(),languageBuilder.toString(),
                edt_min_age.getText().toString(),edt_max_age.getText().toString(),gender.toString());

    }

    private boolean validate() {
        if(!ApiUrls.validateString(edt_amount.getText().toString())){
            showMessage("Enter amount");
            edt_amount.requestFocus();
            return false;
        }
        try {
            if(Integer.valueOf(edt_amount.getText().toString())<=0){
                showMessage("Enter amount");
                edt_amount.requestFocus();
                return false;
            }
        }catch (NumberFormatException e)
        {
            e.printStackTrace();
        }


        if(currency_pos<0){
            showMessage("Please select Currency");
            return false;
        }

  /*      if(!ApiUrls.validateString(edt_key_des.getText().toString())){
            edt_key_des.setError("Enter Key Description");
            edt_key_des.requestFocus();
            return false;
        }*/
      /*  if(!ApiUrls.validateString(edt_price_worth.getText().toString())) {
            edt_price_worth.setError("Enter price");
            edt_price_worth.requestFocus();
            return false;
        }*/
        if(!ApiUrls.validateString(no_of_winners.getText().toString())){
            showMessage("Enter number of winners");
            no_of_winners.requestFocus();
            return false;
        }
        try {
            if(Integer.valueOf(no_of_winners.getText().toString())<=0){
                showMessage("Enter number of winners    ");
                no_of_winners.requestFocus();
                return false;
            }
        }catch (NumberFormatException e)
        {
            e.printStackTrace();
        }

        if(!ApiUrls.validateString(edt_limited_access.getText().toString())) {
            showMessage("Enter Limited access");
            edt_limited_access.requestFocus();
            return false;
        }

        try {
            if(Integer.valueOf(edt_limited_access.getText().toString())<=0){
                showMessage("Enter Limited access");
                edt_limited_access.requestFocus();
                return false;
            }
        }catch (NumberFormatException e)
        {
            e.printStackTrace();
        }

        if(nationalization==0) {
            if (country_pos < 0) {
                showMessage("Please select Country");
                edt_country.requestFocus();
                return false;
            }
            if (!ApiUrls.validateString(edt_zipcode.getText().toString())) {
                showMessage("Enter ZipCode ");
                edt_zipcode.requestFocus();
                return false;
            }

            if (city_pos < 0) {
                showMessage("Please select City");
                edt_city.requestFocus();
                return false;
            }

            if (!ApiUrls.validateString(edt_miles.getText().toString())) {
                showMessage("Enter miles for challenge range");
                edt_miles.requestFocus();
                return false;
            }
        }else if(nationalization==1){
            if(region_pos<0&&regionsAdapter.getItemCount()<=0){
                   showMessage("Select Challenge Region");
                return false;
            }



        }
        if(categorySelectionAdapter.getItemCount()<3){
            showMessage("Please select at least 3 categories");
            return false;
        }
        List<CategoryModel> models=categorySelectionAdapter.getCategoryModels();
        for (int index=0;index<models.size();index++){
            CategoryModel model=models.get(index);
            if(model.getCategory()<=0){
                showMessage("please select at least 3 categories ");
                return false;
            }

        }



        if (!ApiUrls.validateString(edt_min_age.getText().toString())) {
            showMessage("Please enter starting age");
            edt_min_age.requestFocus();
            return false;
        }
        if (!ApiUrls.validateString(edt_max_age.getText().toString())) {
            edt_max_age.requestFocus();
            showMessage("Please enter ending age");
            return false;
        }
        int minAgePos=0;
        int maxAgePos=0;
        try {
            minAgePos=Integer.valueOf(edt_min_age.getText().toString());
            maxAgePos=Integer.valueOf(edt_max_age.getText().toString());
            if (minAgePos >= maxAgePos) {
                showMessage("Minimum age must be less the Maximum age");
                return false;
            }
        }catch (Exception e){
            return false;
        }



        if(!checkbox_female.isChecked()&&!checkbox_male.isChecked()){
            showMessage("Select atleast one gender ");
            return false;
        }
          if(languages_pos<0&&languagesAdapter.getItemCount()<=0)
          {
             showMessage("Select At Least one language");
              edt_language.requestFocus();
              return false;
          }

        return true;

    }
    /**
     * setStage is for selection one of registration step
     * @param step is step of registration process of a challenge
     */
    private void setStage(int step) {
        for(int index=0;index<buttons.size();index++){
            if(index<=step){
                {
                    buttons.get(index).setBackgroundResource(R.drawable.selected);
                    buttons.get(index).setText(String.valueOf(step+1));
                }
            }else buttons.get(index).setBackgroundResource(R.drawable.unselected);

        }
    }

    @Override
    public void onCitiesData(List<City> cities) {
        this.cities=cities;
        edt_city.setText(null);
        city=null;



    }

    @Override
    public void onCountryData(List<Country> countries) {
        if(countries!=null)
            this.countries=countries;

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
        this.categorySelectionAdapter = new CategorySelectionAdapter(this, this.categoryArrayList);
        this.cat_subcat_list.setAdapter(this.categorySelectionAdapter);
    }

//    int winnerPos=0,maxAgePos=0,minAgePos=0,milesPos=0;
/*    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spinner_max_age:
                maxAgePos=position;
                getSizeofAudience();
                break;

            case R.id.spinner_min_age:
                minAgePos=position;
                getSizeofAudience();
                break;

            case R.id.spinner_miles:
                milesPos=position;
                break;

            case R.id.spinner_winner:
                winnerPos=position;
                break;


        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }*/


//used to get size of the audiences size belongs to this criteria on selection gender, age,locality,borders areas etc.
   public void getSizeofAudience(){
       if (!connectionDetector.isConnectingToInternet()) {
           showMessage("Please check internet connection");
           return;
       }


       String ageStart = null, ageEnd = null;

       String radar = "national";
       if (nationalization == 1) {
           radar = "international";
       }
       StringBuilder  gender=new StringBuilder();
       if(checkbox_male.isChecked()){
           gender.append("male");

       }
       if(checkbox_female.isChecked()){
           if(gender.length()>0){
               gender.append(',').append("female");
           }else {
               gender.append("female");
           }

       }
       ageStart = edt_min_age.getText().toString();
       ageEnd = edt_max_age.getText().toString();

       RetrofitApis retrofitApis = RetrofitApis.Factory.create(this);
       Call<ResponseBody> call = retrofitApis.calculateAudience(radar, gender.toString(), ageStart, ageEnd);

       showProgressIndicator(true);
       call.enqueue(new Callback<ResponseBody>() {
           @Override
           public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
               showProgressIndicator(false);
               if (response.isSuccessful()) {
                   try {
                       JSONObject object = new JSONObject(response.body().string());
                       if (object.optInt("status") == 1) {
                           JSONObject size = object.getJSONObject("size");
                           String audienceSize = size.optString("size");
                           userSession.setAudSize(audienceSize);
                           audience_size.setText(audienceSize);
                           showMessage(object.optString("message"));
                       }

                   } catch (JSONException e) {
                       e.printStackTrace();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }

               }
           }

           @Override
           public void onFailure(Call<ResponseBody> call, Throwable t) {
               showProgressIndicator(false);

           }
       });

   }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        getSizeofAudience();
    }

    @Override
    public void onChallengeUpdated(Challenge result) {
       if(result!=null)
        this.userSession.saveJackpot(result);
        startActivity(new Intent(this,InviteFriendActivity.class));
    }
    @Override
    public void onEditView(int view, int position) {
        switch (view) {
            case R.id.edt_city:
                if(cities!=null)
                {
                    edt_city.setText(cities.get(position).getCityName());
                    city=cities.get(position);
                    city_pos=position;
                }
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
            case R.id.edt_region:
                if(regions!=null)
                {
                    edt_region.setText(regions.get(position).getRegName());
                    region_pos=position;
                }

                break;
            case R.id.edt_language:
                if(languages!=null)
                {
                    edt_language.setText(languages.get(position).getName());
                    languages_pos=position;
                }

                break;
            case R.id.edt_currency:
                if(currencies!=null)
                {
                    edt_currency.setText(currencies.get(position).getCode()+"("+currencies.get(position).getSymbol()+")");
                    currency_pos=position;
                }

                break;


        }
    }

    @Override
    public void onRegionList(List<Region> regions) {
       this.regions=regions;
    }

    @Override
    public void onLanguages(List<Language> languages) {
       this.languages=languages;
    }

    @Override
    public void onBackPressed() {
        showAlertDialog();
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.datepicker);
        // Add the buttons
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteChallenge();
                dialog.dismiss();
            }
        });
        // Add the buttons
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                dialog.dismiss();

            }
        });
        builder.setMessage("Do you want discard the changes");


// Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }

        private void deleteChallenge() {
                  if(userSession.getJackpot()!=null)
            RetrofitApis.Factory.create(this).deleteChallenge(userSession.getJackpot().getChallengeId(),userSession.getJackpot().getType());
            finish();
        }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(s.length()>0&&s.length()%2==0)
            getSizeofAudience();

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
