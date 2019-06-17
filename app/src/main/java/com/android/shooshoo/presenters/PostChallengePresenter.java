package com.android.shooshoo.presenters;

import android.net.Uri;

import com.android.shooshoo.models.GameMasterResult;
import com.android.shooshoo.models.RecentPostsResponce;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.views.BaseView;
import com.android.shooshoo.views.PostChallengeView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostChallengePresenter implements BasePresenter<PostChallengeView> {
    PostChallengeView view;
    private RetrofitApis retrofitApis;
    @Override
    public void attachView(PostChallengeView view) {
        this.view=view;
        retrofitApis= RetrofitApis.Factory.create(view.getContext());

    }

    @Override
    public void detachView() {
        view.showProgressIndicator(false);
        view=null;
        retrofitApis=null;
    }
    public void postChallenge(String userId, String challengeId, String filename, String type,String mPostDes){
        if(view!=null){
            File file=null;
            MultipartBody.Part body=null;
            if(filename!=null)
            {
                file = new File(filename);
                RequestBody reqFile= RequestBody.create(MediaType.parse("video/*"), file);
                body = MultipartBody.Part.createFormData("content", file.getName(), reqFile);
            }
            view.showProgressIndicator(true);
            retrofitApis.postforChallenge(getTextPart(challengeId),getTextPart(userId),getTextPart(type),body,getTextPart(mPostDes)).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    view.showProgressIndicator(false);
                    if(response.isSuccessful()){
                        try {
                            String res= response.body().string();
                            JSONObject object=new JSONObject(res);
                            int status=object.optInt("status");
                            view.showMessage(object.getString("message"));
                            if(status==1){
                              view.onSuccessfulUpload("Successfully posted the video");
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    view.showProgressIndicator(false);
                    view.showMessage(t.getMessage());


                }
            });

        }

    }
    private RequestBody getTextPart(String s) {
        if(s==null){
            s="";
        }
        return RequestBody.create(MediaType.parse("text/plain"),s);
    }
    public void getRecentPosts(String challengeId,  String type){

          if(view!=null){
              view.showProgressIndicator(true);
              retrofitApis.getRecentPostsOfChallenge(challengeId,type).enqueue(new Callback<RecentPostsResponce>() {
                  @Override
                  public void onResponse(Call<RecentPostsResponce> call, Response<RecentPostsResponce> response) {
                      view.showProgressIndicator(false);
                      if(response.isSuccessful()){
                      RecentPostsResponce recentPostsResponce=response.body();
                      view.onRecentPosts(recentPostsResponce.getPost());
                      }

                  }

                  @Override
                  public void onFailure(Call<RecentPostsResponce> call, Throwable t) {
                      view.showProgressIndicator(false);
                      view.showMessage(t.getMessage());
                  }
              });
          }

    }
}
