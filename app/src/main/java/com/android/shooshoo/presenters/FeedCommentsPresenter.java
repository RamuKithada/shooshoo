package com.android.shooshoo.presenters;

import com.android.shooshoo.models.CommentsResponce;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.views.FeedCommentsView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedCommentsPresenter implements BasePresenter<FeedCommentsView>{
    FeedCommentsView view=null;
    RetrofitApis retrofitApis=null;

    @Override
    public void attachView(FeedCommentsView view) {
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
    public void postReply(String feedId,String userId,String commentId,String comment){
            view.showProgressIndicator(true);
            retrofitApis.replyComment(feedId,userId,comment,commentId).enqueue(new Callback<ResponseBody>() {
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
                                view.onReplyPosted(status,msg);

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
    public void postComment(String feedId,String userId,String comment){

            view.showProgressIndicator(true);
            retrofitApis.addComments(feedId,userId,comment).enqueue(new Callback<ResponseBody>() {
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
                                view.onCommentPosted(status,msg);

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
    public void getComments(String feedId,String limit,String offset){
            view.showProgressIndicator(true);
            retrofitApis.getComments(feedId,limit,offset).enqueue(new Callback<CommentsResponce>() {
                @Override
                public void onResponse(Call<CommentsResponce> call, Response<CommentsResponce> response) {
                    if(view!=null)
                        view.showProgressIndicator(false);
                    if(response.isSuccessful()){
                        CommentsResponce commentsResponce=response.body();
                        if(commentsResponce==null)
                            return;
                        if(view!=null)
                            view.showMessage(commentsResponce.getMessage());
                        if(commentsResponce.getStatus()==1){
                            if(view!=null)
                                view.onAllComments(commentsResponce.getComments(),commentsResponce.getCount());
                        }
                        }

                }

                @Override
                public void onFailure(Call<CommentsResponce> call, Throwable t) {
                    if(view!=null)
                    {
                        view.showProgressIndicator(false);
                        view.showMessage(t.getMessage());
                    }

                }
            });

    }

}
