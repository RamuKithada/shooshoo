package com.android.shooshoo.presenters;

import com.android.shooshoo.models.ChallengeSearchResponse;
import com.android.shooshoo.models.UserSearchResponse;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.views.SearchView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeSearchPresenter implements BasePresenter<SearchView>{
    private SearchView view;
    private RetrofitApis retrofitApis;
    @Override
    public void attachView(SearchView view) {
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
    public void searchUsers(String searchKey){
        retrofitApis.searchUsers(searchKey,"users").enqueue(new Callback<UserSearchResponse>() {
            @Override
            public void onResponse(Call<UserSearchResponse> call, Response<UserSearchResponse> response) {
                if(view!=null)
                    view. showProgressIndicator(false);
                if(response.isSuccessful()){
                    UserSearchResponse followerResult=response.body();
                    if(view!=null)
                        view.onUserSearchResult(followerResult.getUsers());
                }

            }

            @Override
            public void onFailure(Call<UserSearchResponse> call, Throwable t) {
                if(view!=null)
                {
                    view.showProgressIndicator(false);
                    view.showMessage(t.getMessage());
                }
            }
        });

    }

  public void searchChallenges(String searchKey){
//        if(view!=null)
//            view.showProgressIndicator(true);
        retrofitApis.searchChallenge(searchKey,"challenges").enqueue(new Callback<ChallengeSearchResponse>() {
            @Override
            public void onResponse(Call<ChallengeSearchResponse> call, Response<ChallengeSearchResponse> response) {
                if(view!=null)
                    view. showProgressIndicator(false);
                if(response.isSuccessful()){
                    ChallengeSearchResponse followerResult=response.body();
                    if(view!=null)
                        view.onChallengeSearchResult(followerResult.getChallenges());
                }

            }

            @Override
            public void onFailure(Call<ChallengeSearchResponse> call, Throwable t) {
                if(view!=null)
                {
                    view.showProgressIndicator(false);
                    view.showMessage(t.getMessage());
                }
            }
        });

    }


}
