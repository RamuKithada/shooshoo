package com.android.shooshoo.presenters;
import com.android.shooshoo.models.JackpotResult;
import com.android.shooshoo.utils.RetrofitApis;
import com.android.shooshoo.views.JackpotChallengeView;

import java.io.File;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/** {@link JackpotChallengePresenter} is used to call web service related to create or save JackpotAudience challenge
 * {@link JackpotChallengeView} is used by {@link JackpotChallengePresenter} to interact with activity that want use {@link JackpotChallengePresenter}
 */
public class JackpotChallengePresenter implements BasePresenter<JackpotChallengeView> {
  private   RetrofitApis retrofitApis;
  private   JackpotChallengeView view;
    @Override
    public void attachView(JackpotChallengeView view) {
     this.view =view;
     retrofitApis=RetrofitApis.Factory.create(view.getContext());

    }

    @Override
    public void detachView() {
        if(view!=null)
        view.showProgressIndicator(false);
        retrofitApis=null;
        view =null;

    }
  Callback<JackpotResult> callback=new Callback<JackpotResult>() {
      @Override
      public void onResponse(Call<JackpotResult> call, Response<JackpotResult> response) {
          if (view != null) {
              view.showProgressIndicator(false);
              if (response.isSuccessful()) {
                  JackpotResult companyResponse = response.body();
                  view.showMessage(companyResponse.getMessage());
                  if (companyResponse.getStatus() == 1)
                      view.onChallengeUpdated(companyResponse.getChallenge());
              }
          }
      }

      @Override
      public void onFailure(Call<JackpotResult> call, Throwable t) {

          if (view != null) {
              view.showProgressIndicator(false);
              view.showMessage(t.getMessage());
          }

      }
  };



/*
    public void createGameMaster(Uri masterLogo,String userid, String firstName, String lastName, String dob, String country, String city, String zipcode,
                                  String streetName, String streetNumber, String mobileNumber, String gender){
        File file=null;
        MultipartBody.Part body=null;
        if(masterLogo!=null)
        {
            file = new File(masterLogo.getPath());
            RequestBody reqFile= RequestBody.create(MediaType.parse("image/*"), file);
            body = MultipartBody.Part.createFormData("masterLogo", file.getName(), reqFile);
        }
        if(view!=null){
        view.showProgressIndicator(true);
        retrofitApis.saveGameMaster(body,getTextPart(userid),getTextPart(firstName),getTextPart(lastName),getTextPart(dob),
                getTextPart(country),getTextPart(city),getTextPart(zipcode),getTextPart(streetName),getTextPart(streetNumber),
                getTextPart(mobileNumber),getTextPart(gender))
                .enqueue(callback);}
    }*/



    public void createChallenge(String  userId,String  bannerImage,String challVideo,String challName,
                                String  startDate,String  startTime,String  endDate,String  endtime,String  description,
                                String  photoEntries, String  videoEntries,String  videoLength){


        String  maxLength=videoLength;
        if(videoLength.toLowerCase().contains("sec"))
            maxLength=videoLength.toLowerCase().replace("sec"," ").trim();
        else if(videoLength.toLowerCase().contains("sec"))
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
        if(view!=null){
        view.showProgressIndicator(true);
        retrofitApis.saveJackpotChallenge(getTextPart(userId),bannerImageBody,challengeVideoBody,
                getTextPart(challName),getTextPart(startDate),getTextPart(startTime),getTextPart(endDate),
                getTextPart(endtime),getTextPart(description),getTextPart(photoEntries),getTextPart(videoEntries),
               getTextPart(maxLength))
                .enqueue(callback);
        }



    }



    private RequestBody getTextPart(String s) {
        if(s==null){
            s="";
        }
        return RequestBody.create(MediaType.parse("text/plain"),s);
    }

    public void createAudience(String challenegId, String userId,String amount, String limitedAccess,
                               String winners,String radar,String audZipcode,String audMiles,String country, String city,
                               String categories,String audRegion,String languages,String ageStart, String ageEnd,String gender) {



  /*      if(view!=null) {
            view.showProgressIndicator(true);
            retrofitApis.saveJackpotAudience(challenegId, userId, amount,
                    limitedAccess, winners, radar, country,audZipcode, audMiles, city,
                    categories,audRegion,languages,  ageStart, ageEnd, gender).enqueue(callback);
        }*/

    }
}
