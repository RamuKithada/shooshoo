package com.android.shooshoo.presenters;

import com.android.shooshoo.models.FeedsResponse;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.views.FeedsView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedsPresenter implements BasePresenter<FeedsView>{
    FeedsView view=null;
    RetrofitApis retrofitApis=null;

    @Override
    public void attachView(FeedsView view) {
        this.view=view;
        retrofitApis=RetrofitApis.Factory.create(view.getContext());
    }

    @Override
    public void detachView() {
        view.showProgressIndicator(false);
      this.retrofitApis=null;
      view=null;
    }
    public void loadFeeds(){
        if(view!=null){

            view.showProgressIndicator(true);
            retrofitApis.getFeeds().enqueue(new Callback<FeedsResponse>() {
                @Override
                public void onResponse(Call<FeedsResponse> call, Response<FeedsResponse> response) {
                    if(view!=null){
                        view.showProgressIndicator(false);
                        if(response.isSuccessful()){
                            FeedsResponse feedsResponse=response.body();
                            view.showMessage(feedsResponse.getMessage());
                            if(feedsResponse.getStatus()==1){
                                view.onFeedsLoaded(feedsResponse.getFeeds());
                            }

                        }

                    }


                }

                @Override
                public void onFailure(Call<FeedsResponse> call, Throwable t) {
                    if(view!=null) {
                        view.showProgressIndicator(false);
                        view.showMessage(t.getMessage());
                    }
                }
            });
        }


    }

}
