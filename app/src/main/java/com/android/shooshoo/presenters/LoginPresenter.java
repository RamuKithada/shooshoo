package com.android.shooshoo.presenters;


import com.android.shooshoo.utils.ApiUrls;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.models.LoginSuccess;
import com.android.shooshoo.views.LoginView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements BasePresenter<LoginView> {

    private LoginView loginView;
 private RetrofitApis retrofitApis;
    @Override
    public void attachView(LoginView view) {
    this.loginView=view;
     retrofitApis= RetrofitApis.Factory.create(view.getContext());
    }

    @Override
    public void detachView() {
        loginView=null;
        retrofitApis=null;
    }

    public void loginUser(String usr,String pws){
        loginView.showProgressIndicator(true);
       retrofitApis.loginUser(usr,pws, ApiUrls.DEVICE_TYPE,ApiUrls.DEVICE_TOKEN)
                .enqueue(new Callback<LoginSuccess>() {
                    @Override
                    public void onResponse(Call<LoginSuccess> call, Response<LoginSuccess> response) {
                        loginView.showProgressIndicator(false);
                        if(response.isSuccessful()){
                            loginView.loginDetails(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginSuccess> call, Throwable t) {
                        loginView.showProgressIndicator(false);
                        if(t!=null)
                            loginView.showMessage(t.getMessage());
                    }
                });

    }
    public void signupUser(String usr,String pws,String email){
        loginView.showProgressIndicator(true);
        retrofitApis.signupUser(usr,email,pws, ApiUrls.DEVICE_TYPE,ApiUrls.DEVICE_TOKEN)
                .enqueue(new Callback<LoginSuccess>() {
                    @Override
                    public void onResponse(Call<LoginSuccess> call, Response<LoginSuccess> response) {
                        loginView.showProgressIndicator(false);
                        if(response.isSuccessful()){
                            loginView.loginDetails(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginSuccess> call, Throwable t) {
                        loginView.showProgressIndicator(false);
                        if(t!=null)
                            loginView.showMessage(t.getMessage());
                    }
                });

    }
    public void forgetPassword(String email){
        loginView.showProgressIndicator(true);
        retrofitApis.forgetPassword(email)
                .enqueue(new Callback<LoginSuccess>() {
                    @Override
                    public void onResponse(Call<LoginSuccess> call, Response<LoginSuccess> response) {
                        loginView.showProgressIndicator(false);
                        if(response.isSuccessful()){
                            loginView.loginDetails(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginSuccess> call, Throwable t) {
                        loginView.showProgressIndicator(false);
                        if(t!=null)
                            loginView.showMessage(t.getMessage());
                    }
                });
    }
}
