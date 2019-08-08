package com.android.shooshoo.presenters;

import android.net.Uri;

import com.android.shooshoo.models.LoginSuccess;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.views.UpdateUserInfoView;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/****
 * {@link UpdateUserInfoPresenter} is used to update the categories and brands to server
 */
public class UpdateUserInfoPresenter implements BasePresenter<UpdateUserInfoView> {
    UpdateUserInfoView view;
    RetrofitApis retrofitApis;

    @Override
    public void attachView(UpdateUserInfoView view) {
        this.view=view;
        retrofitApis=RetrofitApis.Factory.create(view.getContext());
    }

    @Override
    public void detachView() {
       this.view=null;
       retrofitApis=null;
    }
    public void updateUserProfile(Uri newsImage, String userName,String password,String firstName, String lastName, String dob,
                                  String countryId, String cityId, String zipcode, String streetName, String streetNumber,
                                  String mobile,String email, String gender, String lat, String lng, String token){
        File file = null;
        MultipartBody.Part body = null;
        if (newsImage != null) {
            file = new File(newsImage.getPath());
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            body = MultipartBody.Part.createFormData("image", file.getName(), reqFile);
        }

        if(view!=null)
        {
            view.showProgressIndicator(true);
        }

        retrofitApis.updateProfile(body,
                getTextPart(userName),
                getTextPart(password),
                getTextPart(firstName), getTextPart(lastName),
                getTextPart(dob), getTextPart(countryId),
                getTextPart(cityId), getTextPart(zipcode),
                getTextPart(streetName), getTextPart(streetNumber),
                getTextPart(mobile),
                getTextPart(email),
                getTextPart(gender.toLowerCase()),
                getTextPart(lat),
                getTextPart(lng),
                getTextPart("android"),
                getTextPart(token)).enqueue(new Callback<LoginSuccess>() {
            @Override
            public void onResponse(Call<LoginSuccess> call, Response<LoginSuccess> response) {
                if(view!=null)
                view.showProgressIndicator(false);
                if(response.isSuccessful()){
                    if(view!=null)
                        view.loginDetails(response.body());
                }

            }

            @Override
            public void onFailure(Call<LoginSuccess> call, Throwable t) {
                if(view!=null)
                    view.showProgressIndicator(false);
            }
        });


    }

 public void updateProfile( Uri newsImage, String userId, String firstName, String lastName,String countryId,
                            String cityId, String zipcode, String streetName, String streetNumber,
                            String mobile, String gender, String token){



     Callback<ResponseBody> bodyCallback=new Callback<ResponseBody>() {
         @Override
         public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
             if(view!=null)
                 view.showProgressIndicator(false);
             if(response.isSuccessful()){
                 if(view!=null)
                     view.onUpdateUserInfo(response.body());
             }

         }

         @Override
         public void onFailure(Call<ResponseBody> call, Throwable t) {
             if(view!=null)
                 view.showProgressIndicator(false);
         }
     };


        if(view!=null)
            view.showProgressIndicator(true);
     File file = null;
     MultipartBody.Part body = null;
     if (newsImage != null) {
         file = new File(newsImage.getPath());
         RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
         body = MultipartBody.Part.createFormData("image", file.getName(), reqFile);
     }
          if(newsImage==null)
           retrofitApis.updateProfile(userId,firstName,lastName,countryId,cityId,zipcode,streetName,streetNumber,mobile,gender,token)
                .enqueue(bodyCallback);
          else {
              retrofitApis.updateProfileImage(body,
                      getTextPart(userId),
                      getTextPart(firstName),
                      getTextPart(lastName), getTextPart(countryId),
                      getTextPart(cityId), getTextPart(zipcode),
                      getTextPart(streetName), getTextPart(streetNumber),
                      getTextPart(mobile),
                      getTextPart(gender.toLowerCase()),
                      getTextPart(token)).enqueue(bodyCallback);


          }


    }




    /**
     * to update the categories to server
     * @param userId user id
     * @param catList categories list that are selected by user
     */
    public void updateUserCat(String userId,String catList){
        if(view!=null)
        view.showProgressIndicator(true);
        retrofitApis.updateUserCat(userId,catList).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(view!=null)
                view.showProgressIndicator(false);
                if(response.isSuccessful()){
                    if(view!=null)
                    view.onUpdateUserInfo(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(view!=null){  view.showProgressIndicator(false);
                view.showMessage(t.getMessage());
                }

            }
        });

    }

    /**
     * to update the brands to server
     * @param userId is user id
     * @param brandList ids of the brands
     */

    public void updateUserBrand(String userId,String brandList){
        if(view!=null)
        view.showProgressIndicator(true);
        retrofitApis.updateUserBrand(userId,brandList).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(view!=null)
                view.showProgressIndicator(false);
                if(response.isSuccessful()){
                    if(view!=null)
                    view.onUpdateUserInfo(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(view!=null){
                    view.showProgressIndicator(false);
                    view.showMessage(t.getMessage());
                }

            }
        });

    }

  /*  public void saveBankDetails(String userid,String iban,String bic_swift,String accountOwner,String bankName){
        if(view!=null)
            view.showProgressIndicator(true);
        retrofitApis.saveBankDetails(userid, iban, bic_swift, accountOwner, bankName).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(view!=null)
                    view.showProgressIndicator(false);
                if(response.isSuccessful()){
                    if(view!=null)
                        view.onUpdateUserInfo(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(view!=null){
                    view.showProgressIndicator(false);
                    view.showMessage(t.getMessage());
                }

            }
        });

    }*/

    private RequestBody getTextPart(String s) {
        if (s == null) {
            s = "";
        }
        return RequestBody.create(MediaType.parse("text/plain"), s);
    }

}
