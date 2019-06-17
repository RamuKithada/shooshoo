package com.android.shooshoo.presenters;

import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.views.BaseView;
import com.android.shooshoo.views.UpdateUserInfoView;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/****
 * {@link UpdateUserInfoPresenter} is used to update the categories and brands to server
 */
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

    /**
     * to update the categories to server
     * @param userId user id
     * @param catList categories list that are selected by user
     */
    public void updateUserCat(String userId,String catList){
        if(view!=null)
        view.showProgressIndicator(true);
        retrofitApis.updateUserCat(userId,catList).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(view!=null)
                view.showProgressIndicator(false);
                if(response.isSuccessful()){
                    if(view!=null)
                    view.onUpdateUserInfo(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(view!=null){  view.showProgressIndicator(false);
                view.showMessage(t.getMessage());
                }

            }
        });

    }

    /**
     * to update the brands to server
     * @param userId is user id
     * @param brandList ids of the brands
     */

    public void updateUserBrand(String userId,String brandList){
        if(view!=null)
        view.showProgressIndicator(true);
        retrofitApis.updateUserBrand(userId,brandList).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(view!=null)
                view.showProgressIndicator(false);
                if(response.isSuccessful()){
                    if(view!=null)
                    view.onUpdateUserInfo(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(view!=null){
                    view.showProgressIndicator(false);
                    view.showMessage(t.getMessage());
                }

            }
        });

    }



}
