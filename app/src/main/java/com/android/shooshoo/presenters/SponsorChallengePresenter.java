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
/** {@link SponsorChallengePresenter} is used to call web service related to create saveJackpotAudience challenge
 * {@link SponsorChallengeView} is used by {@link SponsorChallengePresenter} to interact withe activity that want use {@link SponsorChallengePresenter}
 */
public class SponsorChallengePresenter implements BasePresenter<SponsorChallengeView> {


    RetrofitApis retrofitApis;
    SponsorChallengeView view;

    @Override
    public void attachView(SponsorChallengeView view) {
        this.view=view;
        this.retrofitApis=RetrofitApis.Factory.create(view.getContext());
    }

    @Override
    public void detachView() {
        if(view!=null)
            view.showProgressIndicator(false);
        this.view=null;
        this.retrofitApis=null;

    }
    Callback<CompanyResponse> companyResponseCallback=new Callback<CompanyResponse>() {
        @Override
        public void onResponse(Call<CompanyResponse> call, Response<CompanyResponse> response) {
            if(view!=null)
            view.showProgressIndicator(false);

            if(response.isSuccessful()){
                CompanyResponse companyResponse=response.body();
                if(view!=null)
                   view.showMessage(companyResponse.getMessage());
                if(companyResponse.getStatus()==1)
                    if(view!=null)
                    view.onCompanyRegister(companyResponse.getData());

            }
        }

        @Override
        public void onFailure(Call<CompanyResponse> call, Throwable t) {
            if(view!=null){
                view.showProgressIndicator(false);
            view.showMessage(t.getMessage());
        }
        }
    };

    Callback<ChallengeResponse> challengeResponseCallback=new Callback<ChallengeResponse>() {
        @Override
        public void onResponse(Call<ChallengeResponse> call, Response<ChallengeResponse> response) {
            if(view!=null)
            view.showProgressIndicator(false);

            if(response.isSuccessful()){
                ChallengeResponse companyResponse=response.body();
                if(view!=null)
                view.showMessage(companyResponse.getMessage());
                if(companyResponse.getStatus()==1)
                    if(view!=null)
                    view.onChallengeResponse(companyResponse.getData());

            }
        }

        @Override
        public void onFailure(Call<ChallengeResponse> call, Throwable t) {
            if(view!=null){
            view.showProgressIndicator(false);
            view.showMessage(t.getMessage());
        }}
    };



    public void createCompany(Uri companyLogo, String userId,String compName, String email, String firstName, String  lastName,
                              String  country, String  city, String  zipcode, String  street, String  streetNumber,
                              String mobile, String taxNum, String  companyEmail){

        File file=null;
        MultipartBody.Part body=null;
        if(companyLogo!=null)
        {
            file = new File(companyLogo.getPath());
            RequestBody reqFile= RequestBody.create(MediaType.parse("image/*"), file);
            body = MultipartBody.Part.createFormData("compLogo", file.getName(), reqFile);
        }
        if(view!=null)
        view.showProgressIndicator(true);
        retrofitApis.saveCompany(body,getTextPart(userId),getTextPart(compName),getTextPart(email),getTextPart(firstName),getTextPart(lastName)
          ,getTextPart(country),getTextPart(city),getTextPart(zipcode),getTextPart(street),getTextPart(streetNumber),getTextPart(mobile)
        ,getTextPart(taxNum),getTextPart(companyEmail)).enqueue(companyResponseCallback);

    }

   public void createChallenge(String  userId,String  sponsoredBy,int privateSponsor,String challName,String  startDate,String  startTime,
                               String  endDate,String  endtime,String  description,String  photoEntries,
                               String  videoEntries,String  videoLength,String  bannerImage,String challVideo){


       String  maxLength=videoLength;
       if(videoLength.toLowerCase().contains("sec"))
           maxLength=videoLength.toLowerCase().replace("sec"," ").trim();
       else if(videoLength.toLowerCase().contains("min"))
           maxLength=videoLength.toLowerCase().replace("min"," ").trim();
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
       if(view!=null)
       view.showProgressIndicator(true);
       retrofitApis.saveChallenge(getTextPart(userId),getTextPart(sponsoredBy),getTextPart(""+privateSponsor),getTextPart(challName),getTextPart(startDate),
               getTextPart(startTime),getTextPart(endDate),getTextPart(endtime),getTextPart(description),getTextPart(photoEntries),
               getTextPart(videoEntries),getTextPart(maxLength),bannerImageBody,challengeVideoBody).enqueue(challengeResponseCallback);



   }

   public  void createAudience( String companyId, String userId, String prizesInfo, String totalPrize,
                               String winners,String radar,String audZipcode,String audMiles, String countryId,String cityId,
                                String categories, String audRegion, String language,String ageStart, String ageEnd,String gender){
       if(view!=null)
       view.showProgressIndicator(true);
        retrofitApis.saveAudience(companyId, userId, prizesInfo, totalPrize, winners, radar, audZipcode, audMiles, countryId,cityId,
                          categories,audRegion,language,ageStart,ageEnd, gender).enqueue(challengeResponseCallback);


   }

   public void saveCampaign(String challengeId, String userId, String budget, String summery){
       if(view!=null)
        view.showProgressIndicator(true);
        retrofitApis.saveCampaign(challengeId,userId,budget,summery).enqueue(challengeResponseCallback);


   }


    private RequestBody getTextPart(String s) {
        if(s==null){
            s="";
        }
        return RequestBody.create(MediaType.parse("text/plain"),s);
    }
}
