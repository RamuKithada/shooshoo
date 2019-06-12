package com.android.shooshoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.shooshoo.R;
import com.android.shooshoo.adapter.CategorySelectionAdapter;
import com.android.shooshoo.models.Brand;
import com.android.shooshoo.models.Category;
import com.android.shooshoo.models.CategoryModel;
import com.android.shooshoo.models.City;
import com.android.shooshoo.models.Country;
import com.android.shooshoo.models.GameMaster;
import com.android.shooshoo.presenters.DataLoadPresenter;
import com.android.shooshoo.presenters.JackpotChallengePresenter;
import com.android.shooshoo.utils.ApiUrls;
import com.android.shooshoo.utils.ConnectionDetector;
import com.android.shooshoo.utils.RetrofitApis;
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
public class JackpotAudienceActivity extends BaseActivity implements DataLoadView, JackpotChallengeView,CompoundButton.OnCheckedChangeListener,View.OnClickListener,AdapterView.OnItemSelectedListener{

    @BindView(R.id.btn_next)
    TextView btn_next;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindViews({R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5})
    List<Button> buttons;

    @BindView(R.id.tv_title)
    TextView title;

    @BindView(R.id.edt_amount)
    EditText edt_amount;

    @BindView(R.id.edt_key_des)
    EditText edt_key_des;

    @BindView(R.id.edt_price_worth)
    EditText edt_price_worth;

    @BindView(R.id.edt_limited_access)
    EditText edt_limited_access;

    @BindView(R.id.edt_zipcode)
    EditText edt_zipcode;

    @BindView(R.id.tv_national)
    RelativeLayout tv_national;

    @BindView(R.id.tv_international)
    RelativeLayout tv_international;

    @BindView(R.id.spinner_winner)
    Spinner spinner_winner;

    @BindView(R.id.spinner_miles)
    Spinner spinner_miles;

    @BindView(R.id.spinner_min_age)
    Spinner spinner_min_age;

    @BindView(R.id.spinner_max_age)
    Spinner spinner_max_age;


    @BindView(R.id.edt_address)
    EditText edt_address;

    @BindView(R.id.cat_subcat_list)
    RecyclerView cat_subcat_list;

    @BindView(R.id.btn_more_categories)
    TextView btn_more_categories;

    @BindView(R.id.checkbox_male)
    CheckBox checkbox_male;

    @BindView(R.id.checkbox_female)
    CheckBox checkbox_female;



    @BindView(R.id.audience_size)
    TextView audience_size;

    ArrayList<String> winners=new ArrayList<String>();
    ArrayList<String> miles=new ArrayList<String>();
    ArrayList<String> age=new ArrayList<String>();

    DataLoadPresenter dataLoadPresenter;
    ConnectionDetector connectionDetector;
    CategorySelectionAdapter categorySelectionAdapter;

    JackpotChallengePresenter jackpotChallengePresenter;
    List<Category> categoryArrayList=new ArrayList<Category>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jackpot_audience);
        ButterKnife.bind(this);
        btn_next.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        btn_more_categories.setOnClickListener(this);
        title.setText("Audience");
        tv_national.setBackgroundResource(R.drawable.cat_selected);
        tv_international.setBackgroundResource(R.drawable.cat_unselected);
        setStage(2);
        spinnersInti();
        categorySelectionAdapter=new CategorySelectionAdapter(this,categoryArrayList);
        dataLoadPresenter=new DataLoadPresenter();
        dataLoadPresenter.attachView(this);
        jackpotChallengePresenter =new JackpotChallengePresenter();
        jackpotChallengePresenter.attachView(this);
        connectionDetector=new ConnectionDetector(this);
        checkbox_male.setOnCheckedChangeListener(this);
        checkbox_female.setOnCheckedChangeListener(this);

        if(connectionDetector.isConnectingToInternet()){
            dataLoadPresenter.loadAllcategoriesList();
        }



    }

    private void spinnersInti() {

        for(int i=1;i<=10;i++){
            winners.add("Top "+i);
            miles.add(i+" Miles");
        }
        for(int i=18;i<60;i++)
            age.add(i+" Years");

        ArrayAdapter<String> winnerAdapter = new ArrayAdapter<String>(this, R.layout.spinnet_text, winners);
        winnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_winner.setAdapter(winnerAdapter);
        spinner_winner.setOnItemSelectedListener(this);

        ArrayAdapter<String> ageAdapter = new ArrayAdapter<String>(this, R.layout.spinnet_text, age);
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_min_age.setAdapter(ageAdapter);
        spinner_min_age.setOnItemSelectedListener(this);

        spinner_max_age.setAdapter(ageAdapter);
        spinner_max_age.setOnItemSelectedListener(this);

        ArrayAdapter<String> milesAdapter = new ArrayAdapter<String>(this, R.layout.spinnet_text, miles);
        milesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_miles.setAdapter(milesAdapter);
        spinner_miles.setOnItemSelectedListener(this);

        tv_national.setOnClickListener(radioClickListener);
        tv_international.setOnClickListener(radioClickListener);
    }

    int nationalization=0;
    View.OnClickListener radioClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.tv_national:
          tv_national.setBackgroundResource(R.drawable.cat_selected);
          tv_international.setBackgroundResource(R.drawable.cat_unselected);
          nationalization=0;
            break;
                case R.id.tv_international:
          tv_national.setBackgroundResource(R.drawable.cat_unselected);
          tv_international.setBackgroundResource(R.drawable.cat_selected);
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

           StringBuilder cats=new StringBuilder();
                StringBuilder brands=new StringBuilder();
                    for (CategoryModel categoryModel: categorySelectionAdapter.getCategoryModels()) {
                        if(categoryArrayList.size()>categoryModel.getCategory())
                        if(categoryArrayList.get(categoryModel.getCategory()).getBrands()!=null)
                        if(categoryArrayList.get(categoryModel.getCategory()).getBrands().size()>categoryModel.getSubcategory()){
                            Brand brand=categoryArrayList.get(categoryModel.getCategory()).getBrands().get(categoryModel.getSubcategory());

                            if(cats.length()>0){
                                cats.append(',').append(brand.getCategoryId());
                            }else
                                cats.append(brand.getCategoryId());

                            if(brands.length()>0){
                                brands.append(',').append(brand.getBrandId());
                            }else
                                brands.append(brand.getBrandId());

                        }
                    }

                    if(!connectionDetector.isConnectingToInternet())
                    {
                        showMessage("Please check internet connection !");
                        break;
                    }
jackpotChallengePresenter.createAudience(userSession.getSponsorChallenge(),userSession.getUserId(),edt_amount.getText().toString(),
        edt_key_des.getText().toString(),edt_price_worth.getText().toString(), edt_limited_access.getText().toString(),winners.get(winnerPos),radar,
        edt_zipcode.getText().toString(),miles.get(milesPos),edt_address.getText().toString(),cats.toString(),brands.toString()
        ,age.get(minAgePos),age.get(maxAgePos),gender.toString());
        }

//
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_more_categories:
                categorySelectionAdapter.add();
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
        if(!ApiUrls.validateString(edt_price_worth.getText().toString())) {
            edt_price_worth.setError("Enter price");
            edt_price_worth.requestFocus();
            return false;
        }

        if(!ApiUrls.validateString(edt_limited_access.getText().toString())) {
            edt_limited_access.setError("Enter Limited access");
            edt_limited_access.requestFocus();
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
                    buttons.get(index).setText(String.valueOf(step+1));
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


//used to get size of the audiences size belongs to this criteria on selection gender, age,locality,borders areas etc.
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
    public void onGameMasterCreate(GameMaster result) {
        this.userSession.setSponsorChallenge(result.getChallengeId());
        this.userSession.saveGameMaster(result);
        startActivity(new Intent(this,InviteFriendActivity.class));
    }
}
