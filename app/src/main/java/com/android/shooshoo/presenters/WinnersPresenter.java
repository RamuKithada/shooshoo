package com.android.shooshoo.presenters;
import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.WinnersResponce;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.views.WinnersListView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WinnersPresenter implements BasePresenter<WinnersListView>
{
    WinnersListView view=null;
    RetrofitApis retrofitApis=null;
    @Override
    public void attachView(WinnersListView view) {
        this.view=view;
        this.retrofitApis=RetrofitApis.Factory.create(view.getContext());

    }

    @Override
    public void detachView() {
        if(view!=null){
            view.showProgressIndicator(false);
        }
        view=null;
        retrofitApis=null;

    }
    public void getWinnersListForTheChallenge(Challenge challenge,String type){
        if(view!=null)
            view.showProgressIndicator(true);
        if(retrofitApis!=null)
            retrofitApis.getWinnersList(challenge.getChallengeId(),type)
            .enqueue(new Callback<WinnersResponce>() {
                @Override
                public void onResponse(Call<WinnersResponce> call, Response<WinnersResponce> response) {

                    if(view!=null)
                        view.showProgressIndicator(false);
                    if(response.isSuccessful()){
                        WinnersResponce winnersResponce=response.body();

                        if(winnersResponce!=null){
                            if(view!=null)
                                view.onWinnersListresult(winnersResponce.getWinners());


                        }

                    }
                }

                @Override
                public void onFailure(Call<WinnersResponce> call, Throwable t) {
                    if(view!=null)
                    {
                        view.showProgressIndicator(false);
                        view.showMessage(t.getMessage());
                    }

                }
            });


    }


}
