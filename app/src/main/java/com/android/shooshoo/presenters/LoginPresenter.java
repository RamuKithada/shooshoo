package com.android.shooshoo.presenters;


import com.android.shooshoo.utils.ApiUrls;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.models.LoginSuccess;
import com.android.shooshoo.views.LoginView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This used to call web services and interact with the activity which  implements {@link LoginView}
 */
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
/**
     loginUser is used to initiate login service
 @param pws is password of the user
 @param usr  is user mail when he provided registration time
 */
    public void loginUser(String usr,String pws,String token){
        loginView.showProgressIndicator(true);
       retrofitApis.loginUser(usr,pws, ApiUrls.DEVICE_TYPE,token)
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

    /**this is used to call the signup an user
     *
     * @param usr username
     * @param pws is the password for login
     * @param email user mail id for sign up
     */
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

    /**
     * forgetPassword is used to call the forget password web service to change password
     * @param email is registered mail id
     */
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
