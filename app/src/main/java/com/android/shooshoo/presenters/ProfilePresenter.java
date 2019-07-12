package com.android.shooshoo.presenters;
import com.android.shooshoo.models.ProfileResponse;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.views.ProfileView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter implements BasePresenter<ProfileView>{
    ProfileView view;
    private RetrofitApis retrofitApis;
    @Override
    public void attachView(ProfileView view) {
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
    public void  loadProfile(String userId){
        if(view!=null)
        view.showProgressIndicator(true);
        retrofitApis.getProfile(userId).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if(view!=null)
                view.showProgressIndicator(false);
                if(response.isSuccessful()){
                    ProfileResponse profileResponce=response.body();
                    if(view!=null)
                    {
                        view.onProfileData(profileResponce.getUserInfo());
                        view.onBrands(profileResponce.getBrands());
//                        view.onPosts(profileResponce.getPosts());
//                        view.onBankDetails(profileResponce.getBankDetails());
                    }
                }

            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                if(view!=null) {
                    view.showProgressIndicator(false);
                    view.showMessage(t.getMessage());
                }


            }
        });

    }
    public void saveDescription(String userId,String des){
        if(view!=null)
            view.showProgressIndicator(true);
        retrofitApis.updateProfile(userId,des).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(view!=null)
                    view.showProgressIndicator(false);
                if(response.isSuccessful()){
                    ResponseBody profileResponce=response.body();
                    if(view!=null)
                    {
                        try {
                            JSONObject object=new JSONObject(profileResponce.string());
                            if(object.getString("status").equalsIgnoreCase("1")){
                                view.showMessage(object.getString("message"));
                            }else {
                                view.showMessage(object.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(view!=null) {
                    view.showProgressIndicator(false);
                    view.showMessage(t.getMessage());
                }


            }
        });

    }


}
