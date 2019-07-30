package com.android.shooshoo.presenters;

import android.util.Log;

import com.android.shooshoo.activity.FeedsActivity;
import com.android.shooshoo.models.ChallengeFeeds;
import com.android.shooshoo.models.FeedsResponse;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.views.FeedsView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
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
        if(view!=null)
        view.showProgressIndicator(false);
      this.retrofitApis=null;
      view=null;
    }
    public void fullFeeds(){
        if(view!=null){

            view.showProgressIndicator(true);
            retrofitApis.getFeeds().enqueue(new Callback<ChallengeFeeds>() {
                @Override
                public void onResponse(Call<ChallengeFeeds> call, Response<ChallengeFeeds> response) {
                    if(view!=null){
                        view.showProgressIndicator(false);
                        if(response.isSuccessful()){
                            ChallengeFeeds feedsResponse=response.body();
                            view.showMessage(feedsResponse.getMessage());
                            if(feedsResponse.getStatus()==1){
//                                feedsResponse.setType(FeedsActivity.GRID);
                                view.onFeedsLoaded(feedsResponse);
                            }

                        }

                    }


                }

                @Override
                public void onFailure(Call<ChallengeFeeds> call, Throwable t) {
                    if(view!=null) {
                        view.showProgressIndicator(false);
                        view.showMessage(t.getMessage());
                    }
                }
            });
        }


    }

     public void gridFeeds(){
         if(view!=null){
             view.showProgressIndicator(true);
             retrofitApis.getChallengeFeeds().enqueue(new Callback<ChallengeFeeds>() {
                 @Override
                 public void onResponse(Call<ChallengeFeeds> call, Response<ChallengeFeeds> response) {
                     if(view!=null){
                         view.showProgressIndicator(false);
                         if(response.isSuccessful()){
                             ChallengeFeeds feedsResponse=response.body();
                             view.showMessage(feedsResponse.getMessage());
                             if(feedsResponse.getStatus()==1){
//                                 feedsResponse.setType(FeedsActivity.FULL);
                                 view.onFeedsLoaded(feedsResponse);
                             }

                         }

                     }


                 }

                 @Override
                 public void onFailure(Call<ChallengeFeeds> call, Throwable t) {
                     if(view!=null) {
                         view.showProgressIndicator(false);
                         view.showMessage(t.getMessage());
                     }
                 }
             });
         }


     }
     public void loadFeeds(String type,int limit,int offset,String userid){
         if(view!=null) {
             view.showProgressIndicator(true);
             retrofitApis.loadFeeds(type, ""+offset, ""+limit,userid).enqueue(new Callback<FeedsResponse>() {
                 @Override
                 public void onResponse(Call<FeedsResponse> call, Response<FeedsResponse> response) {
                     if(view!=null)
                      view.showProgressIndicator(false);
                     if(response.isSuccessful()){
                         if(view!=null){
                             FeedsResponse feedsResponse=response.body();
                             if(feedsResponse.getStatus()==1)
                             view.onFeedsLoaded(feedsResponse.getFeeds(),feedsResponse.getCount());
                         }
                     }

                 }

                 @Override
                 public void onFailure(Call<FeedsResponse> call, Throwable t) {
                     if(view!=null)
                     {
                         view.showProgressIndicator(false);
                         view.showMessage(t.getMessage());
                     }
                 }
             });
         }
     }



    /**
     * Like the feed user watch
     * @param userId,feedId
     */
    public void likeFeed(String userId,String feedId){
        if(view!=null){
            view.showProgressIndicator(true);
            retrofitApis.likeFeed(userId,feedId).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(view!=null)
                        view.showProgressIndicator(false);

                    if(response.isSuccessful()){
                        try {
                            String res=  response.body().string();
                            JSONObject object=new JSONObject(res);
                            String msg=object.optString("message");
                            int status=object.optInt("status");
                            if(view!=null)
                                 view.onFeedLike(status,msg);

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else {

                    }


                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if(view!=null)
                    {
                        view.showProgressIndicator(false);
                        view.showMessage(t.getMessage());
                    }

                }
            });


        }

    }
    /**
     * Like the feed user watch
     * @param userId,feedId
     */
    public void viewFeed(String userId,String feedId){
        Log.e("userid :"+userId,"feedId : "+feedId);
//        if(view!=null){
//            view.showProgressIndicator(true);
            retrofitApis.feedViewed(userId,feedId).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                 /*
//                    if(view!=null)
//                        view.showProgressIndicator(false);

                    if(response.isSuccessful()){
                        try {
                            String res=  response.body().string();
                            JSONObject object=new JSONObject(res);
                            String msg=object.optString("message");
                            int status=object.optInt("status");
//                            if(view!=null)
//                                 view.onFeedViewed(status,msg);

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else {

                    }

*/
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                 /*   if(view!=null)
                    {
                        view.showProgressIndicator(false);
                        view.showMessage(t.getMessage());
                    }*/

                }
            });


//        }

    }
    /**
     * Follow the user watch
     * @param userId,profileId
     */
    public void followUser(String userId,String profileId){
        Log.e("userid :"+userId,"feedId : "+profileId);
        if(view!=null){
            view.showProgressIndicator(true);
            retrofitApis.followUser(userId,profileId).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(view!=null)
                        view.showProgressIndicator(false);

                    if(response.isSuccessful()){
                        try {
                            String res=  response.body().string();
                            JSONObject object=new JSONObject(res);
                            String msg=object.optString("message");
                            int status=object.optInt("status");
                            if(view!=null)
                                 view.onFollowed(status,msg);

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else {

                    }


                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if(view!=null)
                    {
                        view.showProgressIndicator(false);
                        view.showMessage(t.getMessage());
                    }

                }
            });


        }

    }
    /**
     * Follow the user watch
     * @param userId,profileId
     */
    public void reportPost(String userId,String postId){
        if(view!=null){
            view.showProgressIndicator(true);
            retrofitApis.reportFeed(userId,postId).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(view!=null)
                        view.showProgressIndicator(false);

                    if(response.isSuccessful()){
                        try {
                            String res=  response.body().string();
                            JSONObject object=new JSONObject(res);
                            String msg=object.optString("message");
                            int status=object.optInt("status");
                            if(view!=null)
                                view.onFollowed(status,msg);

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else {

                    }


                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if(view!=null)
                    {
                        view.showProgressIndicator(false);
                        view.showMessage(t.getMessage());
                    }

                }
            });


        }

    }

}
