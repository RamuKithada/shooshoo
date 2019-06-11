package com.android.shooshoo.presenters;

import com.android.shooshoo.models.HomeSponsorResponce;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.views.HomeView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter implements BasePresenter<HomeView> {
    private HomeView homeView;
    private RetrofitApis retrofitApis;

    public void attachView(HomeView view) {
        this.homeView=view;
        retrofitApis= RetrofitApis.Factory.create(view.getContext());
    }

    @Override
    public void detachView() {
        homeView=null;
        retrofitApis=null;
    }
    public void loadSponsor(){
        homeView.showProgressIndicator(true);
        retrofitApis.getHomeSponcers()
                .enqueue(new Callback<HomeSponsorResponce>() {
                    @Override
                    public void onResponse(Call<HomeSponsorResponce> call, Response<HomeSponsorResponce> response) {
                        homeView.showProgressIndicator(false);
                        if(response.isSuccessful()){
                            HomeSponsorResponce homeSponsorResponce=response.body();
                            homeView.onLoadSponsors(homeSponsorResponce.getChallenges());
                        }
                    }

                    @Override
                    public void onFailure(Call<HomeSponsorResponce> call, Throwable t) {
                        homeView.showProgressIndicator(false);
                        if(t!=null)
                            homeView.showMessage(t.getMessage());
                    }
                });
    }

}
