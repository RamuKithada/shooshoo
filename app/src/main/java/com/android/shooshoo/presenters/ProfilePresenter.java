package com.android.shooshoo.presenters;
import com.android.shooshoo.models.ProfileResponse;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.views.ProfileView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter implements BasePresenter<ProfileView>{
    ProfileView view;
    private RetrofitApis retrofitApis;
    @Override
    public void attachView(ProfileView view) {
        this.view=view;
        retrofitApis= RetrofitApis.Factory.create(view.getContext());

    }

    @Override
    public void detachView() {
        if(view!=null)
        view.showProgressIndicator(false);
        view=null;
        retrofitApis=null;
    }
    public void  loadProfile(String userId){
        if(view!=null)
        view.showProgressIndicator(true);
        retrofitApis.getProfile(userId).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if(view!=null)
                view.showProgressIndicator(false);
                if(response.isSuccessful()){
                    ProfileResponse profileResponce=response.body();
                    if(view!=null)
                    {
                        view.onProfileData(profileResponce.getUserInfo());
                        view.onBrands(profileResponce.getBrands());
//                        view.onPosts(profileResponce.getPosts());
//                        view.onBankDetails(profileResponce.getBankDetails());
                    }
                }

            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                if(view!=null) {
                    view.showProgressIndicator(false);
                    view.showMessage(t.getMessage());
                }


            }
        });

    }


}
