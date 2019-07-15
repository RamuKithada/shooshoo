package com.android.shooshoo.presenters;

import com.android.shooshoo.models.CompanyDetails;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.views.BrandProfileView;

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
        public void getBrandDetails(String comanyId){
        if(view!=null)
            view.showProgressIndicator(true);

         companyDetailsCall=retrofitApis.getCompanyDetails(comanyId);
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
}
