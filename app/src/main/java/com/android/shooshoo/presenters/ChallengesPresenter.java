package com.android.shooshoo.presenters;

import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.ChallengesResponse;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.views.ChallengesView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChallengesPresenter implements BasePresenter<ChallengesView>{
    ChallengesView view=null;
    RetrofitApis retrofitApis=null;
    @Override
    public void attachView(ChallengesView view) {
        this.view=view;
        this.retrofitApis= RetrofitApis.Factory.create(view.getContext());

    }

    @Override
    public void detachView() {
        if(view!=null){
            view.showProgressIndicator(false);
        }
        view=null;
        retrofitApis=null;

    }
    public void getChallenges(String userId){
        if(view!=null)
            view.showProgressIndicator(true);
        if(retrofitApis!=null)
            retrofitApis.getMyChallenges(userId,"5","0")
                    .enqueue(new Callback<ChallengesResponse>() {
                        @Override
                        public void onResponse(Call<ChallengesResponse> call, Response<ChallengesResponse> response) {

                            if(view!=null)
                                view.showProgressIndicator(false);
                            if(response.isSuccessful()){
                                ChallengesResponse challengeResponse=response.body();

                                if(challengeResponse!=null){
                                    if(view!=null)
                                    {
                                        view.onCreatedChallenges(challengeResponse.getCreated());
                                        view.onEnteredChallenges(challengeResponse.getEntered());
                                        view.onSavedChallenges(challengeResponse.getSaved());
                                    }


                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<ChallengesResponse> call, Throwable t) {
                            if(view!=null)
                            {
                                view.showProgressIndicator(false);
                                view.showMessage(t.getMessage());
                            }

                        }
                    });


    }


}
