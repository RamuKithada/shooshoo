package com.android.shooshoo.presenters;

import com.android.shooshoo.models.HomeResponse;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.views.HomeView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * {@link HomePresenter} is used to call web services from server i.e, used to in home fragment
 */
public class HomePresenter implements BasePresenter<HomeView> {
    private HomeView homeView;
    private RetrofitApis retrofitApis;

    public void attachView(HomeView view) {
        this.homeView=view;
        retrofitApis= RetrofitApis.Factory.create(view.getContext());
    }

    @Override
    public void detachView() {
        if(homeView!=null)
        homeView.showProgressIndicator(false);
        homeView=null;
        retrofitApis=null;
    }

    /**
     * Load the Sponsor challenges from server to show in home fragment
     */
    public void loadHome(String userId){

        homeView.showProgressIndicator(true);
        retrofitApis.getHomeData()
                .enqueue(new Callback<HomeResponse>() {
                    @Override
                    public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                        if(homeView!=null) {
                            homeView.showProgressIndicator(false);
                            if (response.isSuccessful()) {
                                HomeResponse homeResponse = response.body();
                                homeView.onLoadService(homeResponse);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<HomeResponse> call, Throwable t) {
                        if (homeView != null) {
                            homeView.showProgressIndicator(false);
                            if (t != null)
                                homeView.showMessage(t.getMessage());
                        }
                    }
                });
    }
  /*  public void loadJackpots(){

        homeView.showProgressIndicator(true);
        retrofitApis.getHomeJackpots()
                .enqueue(new Callback<HomeResponse>() {
                    @Override
                    public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                        if(homeView!=null) {
                            homeView.showProgressIndicator(false);
                            if (response.isSuccessful()) {
                                HomeResponse homeSponsorResponce = response.body();
                                homeView.onLoadSponsors(homeSponsorResponce.getJackpotsChallenges());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<HomeResponse> call, Throwable t) {
                        if (homeView != null) {
                            homeView.showProgressIndicator(false);
                            if (t != null)
                                homeView.showMessage(t.getMessage());
                        }
                    }
                });
    }*/
    public void LoadHome(String userId){


    }

}
