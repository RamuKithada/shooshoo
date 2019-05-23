package com.android.shooshoo.presenters;

import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.views.BaseView;
import com.android.shooshoo.views.UpdateUserInfoView;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUserInfoPresenter implements BasePresenter<UpdateUserInfoView> {
    UpdateUserInfoView view;
    RetrofitApis retrofitApis;

    @Override
    public void attachView(UpdateUserInfoView view) {
        this.view=view;
        retrofitApis=RetrofitApis.Factory.create(view.getContext());
    }

    @Override
    public void detachView() {
       this.view=null;
       retrofitApis=null;
    }

    public void updateUserCat(String userId,String catList){
        view.showProgressIndicator(true);
        retrofitApis.updateUserCat(userId,catList).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showProgressIndicator(false);
                if(response.isSuccessful()){
                    view.onUpdateUserInfo(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.showProgressIndicator(false);
                view.showMessage(t.getMessage());

            }
        });

    }
    public void updateUserBrand(String userId,String brandList){
        view.showProgressIndicator(true);
        retrofitApis.updateUserBrand(userId,brandList).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showProgressIndicator(false);
                if(response.isSuccessful()){
                    view.onUpdateUserInfo(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.showProgressIndicator(false);
                view.showMessage(t.getMessage());

            }
        });

    }



}
