package com.android.shooshoo.presenters;

import android.net.Uri;

import com.android.shooshoo.models.ChallengeResponse;
import com.android.shooshoo.models.CompanyResponse;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.views.SponsorChallengeView;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SponcerChallengePresenter implements BasePresenter<SponsorChallengeView> {
    RetrofitApis retrofitApis;
    SponsorChallengeView view;

    @Override
    public void attachView(SponsorChallengeView view) {
        this.view=view;
        this.retrofitApis=RetrofitApis.Factory.create(view.getContext());
    }

    @Override
    public void detachView() {
        this.view=null;
        this.retrofitApis=null;
    }
    Callback<CompanyResponse> companyResponseCallback=new Callback<CompanyResponse>() {
        @Override
        public void onResponse(Call<CompanyResponse> call, Response<CompanyResponse> response) {
            view.showProgressIndicator(false);

            if(response.isSuccessful()){
                CompanyResponse companyResponse=response.body();
                view.showMessage(companyResponse.getMessage());
                if(companyResponse.getStatus()==1)
                    view.onCompanyRegister(companyResponse.getData());

            }
        }

        @Override
        public void onFailure(Call<CompanyResponse> call, Throwable t) {
            view.showProgressIndicator(false);
            view.showMessage(t.getMessage());
        }
    };

    Callback<ChallengeResponse> challengeResponseCallback=new Callback<ChallengeResponse>() {
        @Override
        public void onResponse(Call<ChallengeResponse> call, Response<ChallengeResponse> response) {
            view.showProgressIndicator(false);

            if(response.isSuccessful()){
                ChallengeResponse companyResponse=response.body();
                view.showMessage(companyResponse.getMessage());
                if(companyResponse.getStatus()==1)
                    view.onChallengeResponse(companyResponse.getData());

            }
        }

        @Override
        public void onFailure(Call<ChallengeResponse> call, Throwable t) {
            view.showProgressIndicator(false);
            view.showMessage(t.getMessage());
        }
    };



    public void createCompany(Uri companyLogo, String userId,String compName, String email, String firstName, String  lastName,
                              String  country, String  city, String  zipcode, String  street, String  streetNumber,
                              String mobile, String taxNum, String  privateSponsor){

        File file=null;
        MultipartBody.Part body=null;
        if(companyLogo!=null)
        {
            file = new File(companyLogo.getPath());
            RequestBody reqFile= RequestBody.create(MediaType.parse("image/*"), file);
            body = MultipartBody.Part.createFormData("compLogo", file.getName(), reqFile);
        }
        view.showProgressIndicator(true);
        retrofitApis.saveComapany(body,getTextPart(userId),getTextPart(compName),getTextPart(email),getTextPart(firstName),getTextPart(lastName)
          ,getTextPart(country),getTextPart(city),getTextPart(zipcode),getTextPart(street),getTextPart(streetNumber),getTextPart(mobile)
        ,getTextPart(taxNum),getTextPart(privateSponsor)).enqueue(companyResponseCallback);

    }

   public void createChallenge(String  userId,String  sponsoredBy,String  bannerImage,String challVideo,String challName,String  startDate,String  startTime,
                               String  endDate,String  endtime,String  description,String  photoEntries, String  videoEntries,String  maxLength){

       File bannerImagefile=null;
       MultipartBody.Part bannerImageBody=null;
       if(bannerImage!=null)
       {
           bannerImagefile = new File(bannerImage);
           RequestBody reqFile= RequestBody.create(MediaType.parse("image/*"), bannerImagefile);
           bannerImageBody = MultipartBody.Part.createFormData("bannerImage", bannerImagefile.getName(), reqFile);
       }

       File challengeVideofile=null;
       MultipartBody.Part challengeVideoBody=null;
       if(challVideo!=null)
       {
           challengeVideofile = new File(challVideo);
           RequestBody reqFile= RequestBody.create(MediaType.parse("video/*"), challengeVideofile);
           challengeVideoBody = MultipartBody.Part.createFormData("challengeVideo", challengeVideofile.getName(), reqFile);
       }
       view.showProgressIndicator(true);
       retrofitApis.saveChallenge(getTextPart(userId),getTextPart(sponsoredBy),bannerImageBody,challengeVideoBody,getTextPart(challName),getTextPart(startDate),
               getTextPart(startTime),getTextPart(endDate),getTextPart(endtime),getTextPart(description),getTextPart(photoEntries),getTextPart(videoEntries),getTextPart(maxLength)
               ).enqueue(challengeResponseCallback);



   }

   public  void createAudience( String companyId, String userId,String amount, String keyDescription,String priceWorth, String totalPrize,
                               String winners,String radar,String audZipcode,String audMiles, String personalAddress,
                                String categories,String brands,String ageStart, String ageEnd,String gender){
       view.showProgressIndicator(true);
        retrofitApis.saveAudience(companyId, userId,amount, keyDescription, priceWorth, totalPrize, winners, radar, audZipcode, audMiles, personalAddress,
                categories,brands,ageStart,ageEnd, gender).enqueue(challengeResponseCallback);


   }

   public void saveCampaign(String companyId, String userId, String budget, String summery){
       view.showProgressIndicator(true);
        retrofitApis.saveCampaign(companyId,userId,budget,summery).enqueue(challengeResponseCallback);


   }


    private RequestBody getTextPart(String s) {
        if(s==null){
            s="";
        }
        return RequestBody.create(MediaType.parse("text/plain"),s);
    }
}
