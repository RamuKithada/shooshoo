package com.android.shooshoo.presenters;
import com.android.shooshoo.models.NotificationResult;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.views.NotificationView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationPresenter implements BasePresenter<NotificationView>{
    NotificationView view;
    RetrofitApis retrofitApis;

    @Override
    public void attachView(NotificationView view) {
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
    public void  getNotificationList(String userid,int limit,int offset){

            if(view!=null)
                view.showProgressIndicator(true);
            retrofitApis.getMyNotifications(userid,String.valueOf(limit),String.valueOf(offset))
                    .enqueue(new Callback<NotificationResult>() {
                @Override
                public void onResponse(Call<NotificationResult> call, Response<NotificationResult> response) {
                    if(view!=null)
                        view.showProgressIndicator(false);
                    if(response.isSuccessful()){
                        NotificationResult rulesResponse=response.body();
                        if(view!=null)
                           if(rulesResponse.getStatus()==1)
                            view.onNotificationList(rulesResponse.getNotifications(),rulesResponse.getCount());
                    }
                }

                @Override
                public void onFailure(Call<NotificationResult> call, Throwable t) {
                    if(view!=null) {
                        view.showProgressIndicator(false);
                        view.showMessage(t.getMessage());
                    }
                }
            });


    }
}
