package com.android.shooshoo.presenters;

import com.android.shooshoo.models.CategoryList;
import com.android.shooshoo.models.CityResult;
import com.android.shooshoo.models.Country;
import com.android.shooshoo.models.CountryResult;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.views.DataLoadView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * {@link DataLoadPresenter} is used to load the common data like countries
 * ,cites from selected country and all categories and their available brands
 */
public class DataLoadPresenter implements BasePresenter<DataLoadView> {



    DataLoadView view;
    RetrofitApis retrofitApis;

    @Override
    public void attachView(DataLoadView view) {
        this.view=view;
        this.retrofitApis=RetrofitApis.Factory.create(view.getContext());
    }

    @Override
    public void detachView() {
         if(view!=null)
          view.showProgressIndicator(false);
        this.retrofitApis=null;
        this.view=null;

    }

    /**this is used to load countries data*/
    public void loadCountryData(){
        if(view!=null)
        view.showProgressIndicator(true);
        retrofitApis.getCountries().enqueue(new Callback<CountryResult>() {
            @Override
            public void onResponse(Call<CountryResult> call, Response<CountryResult> response) {
               if(view!=null){
                view.showProgressIndicator(false);
                if(response.isSuccessful()){

                    CountryResult countryResult=response.body();
                    if(countryResult.getStatus()==1)
                    {
                        List<Country> countries=countryResult.getCountries();
                        view.onCountryData(countries);
                    }else
                    view.showMessage(countryResult.getMessage());
                }}
            }

            @Override
            public void onFailure(Call<CountryResult> call, Throwable t) {
               if(view!=null) {
                   view.showProgressIndicator(false);
                   view.showMessage(t.getMessage());
               }

            }
        });

    }
   /**This is used to load cites data*/
    public void loadCites(String countryCode){
        if(view!=null)
        view.showProgressIndicator(true);
        retrofitApis.getCities(countryCode).enqueue(new Callback<CityResult>() {

            @Override
            public void onResponse(Call<CityResult> call, Response<CityResult> response) {
                if(view!=null)
                view.showProgressIndicator(false);
                if(response.isSuccessful()){
                    CityResult cityResult=response.body();

                    if(cityResult.getStatus()==1){
                        if(view!=null)
                        view.onCitiesData(cityResult.getCities());
                        //                    displayListCity(cityResult.getCities(),spinner_city);

                    }else if(cityResult.getStatus()==2)
                    { if(view!=null)
                        view.onCitiesData(null);
                    }
                    if(view!=null)
                        view.showMessage(cityResult.getMessage());


                }
            }

            @Override
            public void onFailure(Call<CityResult> call, Throwable t) {
                if(view!=null) {
                    view.showProgressIndicator(false);
                    view.showMessage(t.getMessage());
                }

            }
        });
    }
    /**This is used to load all categories and the brands*/
    public void loadAllcategoriesList(){
        if(view!=null)
        view.showProgressIndicator(true);
        retrofitApis.getAllCategories().enqueue(new Callback<CategoryList>() {
            @Override
            public void onResponse(Call<CategoryList> call, Response<CategoryList> response) {
                if(view!=null)
                view.showProgressIndicator(false);
                if(response.isSuccessful()){
                    CategoryList categoryList=response.body();
                    if(categoryList.getStatus()==1){
                        if(view!=null)
                        view.onAllCategories(categoryList.getCategories());
                        //                    displayListCity(cityResult.getCities(),spinner_city);

                    }else {
                        if(view!=null)
                        view.showMessage(categoryList.getMessage());
                    }


                }
            }

            @Override
            public void onFailure(Call<CategoryList> call, Throwable t) {
                if(view!=null){ view.showProgressIndicator(false);
                view.showMessage(t.getMessage());
            }
            }
        });

    }

}
