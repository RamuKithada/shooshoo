package com.android.shooshoo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
public class SponsorAudienceActivity extends BaseActivity implements DataLoadView,SponsorChallengeView, CompoundButton.OnCheckedChangeListener,View.OnClickListener,AdapterView.OnItemSelectedListener,FragmentListDialogListener {

    @BindView(R.id.btn_next)
    TextView btn_next;

    @BindView(R.id.iv_back)
    ImageView iv_back;

    @BindView(R.id.iv_help)
    ImageView iv_help;

    @BindViews({R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5})
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

    @BindView(R.id.edt_gender)
    AppCompatEditText edt_gender;

    @BindView(R.id.edt_min_age)
    AppCompatEditText edt_min_age;


    @BindView(R.id.edt_max_age)
    AppCompatEditText edt_max_age;




    @BindView(R.id.tv_price_total)
    AppCompatTextView tv_price_total;




    /** winners,miles,age are lists set them to FragmentDialog to show to choose one of the them.
     */




    DataLoadPresenter dataLoadPresenter;
    ConnectionDetector connectionDetector;
    CategorySelectionAdapter categorySelectionAdapter;

    SponsorChallengePresenter sponsorChallengePresenter;
    List<Category> categoryArrayList=new ArrayList<Category>();


    ArrayList<PrizeBaseModel> prices=new ArrayList<PrizeBaseModel>();
    PrizeListAdapter priceWorthAdapter=null;
    int total=0;
    RecyclerView.AdapterDataObserver  adapterDataObserver=new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
         /*   total=0;
            for (EditModel model:prices){
                try {
                    int price=Integer.valueOf(model.getEditTextValue());
                     total=total+price;
                }catch (Exception e){
                    e.printStackTrace();
                }
          }
          edt_price_total.setText(String.valueOf(total));*/
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor_audience);
        ButterKnife.bind(this);
        btn_next.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        btn_more_categories.setOnClickListener(this);
        title.setText("PRIZE & Outreach");
        iv_help.setVisibility(View.VISIBLE);
        tv_national.setBackgroundResource(R.drawable.cat_selected);
        tv_international.setBackgroundResource(R.drawable.cat_unselected);
        setStage(2);
        spinnersInti();
        categorySelectionAdapter=new CategorySelectionAdapter(this,categoryArrayList);
        priceWorthAdapter=new PrizeListAdapter(this,prices);
        priceWorthAdapter.registerAdapterDataObserver(adapterDataObserver);
        btn_more_prizes.setOnClickListener(this);
        rv_price_list.setAdapter(priceWorthAdapter);
        rv_price_list.setLayoutManager(new LinearLayoutManager(this));
        rv_price_list.setNestedScrollingEnabled(false);
                dataLoadPresenter=new DataLoadPresenter();
        dataLoadPresenter.attachView(this);
        sponsorChallengePresenter =new SponsorChallengePresenter();
        sponsorChallengePresenter.attachView(this);
        connectionDetector=new ConnectionDetector(this);

        et_prize_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomListFragmentDialog showFragment=new CustomListFragmentDialog();
                Bundle args = new Bundle();
                args.putStringArray("list",new String[]{"Cash","Product"});
                args.putInt("view",et_prize_type.getId());
                showFragment.setArguments(args);
                showFragment.show(getSupportFragmentManager(),"Prize Type");

            }
        });
/*        edt_price_worth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int value=Integer.valueOf(s.toString());
                    int mTotal=total+value;
                    edt_price_total.setText(String.valueOf(mTotal));
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
        if(connectionDetector.isConnectingToInternet()){
            dataLoadPresenter.loadAllcategoriesList();
        }



    }

    private void spinnersInti() {

        national_lay.setOnClickListener(radioClickListener);
        international_lay.setOnClickListener(radioClickListener);
    }

    int nationalization=0;
    View.OnClickListener radioClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.national_lay:
          tv_national.setTextColor(Color.parseColor("#FFFFFF"));
          tv_international.setTextColor(Color.parseColor("#85868A"));
          nationalization=0;
            break;
                case R.id.tv_international:
                    tv_international.setTextColor(Color.parseColor("#FFFFFF"));
                    tv_national.setTextColor(Color.parseColor("#85868A"));
          nationalization=1;
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
                    String radar="national";
                    if(nationalization==1){
                        radar="international";
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
/*                StringBuilder stringBuilder=new StringBuilder();
//                    JSONArray jsonArray=new JSONArray();
                for (int index=0;index<prices.size();index++){
                    EditModel model=prices.get(index);
                    if(index!=0){
                        stringBuilder.append(',').append(model.getEditTextValue());
                    }else stringBuilder.append(model.getEditTextValue());

                }
                    stringBuilder.append(',').append(edt_price_worth.getText().toString());*/

           StringBuilder cats=new StringBuilder();
                StringBuilder brands=new StringBuilder();
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
                   /*     if(categoryArrayList.get(categoryModel.getCategory()).getBrands()!=null)
                        if(categoryArrayList.get(categoryModel.getCategory()).getBrands().size()>categoryModel.getSubcategory()){
                            Brand brand=categoryArrayList.get(categoryModel.getCategory()).getBrands().get(categoryModel.getSubcategory());

                            if(cats.length()>0){
                                if(!cats.toString().contains(brand.getCategoryId()))
                                cats.append(',').append(brand.getCategoryId());
                            }else
                                cats.append(brand.getCategoryId());

                            if(brands.length()>0){
                                if(!brands.toString().contains(brand.getCategoryId()))
                                    brands.append(',').append(brand.getBrandId());
                            }else
                                brands.append(brand.getBrandId());

                        }*/
                    }

                    if(prices!=null){
                        if(prices.size()<0){
                            showMessage("");
                        }
                    }



                    if(!connectionDetector.isConnectingToInternet())
                    {
                        showMessage("Please check internet connection !");
                        return;
                    }/*
sponsorChallengePresenter.createAudience(userSession.getSponsorChallenge(),userSession.getUserId(),edt_amount.getText().toString(),edt_key_des.getText().toString(),
        stringBuilder.toString(),edt_price_total.getText().toString(),winners.get(winnerPos),radar,
        edt_zipcode.getText().toString(),miles.get(milesPos),edt_address.getText().toString(),cats.toString(),brands.toString()
        ,age.get(minAgePos),age.get(maxAgePos),gender.toString());*/
        }

//
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_more_categories:
                categorySelectionAdapter.add();
                break;
            case R.id.btn_more_prizes:
                if(priceWorthAdapter.getItemCount()<4) {

                    PrizeBaseModel prizeBaseModel=null;
                    if(prizetype==0)
                        {
                        CashModel cashModel=new CashModel();
                        cashModel.setAmount(et_cash_amount.getText().toString());
                        cashModel.setCurrency(et_cash_currency.getText().toString());
                        prizeBaseModel=cashModel;
                        }
                    else
                        {
                        PrizeModel  prizeModel=new PrizeModel();
                        prizeModel.setName(et_prize_name.getText().toString());
                        prizeModel.setWorth(et_prize_worth.getText().toString());
                        prizeModel.setNumber(et_prize_number.getText().toString());
                        prizeBaseModel=prizeModel;
                        }
                    if(prizeBaseModel!=null)
                    priceWorthAdapter.addItem(prizeBaseModel);
                    et_prize_type.setText("Cash");
                    cash_lay.setVisibility(View.VISIBLE);
                    et_cash_amount.setText(null);
                    et_cash_currency.setText(null);
                    et_prize_name.setText(null);
                    et_prize_number.setText(null);
                    et_prize_worth.setText(null);

                }
                break;

        }
    }

    private boolean validate() {
        if(!ApiUrls.validateString(edt_amount.getText().toString())){
            edt_amount.setError("Enter amount");
            edt_amount.requestFocus();
            return false;
        }
        if(!ApiUrls.validateString(edt_key_des.getText().toString())){
            edt_key_des.setError("Enter Key Description");
            edt_key_des.requestFocus();
            return false;
        }
     /*   if(!ApiUrls.validateString(edt_price_worth.getText().toString())) {
            edt_price_worth.setError("Enter price");
            edt_price_worth.requestFocus();
            return false;
        }*/

        if(!ApiUrls.validateString(edt_price_total.getText().toString())) {
            edt_price_total.setError("Enter Total price");
            edt_price_total.requestFocus();
            return false;
        }
        if(!ApiUrls.validateString(edt_zipcode.getText().toString())) {
            edt_zipcode.setError("Enter ZipCode");
            edt_zipcode.requestFocus();
            return false;
        }

        if(!ApiUrls.validateString(edt_address.getText().toString())) {
            edt_address.setError("Enter Personal Address");
            edt_address.requestFocus();
            return false;
        }

        if(minAgePos>=maxAgePos){
            showMessage("Minimum age must be less the Maximum age");
            return false;
        }

        if(!checkbox_female.isChecked()&&!checkbox_male.isChecked()){
           showMessage("Select atleast one gender ");
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
            if(index==step){
                {
                    buttons.get(index).setBackgroundResource(R.drawable.selected);
                }
            }else buttons.get(index).setBackgroundResource(R.drawable.unselected);

        }
    }

    @Override
    public void onCitiesData(List<City> cities) {

    }

    @Override
    public void onCountryData(List<Country> countries) {

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

    int winnerPos=0,maxAgePos=0,minAgePos=0,milesPos=0;
    @Override
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

    }

    @Override
    public void onCompanyRegister(Company company) {

    }

    @Override
    public void onChallengeResponse(Challenge company) {
        this.userSession.setSponsorChallenge(company.getChallengeId());
        this.userSession.savaChallenge(company);
        startActivity(new Intent(this,CampaignActivity.class));
    }

   public void getSizeofAudience(){
        if(!connectionDetector.isConnectingToInternet())
        {
            showMessage("Please check internet connection");
            return;
        }


        String ageStart=null,ageEnd=null;

       String radar="national";
       if(nationalization==1){
           radar="international";
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
       ageStart=age.get(minAgePos).toLowerCase().replace("years"," ");
       ageEnd=age.get(maxAgePos).toLowerCase().replace("years"," ");

       RetrofitApis retrofitApis=RetrofitApis.Factory.create(this);
       Call<ResponseBody>  call=retrofitApis.calculateAudience(radar,gender.toString(),ageStart,ageEnd);

       showProgressIndicator(true);
       call.enqueue(new Callback<ResponseBody>() {
           @Override
           public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
               showProgressIndicator(false);
               if(response.isSuccessful()){
                   try {
                       JSONObject object=new JSONObject(response.body().string());
                       if(object.optInt("status")==1){
                           JSONObject size=object.getJSONObject("size");
                          String audienceSize=size.optString("size");
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

    int prizetype=0;
    @Override
    public void onEditView(int view, int position) {
        if(view==R.id.et_prize_type){
            if(position==0)
            {
                prizetype=0;
                et_prize_type.setText("Cash");
                cash_lay.setVisibility(View.VISIBLE);
                product_lay.setVisibility(View.GONE);


            }
            else
            {
                prizetype=1;
                et_prize_type.setText("Product");
                cash_lay.setVisibility(View.GONE);
                product_lay.setVisibility(View.VISIBLE);
            }
        }

    }
}
