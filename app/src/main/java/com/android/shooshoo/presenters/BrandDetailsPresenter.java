package com.android.shooshoo.presenters;

import com.android.shooshoo.models.CompanyDetails;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.views.BrandProfileView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrandDetailsPresenter implements BasePresenter<BrandProfileView>{
    BrandProfileView view;
    RetrofitApis retrofitApis;
    @Override
    public void attachView(BrandProfileView view) {
            this.view=view;
            retrofitApis=RetrofitApis.Factory.create(view.getContext());
        }

        @Override
        public void detachView() {
        if(view!=null)
            view.showProgressIndicator(false);
            if(companyDetailsCall!=null)
                if(companyDetailsCall.isCanceled())
                      companyDetailsCall.cancel();

            this.view=null;
            retrofitApis=null;
        }
    Call<CompanyDetails> companyDetailsCall=null;
        public void getBrandDetails(String companyId){
        if(view!=null)
            view.showProgressIndicator(true);

         companyDetailsCall=retrofitApis.getCompanyDetails(companyId);
            companyDetailsCall.enqueue(new Callback<CompanyDetails>() {
            @Override
            public void onResponse(Call<CompanyDetails> call, Response<CompanyDetails> response) {
                if(view!=null)
                    view.showProgressIndicator(false);
                if(response.isSuccessful()){
                    if(view!=null)
                        view.onBrandDetails(response.body());

                }


            }

            @Override
            public void onFailure(Call<CompanyDetails> call, Throwable t) {
                if(view!=null)
                {
                    view.showProgressIndicator(false);
                    view.showMessage(t.getMessage());
                }
            }
        });


        }
        public void saveBrand(String userId,String companyId){
            if(view!=null)
                view.showProgressIndicator(true);
           retrofitApis.saveBrand(userId,companyId)
            .enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(view!=null)
                        view.showProgressIndicator(false);
                    if(response.isSuccessful()){
                        if(view!=null)
                        {
                            try {
                                JSONObject object = new JSONObject(response.body().string());
                                    view.showMessage(object.optString("message"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                    }


                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if(view!=null)
                    {
                        view.showProgressIndicator(false);
                        view.showMessage(t.getMessage());
                    }
                }
            });

        }
}
