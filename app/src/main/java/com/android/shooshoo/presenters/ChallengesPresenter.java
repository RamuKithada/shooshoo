package com.android.shooshoo.presenters;

import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.ChallengeResponse;
import com.android.shooshoo.models.ChallengesMixedList;
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
            retrofitApis.getMyChallenges(userId)
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
                                        List<Challenge> challenges=new ArrayList<Challenge>();
                                        ChallengesMixedList mixedList=challengeResponse.getCreated();
                                        if(mixedList!=null){
                                            if(mixedList.getJackpot()!=null)
                                                challenges.addAll( mixedList.getJackpot());
                                            if(mixedList.getSponsor()!=null)
                                                challenges.addAll(  mixedList.getSponsor());

                                        }
                                        view.onCreatedChallenges(challenges);
                                       challenges=new ArrayList<Challenge>();
                                         mixedList=challengeResponse.getEntered();
                                        if(mixedList!=null){
                                            if(mixedList.getJackpot()!=null)
                                                challenges.addAll(  mixedList.getJackpot());
                                            if(mixedList.getSponsor()!=null)
                                                challenges.addAll( mixedList.getSponsor());

                                        }
                                        view.onEnteredChallenges(challenges);
                                        challenges=new ArrayList<Challenge>();
                                         mixedList=challengeResponse.getSaved();
                                        if(mixedList!=null){
                                            if(mixedList.getJackpot()!=null)
                                                challenges.addAll(  mixedList.getJackpot());
                                            if(mixedList.getSponsor()!=null)
                                                challenges.addAll(mixedList.getSponsor());

                                        }
                                        view.onSavedChallenges(challenges);
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
