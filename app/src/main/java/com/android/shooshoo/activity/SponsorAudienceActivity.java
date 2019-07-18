package com.android.shooshoo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.android.shooshoo.adapter.CategorySelectionAdapter;
import com.android.shooshoo.adapter.PrizeListAdapter;
import com.android.shooshoo.models.Brand;
import com.android.shooshoo.models.CashModel;
import com.android.shooshoo.models.Category;
import com.android.shooshoo.models.CategoryModel;
import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.City;
import com.android.shooshoo.models.Company;
import com.android.shooshoo.models.Country;
import com.android.shooshoo.models.PrizeBaseModel;
import com.android.shooshoo.models.PrizeModel;
import com.android.shooshoo.presenters.DataLoadPresenter;
import com.android.shooshoo.presenters.SponsorChallengePresenter;
import com.android.shooshoo.utils.ApiUrls;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.CustomListFragmentDialog;
import com.android.shooshoo.utils.FragmentListDialogListener;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.views.DataLoadView;
import com.android.shooshoo.views.SponsorChallengeView;

import org.json.JSONArray;
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
/**
 * This {@link SponsorAudienceActivity} is used to show the sponsor challenge target Audience selection functionality
 * button1,button2,button3,button3,button4 are used to show step of the registration. and tv_skip,iv_back are to represent back and skip buttons of layout.
 */
public class SponsorAudienceActivity extends BaseActivity implements DataLoadView,TextWatcher,CompoundButton.OnCheckedChangeListener,SponsorChallengeView,View.OnClickListener,FragmentListDialogListener {

    @BindView(R.id.btn_next)
    TextView btn_next;

    @BindView(R.id.iv_back)
    ImageView iv_back;

    @BindView(R.id.iv_help)
    ImageView iv_help;

    @BindViews({R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5})
    List<Button> buttons;

    @BindView(R.id.tv_title)
    TextView title;

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


    @BindView(R.id.rv_price_list)
    RecyclerView rv_price_list;

    @BindView(R.id.btn_more_prizes)
    ImageView btn_more_prizes;

    @BindView(R.id.audience_size)
    AppCompatTextView audience_size;

    @BindView(R.id.product_lay)
    LinearLayout product_lay;

    @BindView(R.id.cash_lay)
    LinearLayout cash_lay;

    @BindView(R.id.et_prize_type)
    AppCompatEditText et_prize_type;
    @BindView(R.id.et_cash_amount)
    AppCompatEditText et_cash_amount;
    @BindView(R.id.et_cash_currency)
    AppCompatEditText et_cash_currency;
    @BindView(R.id.et_cash_number)
    AppCompatEditText et_cash_number;
    @BindView(R.id.et_prize_name)
    AppCompatEditText et_prize_name;
    @BindView(R.id.et_prize_worth)
    AppCompatEditText et_prize_worth;
    @BindView(R.id.et_prize_number)
    AppCompatEditText et_prize_number;

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

    @BindView(R.id.checkbox_male)
    CheckBox checkbox_male;

    @BindView(R.id.checkbox_female)
    CheckBox checkbox_female;

    @BindView(R.id.edt_min_age)
    AppCompatEditText edt_min_age;


    @BindView(R.id.edt_max_age)
    AppCompatEditText edt_max_age;


    @BindView(R.id.tv_price_total)
    AppCompatTextView tv_price_total;


    /**
     * winners,miles,age are lists set them to FragmentDialog to show to choose one of the them.
     */


    DataLoadPresenter dataLoadPresenter;
    ConnectionDetector connectionDetector;
    CategorySelectionAdapter categorySelectionAdapter;

    SponsorChallengePresenter sponsorChallengePresenter;
    List<Category> categoryArrayList = new ArrayList<Category>();
    List<Country> countries=new ArrayList<Country>();
    List<City> cities=new ArrayList<City>();
    Country country;
    City city;
    int city_pos=-1,country_pos=-1;



    ArrayList<PrizeBaseModel> prices = new ArrayList<PrizeBaseModel>();
    PrizeListAdapter priceWorthAdapter = null;
    double total = 0;
    RecyclerView.AdapterDataObserver adapterDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            total=0;
            for (PrizeBaseModel model:prices){
                try {


                     double price=model.getPrizeAmount();
                     total=total+price;
                }catch (Exception e){
                    e.printStackTrace();
                }
          }
          tv_price_total.setText(String.valueOf(total));
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor_audience);
        ButterKnife.bind(this);
        btn_next.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        iv_help.setOnClickListener(this);
        checkbox_male.setOnCheckedChangeListener(this);
        checkbox_female.setOnCheckedChangeListener(this);
        edt_min_age.addTextChangedListener(this);
        edt_max_age.addTextChangedListener(this);
        edt_miles.addTextChangedListener(this);
        btn_more_categories.setOnClickListener(this);
        title.setText("PRIZE & Outreach");
        setStage(2);
        spinnersInti();
        categorySelectionAdapter = new CategorySelectionAdapter(this, categoryArrayList);
        priceWorthAdapter = new PrizeListAdapter(this, prices);
        priceWorthAdapter.registerAdapterDataObserver(adapterDataObserver);
        btn_more_prizes.setOnClickListener(this);
        rv_price_list.setAdapter(priceWorthAdapter);
        rv_price_list.setLayoutManager(new LinearLayoutManager(this));
        rv_price_list.setNestedScrollingEnabled(false);
        dataLoadPresenter = new DataLoadPresenter();
        dataLoadPresenter.attachView(this);
        sponsorChallengePresenter = new SponsorChallengePresenter();
        sponsorChallengePresenter.attachView(this);
        connectionDetector = new ConnectionDetector(this);

        et_prize_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomListFragmentDialog showFragment = new CustomListFragmentDialog();
                Bundle args = new Bundle();
                args.putStringArray("list", new String[]{"Cash", "Product"});
                args.putInt("view", et_prize_type.getId());
                showFragment.setArguments(args);
                showFragment.show(getSupportFragmentManager(), "Prize Type");

            }
        });
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


        et_cash_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int number=1;
                    if(ApiUrls.validateString(s.toString())&&s.length()>0)
                       number= Integer.valueOf(s.toString());
                    int value=0;
                    if(ApiUrls.validateString(et_cash_amount.getText().toString())&&et_cash_amount.getText().length()>0)
                        value =Integer.valueOf(et_cash_amount.getText().toString());
                    double mTotal=total+value*number;
                    tv_price_total.setText(String.valueOf(mTotal));
                    tv_price_total.setText(String.valueOf(mTotal));
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_prize_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int number=1;
                    if(ApiUrls.validateString(s.toString())&&s.length()>0)
                        number=Integer.valueOf(s.toString());
                    int value=0;
                    if(ApiUrls.validateString(et_prize_worth.getText().toString())&&et_prize_worth.getText().length()>0)
                        value =Integer.valueOf(et_prize_worth.getText().toString());
                    double mTotal=total+value*number;
                    tv_price_total.setText(String.valueOf(mTotal));
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_prize_worth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int value=0;
                    if(ApiUrls.validateString(s.toString())&&s.length()>0)
                        value=Integer.valueOf(s.toString());
                    int number=1;
                    if(ApiUrls.validateString(et_prize_number.getText().toString())&&et_prize_number.getText().length()>0)
                        number =Integer.valueOf(et_prize_number.getText().toString());
                    double mTotal=total+value*number;
                    tv_price_total.setText(String.valueOf(mTotal));
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_cash_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int value=0;
                    if(ApiUrls.validateString(s.toString())&&s.length()>0)
                       value=Integer.valueOf(s.toString());
                    int number=1;
                    if(ApiUrls.validateString(et_cash_number.getText().toString())&&et_cash_number.getText().length()>0)
                        number =Integer.valueOf(et_cash_number.getText().toString());
                    double mTotal=total+value*number;
                    tv_price_total.setText(String.valueOf(mTotal));
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        if (connectionDetector.isConnectingToInternet()) {
            dataLoadPresenter.loadAllcategoriesList();
            dataLoadPresenter.loadCountryData();
        }


    }

    private void spinnersInti() {
        national_lay.setOnClickListener(radioClickListener);
        international_lay.setOnClickListener(radioClickListener);
    }

    int nationalization = 0;
    View.OnClickListener radioClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.national_lay:
                    tv_national.setTextColor(Color.parseColor("#FFFFFF"));
                    tv_international.setTextColor(Color.parseColor("#85868A"));
                    nationalization = 0;
                    break;
                case R.id.international_lay:
                    tv_international.setTextColor(Color.parseColor("#FFFFFF"));
                    tv_national.setTextColor(Color.parseColor("#85868A"));
                    nationalization = 1;
                    break;
            }

            getSizeofAudience();
        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_next:
                if (validate()) {
                    String radar = "national";
                    if (nationalization == 1) {
                        radar = "international";
                    }
                    StringBuilder gender = new StringBuilder();
                    JSONArray jsonArray=new JSONArray();
                for (int index=0;index<prices.size();index++){
                    jsonArray.put(putPrize(prices.get(index)));
                         }
                PrizeBaseModel prizeBaseModel=addPrize();
                  if(prizeBaseModel!=null){
                      jsonArray.put(putPrize(prizeBaseModel));
                  }


                    StringBuilder cats = new StringBuilder();
                    for (CategoryModel categoryModel : categorySelectionAdapter.getCategoryModels()) {
                        if (categoryArrayList.size() > categoryModel.getCategory()) {
                            Category mCategory = categoryArrayList.get(categoryModel.getCategory());
                            if (cats.length() > 0) {
                                if (mCategory.getCategoryId() != null && !cats.toString().contains(mCategory.getCategoryId()))
                                    cats.append(',').append(mCategory.getCategoryId());
                            } else {
                                if (mCategory.getCategoryId() != null && !cats.toString().contains(mCategory.getCategoryId()))
                                    cats.append(mCategory.getCategoryId());
                            }
                        }

                    }

                    if (prices != null) {
                        if (prices.size() < 0) {
                            showMessage("Please Announce prizes");
                        }
                    }


                    if (!connectionDetector.isConnectingToInternet()) {
                        showMessage("Please check internet connection !");
                        return;
                    }
                sponsorChallengePresenter.createAudience(userSession.getSponsorChallenge(),userSession.getUserId(),
                    jsonArray.toString(),tv_price_total.getText().toString(),no_of_winners.getText().toString(),radar,
                    edt_zipcode.getText().toString(),edt_miles.getText().toString(),countries.get(country_pos).getCountryId(),cities.get(city_pos).getCityId(),cats.toString(),
                    edt_min_age.getText().toString(),edt_max_age.getText().toString(),gender.toString());
                }
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_help:
                showMessage("Help");
                break;
            case R.id.btn_more_categories:
                List<CategoryModel> models=categorySelectionAdapter.getCategoryModels();
                for (int index=0;index<models.size();index++){
                    CategoryModel model=models.get(index);
                    if(model.getCategory()<=0){
                        showMessage("please select category at "+(index+1));
                        return;
                    }

                }

                categorySelectionAdapter.add();
                break;
            case R.id.btn_more_prizes:
                if (priceWorthAdapter.getItemCount() < 4) {
                    PrizeBaseModel prizeBaseModel = addPrize();
                    if (prizeBaseModel != null) {
                        priceWorthAdapter.addItem(prizeBaseModel);
                        et_prize_type.setText("Cash");
                        prizetype=0;
                        cash_lay.setVisibility(View.VISIBLE);
                        product_lay.setVisibility(View.GONE);
                        et_cash_amount.setText(null);
                        et_cash_currency.setText(null);
                        et_cash_number.setText(null);
                        et_prize_name.setText(null);
                        et_prize_number.setText(null);
                        et_prize_worth.setText(null);
                    }

                }
                break;

        }
    }

    private PrizeBaseModel addPrize() {
        PrizeBaseModel prizeBaseModel=null;
        if (prizetype == 0) {
            CashModel cashModel = new CashModel();
            if(!ApiUrls.validateString(et_cash_amount.getText().toString())){
                showMessage("Please enter amount");
                return prizeBaseModel;
            }

            cashModel.setAmount(et_cash_amount.getText().toString());
            if(!ApiUrls.validateString(et_cash_currency.getText().toString())){
                showMessage("Please enter Currency");
                return prizeBaseModel;
            }
            cashModel.setCurrency(et_cash_currency.getText().toString());
            if(!ApiUrls.validateString(et_cash_number.getText().toString())){
                showMessage("Please enter number");
                return prizeBaseModel;
            }
            cashModel.setNumber(et_cash_number.getText().toString());
            prizeBaseModel = cashModel;
        } else {
            PrizeModel prizeModel = new PrizeModel();
            if(!ApiUrls.validateString(et_prize_name.getText().toString())){
                showMessage("Please enter Product Name");
                return prizeBaseModel;
            }
            prizeModel.setName(et_prize_name.getText().toString());
            if(!ApiUrls.validateString(et_prize_worth.getText().toString())){
                showMessage("Please enter Product worth");
                return prizeBaseModel;
            }
            prizeModel.setWorth(et_prize_worth.getText().toString());
            if(!ApiUrls.validateString(et_prize_number.getText().toString())){
                showMessage("Please enter number");
                return prizeBaseModel;
            }
            prizeModel.setNumber(et_prize_number.getText().toString());
            prizeBaseModel = prizeModel;
        }
      return prizeBaseModel;
    }

    private JSONObject putPrize(PrizeBaseModel model) {
        JSONObject object=new JSONObject();
        try {
            if(model instanceof CashModel){
                CashModel cashModel= (CashModel) model;
                object.accumulate("type",cashModel.getType());
                object.accumulate("total",""+cashModel.getPrizeAmount());
                object.accumulate("amount",""+cashModel.getAmount());
                object.accumulate("number",""+cashModel.getNumber());
                object.accumulate("description","");
            }else if(model instanceof PrizeModel){
                PrizeModel prizeModel= (PrizeModel) model;
                object.accumulate("type",prizeModel.getType());
                object.accumulate("total",""+prizeModel.getPrizeAmount());
                object.accumulate("amount",""+prizeModel.getWorth());
                object.accumulate("number",""+prizeModel.getNumber());
                object.accumulate("description",""+prizeModel.getName());

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
     return object;
    }

    private boolean validate() {
            if(priceWorthAdapter.getItemCount()<=0){
              showMessage("Please Prizes Description to winners");
              return false;
                }

          if(!ApiUrls.validateString(no_of_winners.getText().toString())){
              showMessage("Enter number of winners");
              no_of_winners.requestFocus();
            return false;
        }
    if(country_pos<0){
        showMessage("Please select Country");
        edt_country.requestFocus();
        return false;
    }
         if(!ApiUrls.validateString(edt_zipcode.getText().toString())) {
             edt_zipcode.setError("Enter Zip Code ");
             edt_zipcode.requestFocus();
            return false;
        }

    if(city_pos<0){
        showMessage("Please select City");
        edt_city.requestFocus();
        return false;
    }

    if(!ApiUrls.validateString(edt_miles.getText().toString())) {
        edt_miles.setError("Enter miles for challenge range");
        edt_miles.requestFocus();
            return false;
        }

   if(categorySelectionAdapter.getItemCount()<3){
       showMessage("Please select at least 3 categories");
       return false;
   }
   List<CategoryModel> models=categorySelectionAdapter.getCategoryModels();
     for (int index=0;index<models.size();index++){
         CategoryModel model=models.get(index);
         if(model.getCategory()<=0){
             showMessage("please select category at "+index+1);
             return false;
         }

     }

        if (!ApiUrls.validateString(edt_zipcode.getText().toString())) {
            edt_zipcode.setError("Enter ZipCode");
            edt_zipcode.requestFocus();
            return false;
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

       }



        if(!checkbox_female.isChecked()&&!checkbox_male.isChecked()){
           showMessage("Select atleast one gender ");
           return false;
        }

        return true;
    }

    /**
     * setStage is for selection one of registration step
     *
     * @param step is step of registration process of a challenge
     */
    private void setStage(int step) {
        for (int index = 0; index < buttons.size(); index++) {
            if (index <= step) {
                {
                    buttons.get(index).setBackgroundResource(R.drawable.selected);
                }
            } else buttons.get(index).setBackgroundResource(R.drawable.unselected);

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

//    int winnerPos = 0, maxAgePos = 0, minAgePos = 0, milesPos = 0;

   /* @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner_max_age:
                maxAgePos = position;
                getSizeofAudience();
                break;

            case R.id.spinner_min_age:
                minAgePos = position;
                getSizeofAudience();
                break;

            case R.id.spinner_miles:
                milesPos = position;
                break;

            case R.id.spinner_winner:
                winnerPos = position;
                break;


        }


    } */
    @Override
    public void onCompanyRegister(Company company) {

    }




    @Override
    public void onChallengeResponse(Challenge challenge) {
        this.userSession.setSponsorChallenge(challenge.getChallengeId());
        this.userSession.savaChallenge(challenge);
        Intent intent=new Intent(this, CampaignActivity.class);
        intent.putExtra("challenge",challenge);
        startActivity(intent);
    }

    public void getSizeofAudience() {
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

    int prizetype = 0;
    @Override
    public void onEditView(int view, int position) {
        switch (view) {
            case R.id.et_prize_type:
                if (position == 0) {
                    prizetype = 0;
                    et_prize_type.setText("Cash");
                    cash_lay.setVisibility(View.VISIBLE);
                    product_lay.setVisibility(View.GONE);
                } else {
                    prizetype = 1;
                    et_prize_type.setText("Product");
                    cash_lay.setVisibility(View.GONE);
                    product_lay.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.edt_city:
                if(cities!=null)
                {
                    edt_city.setText(cities.get(position).getCityName());
                    edt_city.setError(null);
                    city=cities.get(position);
                    city_pos=position;
                }
                break;
            case R.id.edt_country:
                if(countries!=null)
                {
                    dataLoadPresenter.loadCites(countries.get(position).getCountryId());
                    edt_country.setText(countries.get(position).getCountryName());
                    edt_city.setError(null);
                    country_pos=position;
                    country=countries.get(position);
                }

                break;

        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        getSizeofAudience();

    }
}