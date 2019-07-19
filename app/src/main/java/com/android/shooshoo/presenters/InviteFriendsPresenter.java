package com.android.shooshoo.presenters;

import com.android.shooshoo.models.FollowerResult;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.views.InviteFriendsView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InviteFriendsPresenter implements BasePresenter<InviteFriendsView>{
    InviteFriendsView view;
    RetrofitApis retrofitApis;
    @Override
    public void attachView(InviteFriendsView view) {
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
    public void getFollowers(String userId){
        if(view!=null)
        view.showProgressIndicator(true);
        retrofitApis.getFollowers(userId).enqueue(new Callback<FollowerResult>() {
            @Override
            public void onResponse(Call<FollowerResult> call, Response<FollowerResult> response) {
                if(view!=null)
                    view. showProgressIndicator(false);
                if(response.isSuccessful()){
                    FollowerResult followerResult=response.body();
                        if(view!=null)
                            view.onFriendsList(followerResult.getFollowers());

                }

            }

            @Override
            public void onFailure(Call<FollowerResult> call, Throwable t) {
                if(view!=null)
                {
                    view.showProgressIndicator(false);
                    view.showMessage(t.getMessage());
                }
            }
        });

    }


    public void sendInvitations(String challengeIds,String invites){
        if(view!=null)
        view.showProgressIndicator(true);
        retrofitApis.inviteMyFollowers(challengeIds,invites).enqueue(new Callback<FollowerResult>() {
            @Override
            public void onResponse(Call<FollowerResult> call, Response<FollowerResult> response) {
                if(view!=null)
                    view. showProgressIndicator(false);
                if(response.isSuccessful()){
                    FollowerResult followerResult=response.body();
                    if(followerResult.getStatus()==1)
                        if(view!=null)
                            view.onSuccessFullInvitation(followerResult.getMessage());

                }

            }

            @Override
            public void onFailure(Call<FollowerResult> call, Throwable t) {
                if(view!=null)
                {
                    view.showProgressIndicator(false);
                    view.showMessage(t.getMessage());
                }
            }
        });

    }
}
