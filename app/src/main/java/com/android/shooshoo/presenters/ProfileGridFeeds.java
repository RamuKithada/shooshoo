package com.android.shooshoo.presenters;

import com.android.shooshoo.models.BestPostsResponse;
import com.android.shooshoo.models.NewPostsResponse;
import com.android.shooshoo.models.ProfileResponse;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.views.ProfileGridView;
import com.android.shooshoo.views.ProfileView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileGridFeeds implements BasePresenter<ProfileGridView>{
    ProfileGridView view;
    private RetrofitApis retrofitApis;
    @Override
    public void attachView(ProfileGridView view) {
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

    public void loadFeeds(String type,String userId,int limit,int offset){
        if(type.equalsIgnoreCase("newposts"))
            loadNewPosts(userId, limit, offset);
        if(type.equalsIgnoreCase("bestposts"))
            loadBestPosts(userId, limit, offset);


    }


    public void  loadNewPosts(String userId,int limit,int offset){
        if(view!=null)
        view.showProgressIndicator(true);
        retrofitApis.getNewPosts(userId,""+limit,""+offset).enqueue(new Callback<NewPostsResponse>() {
            @Override
            public void onResponse(Call<NewPostsResponse> call, Response<NewPostsResponse> response) {
                if(view!=null)
                view.showProgressIndicator(false);
                if(response.isSuccessful()){
                    NewPostsResponse newPostsResponse=response.body();
                    if(view!=null)
                    {
                        if(newPostsResponse.getStatus()==1)
                        view.onPosts(newPostsResponse.getPosts(),newPostsResponse.getCount());
                    }
                }

            }

            @Override
            public void onFailure(Call<NewPostsResponse> call, Throwable t) {
                if(view!=null) {
                    view.showProgressIndicator(false);
                    view.showMessage(t.getMessage());
                }


            }
        });

    }
    public void  loadBestPosts(String userId,int limit,int offset){
        if(view!=null)
        view.showProgressIndicator(true);
        retrofitApis.getBestPosts(userId,""+limit,""+offset).enqueue(new Callback<BestPostsResponse>() {
            @Override
            public void onResponse(Call<BestPostsResponse> call, Response<BestPostsResponse> response) {
                if(view!=null)
                view.showProgressIndicator(false);
                if(response.isSuccessful()){
                    BestPostsResponse newPostsResponse=response.body();
                    if(view!=null)
                    {
                        if(newPostsResponse.getStatus()==1)
                        view.onPosts(newPostsResponse.getPosts(),newPostsResponse.getCount());
                    }
                }

            }

            @Override
            public void onFailure(Call<BestPostsResponse> call, Throwable t) {
                if(view!=null) {
                    view.showProgressIndicator(false);
                    view.showMessage(t.getMessage());
                }


            }
        });

    }
}
