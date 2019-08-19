package com.android.shooshoo.presenters;

import com.android.shooshoo.models.ChallengeSearchResponse;
import com.android.shooshoo.models.CompanySearchResponse;
import com.android.shooshoo.models.UserSearchResponse;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.views.SearchView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/***
 *    {@link HomeSearchPresenter } is used to search  query to server and get the resultant list to SearchView
 */

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

    /** This is used to search user by their Username,first name and last name.
     *
     * @param searchKey is user query value
     */
    public void searchUsers(String searchKey){
        if(searchKey.isEmpty())
            searchKey="a";

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

    /** This is used to search Challenge by their challenge name
     *
     * @param searchKey is user query value
     */
  public void searchChallenges(String searchKey){
      if(searchKey.isEmpty())
          searchKey="a";
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

    /** This is used to search Brand  by their brand  name
     *
     * @param searchKey is user query value
     */
  public void searchCompany(String searchKey){
      if(searchKey.isEmpty())
          searchKey="a";
        retrofitApis.searchCompanies(searchKey,"companies").enqueue(new Callback<CompanySearchResponse>() {
            @Override
            public void onResponse(Call<CompanySearchResponse> call, Response<CompanySearchResponse> response) {
                if(view!=null)
                    view. showProgressIndicator(false);
                if(response.isSuccessful()){
                    CompanySearchResponse followerResult=response.body();
                    if(view!=null)
                        view.onBrandSearchResult(followerResult.getChallenges());
                }

            }

            @Override
            public void onFailure(Call<CompanySearchResponse> call, Throwable t) {
                if(view!=null)
                {
                    view.showProgressIndicator(false);
                    view.showMessage(t.getMessage());
                }
            }
        });

    }


}
