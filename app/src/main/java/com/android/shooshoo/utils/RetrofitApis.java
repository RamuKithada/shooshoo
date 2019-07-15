package com.android.shooshoo.utils;

import android.content.Context;

import com.android.shooshoo.models.BestPostsResponse;
import com.android.shooshoo.models.BrandsResult;
import com.android.shooshoo.models.CatResult;
import com.android.shooshoo.models.CategoryList;
import com.android.shooshoo.models.ChallengeFeeds;
import com.android.shooshoo.models.ChallengeListResponse;
import com.android.shooshoo.models.ChallengeResponse;
import com.android.shooshoo.models.ChallengesResponse;
import com.android.shooshoo.models.CityResult;
import com.android.shooshoo.models.CommentsResponce;
import com.android.shooshoo.models.CompanyDetails;
import com.android.shooshoo.models.CompanyResponse;
import com.android.shooshoo.models.CountryResult;
import com.android.shooshoo.models.FeedsResponse;
import com.android.shooshoo.models.GameMasterResult;
import com.android.shooshoo.models.HomeResponse;
import com.android.shooshoo.models.LoginSuccess;
import com.android.shooshoo.models.NewPostsResponse;
import com.android.shooshoo.models.ProfileResponse;
import com.android.shooshoo.models.RecentPostsResponce;
import com.android.shooshoo.models.RulesResponse;
import com.android.shooshoo.models.ViewAllChallengesResponse;
import com.android.shooshoo.models.WinnersResponce;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Here we using the Retrofit Library for service calls
 */
public interface RetrofitApis {



    class Factory {
            public static RetrofitApis create(Context contextOfApplication) {
                // default time out is 15 seconds
                OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .build();

                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ApiUrls.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(okHttpClient)
                        .build();
                return retrofit.create(RetrofitApis.class);
            }
        }


        @FormUrlEncoded
        @POST("signup")
        Call<LoginSuccess> signupUser(@Field("userName") String userName, @Field("email") String email, @Field("password") String password, @Field("deviceType") String deviceType, @Field("deviceToken") String deviceToken );

        @FormUrlEncoded
        @POST("login")
        Call<LoginSuccess> loginUser(@Field("userName") String userName,@Field("password") String password,@Field("deviceType") String deviceType,@Field("deviceToken") String deviceToken );

        @FormUrlEncoded
        @POST("categories")
        Call<CatResult> getCategories(@Field("limit") String limit,@Field("offset") String offset);

        @FormUrlEncoded
        @POST("brands")
        Call<BrandsResult> getBrands(@Field("categoryIds") String categoryIds,@Field("limit") String limit,@Field("offset") String offset);

    @FormUrlEncoded
    @POST("cities")
    Call<CityResult> getCities(@Field("countryId") String countryId);

    @GET("countries")
    Call<CountryResult> getCountries();

    @Multipart
    @POST("signup")
    Call<LoginSuccess> updateProfile(@Part MultipartBody.Part imageFile,
                                     @Part("userName") RequestBody userName,
                                     @Part("password") RequestBody password,
                                     @Part("firstName") RequestBody firstName,
                                     @Part("lastName") RequestBody lastName,
                                     @Part("dob") RequestBody dob,
                                     @Part("country") RequestBody country,
                                     @Part("city") RequestBody city,
                                     @Part("zipcode") RequestBody zipcode,
                                     @Part("street") RequestBody street,
                                     @Part("streetNum") RequestBody streetNum,
                                     @Part("mobileNumber") RequestBody mobileNumber,
                                     @Part("email") RequestBody email,
                                     @Part("gender") RequestBody gender,
                                     @Part("latitude") RequestBody latitude,
                                     @Part("longitude") RequestBody longitude,
                                     @Part("deviceType") RequestBody deviceType,
                                     @Part("deviceToken") RequestBody deviceToken);


    @FormUrlEncoded
    @POST("editprofile")
    Call<ResponseBody> updateProfile(@Field("userId") String userId,
                                     @Field("firstName") String firstName,
                                     @Field("lastName") String lastName,
                                     @Field("country") String country,
                                     @Field("city") String city,
                                     @Field("zipcode") String zipcode,
                                     @Field("street") String street,
                                     @Field("streetNum") String streetNum,
                                     @Field("mobileNumber") String mobileNumber,
                                     @Field("gender") String gender,
                                     @Field("deviceToken") String deviceToken);
    @FormUrlEncoded
    @POST("editprofile")
    Call<ResponseBody> updateProfile(@Field("userId") String userId,
                                     @Field("description") String firstName);

    @Multipart
    @POST("editprofile")
    Call<ResponseBody> updateProfileImage(@Part MultipartBody.Part imageFile,
                                     @Part("userId") RequestBody userId,
                                     @Part("firstName") RequestBody firstName,
                                     @Part("lastName") RequestBody lastName,
                                     @Part("country") RequestBody country,
                                     @Part("city") RequestBody city,
                                     @Part("zipcode") RequestBody zipcode,
                                     @Part("street") RequestBody street,
                                     @Part("streetNum") RequestBody streetNum,
                                     @Part("mobileNumber") RequestBody mobileNumber,
                                     @Part("gender") RequestBody gender,
                                     @Part("deviceToken") RequestBody deviceToken);

    @FormUrlEncoded
    @POST("bankDetails")
    Call<ResponseBody> saveBankDetails(@Field("userId") String userId,@Field("iban") String iban,@Field("bic_swift") String bic_swift,@Field("accountOwner") String accountOwner,
                                       @Field("bankName") String bankName);




    @FormUrlEncoded
    @POST("updateuserinfo")
    Call<ResponseBody> updateUserCat(@Field("userId") String userId,@Field("categories") String categories);
    @FormUrlEncoded
    @POST("updateuserinfo")
    Call<ResponseBody> updateUserBrand(@Field("userId") String userId,@Field("brands") String brands);
    @FormUrlEncoded
    @POST("forgotpassword")
    Call<LoginSuccess> forgetPassword(@Field("email") String email);
    @Multipart
    @POST("company")
    Call<CompanyResponse> saveComapany(@Part MultipartBody.Part  logoImage,@Part("createdBy") RequestBody userId,@Part("companyName") RequestBody compName,@Part("emailId") RequestBody email,
                                       @Part("firstName") RequestBody firstName,@Part("lastName") RequestBody  lastName,
                                       @Part("country") RequestBody  country,@Part("city") RequestBody  city,@Part("zipcode") RequestBody  zipcode,
                                       @Part("street") RequestBody  street,@Part("streetNumber") RequestBody  streetNum,@Part("mobileNumber") RequestBody  mobile,@Part("taxNumber") RequestBody  taxNum,
                                       @Part("privateSponsor") RequestBody  privateSponsor);


    @Multipart
    @POST("sponsor")
    Call<ChallengeResponse> saveChallenge(@Part("createdBy") RequestBody  createdBy, @Part("sponsoredBy") RequestBody  sponsoredBy,
                                          @Part("challengeName") RequestBody  challName, @Part("startDate") RequestBody  startDate,
                                          @Part("startTime") RequestBody  startTime,@Part("endDate") RequestBody  endDate, @Part("endTime") RequestBody  endTime,
                                          @Part("description") RequestBody  description, @Part("photoEntries") RequestBody  photoEntries,
                                          @Part("videoEntries") RequestBody  videoEntries, @Part("maxLength") RequestBody  maxLength,
                                          @Part MultipartBody.Part  bannerImage, @Part MultipartBody.Part  challVideo);
    @FormUrlEncoded
    @POST("audience")
    Call<ChallengeResponse> saveAudience(@Field("challengeId") String challengeId,@Field("createdBy") String createdBy,@Field("amount") String amount,@Field("keyDescription") String keyDescription,
                                        @Field("priceWorth") String priceWorth,@Field("totalPrize") String totalPrize,
                                        @Field("winners") String winners,@Field("radar") String radar,@Field("audZipcode") String audZipcode,
                                        @Field("audMiles") String audMiles,@Field("personalAddress") String personalAddress,@Field("categories") String categories,
                                        @Field("brands") String brands,
                                        @Field("ageStart") String ageStart,@Field("ageEnd") String ageEnd,@Field("gender") String gender);

    @FormUrlEncoded
    @POST("campaign")
    Call<ChallengeResponse> saveCampaign(@Field("challengeId") String challengeId,@Field("createdBy") String createdBy,@Field("budget") String budget,@Field("summery") String summery);

    @GET("allcategories")
    Call<CategoryList> getAllCategories();

    @GET("{challenges}")
    Call<ChallengeListResponse> getChallenges(@Path("challenges") String challenges);

    @FormUrlEncoded
    @POST("sizeofaudience")
    Call<ResponseBody> calculateAudience(@Field("radar") String radar,@Field("gender") String gender,@Field("ageStart") String ageStart,@Field("ageEnd") String ageEnd);

    @Multipart
    @POST("gamemaster")
    Call<GameMasterResult> saveGameMaster(@Part MultipartBody.Part  logoImage, @Part("createdBy") RequestBody userId, @Part("firstName") RequestBody firstName, @Part("lastName") RequestBody lastName,
                                          @Part("dob") RequestBody dob, @Part("country") RequestBody  country, @Part("city") RequestBody  city, @Part("zipcode") RequestBody  zipcode,
                                          @Part("street") RequestBody  street, @Part("streetNumber") RequestBody  streetNumber, @Part("mobileNumber") RequestBody  mobile, @Part("gender") RequestBody  gender);


    @Multipart
    @POST("jackpot")
    Call<GameMasterResult> saveJackpotChallenge(@Part("createdBy") RequestBody  createdBy, @Part("challengeId") RequestBody  challengeId, @Part MultipartBody.Part  bannerImage, @Part MultipartBody.Part  challVideo,
                                          @Part("challengeName") RequestBody  challName, @Part("startDate") RequestBody  startDate, @Part("startTime") RequestBody  startTime,
                                          @Part("endDate") RequestBody  endDate, @Part("endTime") RequestBody  endTime, @Part("description") RequestBody  description, @Part("photoEntries") RequestBody  photoEntries,
                                          @Part("videoEntries") RequestBody  videoEntries,  @Part("miniGame") RequestBody  miniGame,@Part("maxLength") RequestBody  maxLength );

    @FormUrlEncoded
    @POST("jackpotaudience")
    Call<GameMasterResult> saveJackpotAudience(@Field("challengeId") String challengeId,@Field("createdBy") String createdBy,@Field("amount") String amount,
                                                @Field("keyDescription") String keyDescription,@Field("priceWorth") String priceWorth,@Field("limited") String limited,
                                               @Field("winners") String winners,@Field("radar") String radar,@Field("audZipcode") String audZipcode,
                                              @Field("audMiles") String audMiles,@Field("address") String personalAddress,@Field("categories") String categories,
                                             @Field("brands") String brands,
                                             @Field("ageStart") String ageStart,@Field("ageEnd") String ageEnd,@Field("audGender") String gender);
     @GET("home")
     Call<HomeResponse> getHomeData();


    @Multipart
    @POST("addFeed")
    Call<ResponseBody>   postforChallenge(@Part("challengeId") RequestBody  challengeId,
                                              @Part("userId") RequestBody  userId,
                                              @Part("type") RequestBody  type,
                                              @Part MultipartBody.Part  content,
                                              @Part("postDescription") RequestBody mpostDes);
    @FormUrlEncoded
    @POST("recentposts")
    Call<RecentPostsResponce> getRecentPostsOfChallenge(@Field("challengeId") String challengeId,@Field("type") String type);


    @GET("feeds")
    Call<ChallengeFeeds> getFeeds();

    @GET("challengefeeds")
    Call<ChallengeFeeds> getChallengeFeeds();
      @FormUrlEncoded
     @POST("feeds")
     Call<FeedsResponse> loadFeeds(@Field("type") String type ,@Field("offset") String offset,@Field("limit") String limit);
    @FormUrlEncoded
    @POST("likeFeed")
    Call<ResponseBody> likeFeed(@Field("userId") String userId,@Field("postId") String postId);

    @FormUrlEncoded
    @POST("views")
    Call<ResponseBody> feedViewed(@Field("userId") String userId,@Field("postId") String postId);
    @FormUrlEncoded
    @POST("followers")
    Call<ResponseBody> followUser(@Field("userId") String userId,@Field("fromId") String fromId);


    @POST("videolength")
    @FormUrlEncoded
    Call<ResponseBody> getVideolength(@Field("name") String videoname);

    @POST("profile")
    @FormUrlEncoded
    Call<ProfileResponse> getProfile(@Field("userId") String userId);

    @Streaming
    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);

    @FormUrlEncoded
    @POST("comments")
    Call<CommentsResponce> getComments(@Field("postId") String postId, @Field("limit") String limit, @Field("offset") String offset);

    @FormUrlEncoded
    @POST("addComment")
    Call<ResponseBody> addComments(@Field("postId") String postId,@Field("userId") String userId,@Field("comment") String comment);

    @FormUrlEncoded
    @POST("replyComment")
    Call<ResponseBody> replyComment(@Field("postId") String postId,@Field("userId") String userId,@Field("comment") String comment,@Field("commentId") String commentId);

    @GET("rules")
   Call<RulesResponse> getRules();

    @FormUrlEncoded
    @POST("companyInfo")
    Call<CompanyDetails> getCompanyDetails(@Field("companyId") String companyId);

    @FormUrlEncoded
    @POST("profileVisibility")
    Call<LoginSuccess> changeVisibility(@Field("userId") String userId,@Field("profileVisits") String profileVisits,
                                     @Field("rewards") String rewards,@Field("friends") String friends,
                                     @Field("showmeRadar") String showmeRadar,@Field("country") String country,
                                     @Field("city") String city,@Field("categories") String categories,@Field("age") String age);
    @FormUrlEncoded
    @POST("notification")
    Call<LoginSuccess> changeNotification(@Field("userId") String userId,@Field("pFollows") String pFollows,
                                     @Field("pLikes") String pLikes,@Field("pComments") String pComments,
                                     @Field("pMessages") String pMessages,@Field("cCategory") String cCategory,
                                     @Field("cRadar") String cRadar,@Field("cFollows") String cFollows,@Field("cInvitation") String cInvitation,@Field("rPeople") String rPeople);
    @FormUrlEncoded
    @POST("winners")
    Call<WinnersResponce> getWinnersList(@Field("challengeId") String challengeId,@Field("type") String type);

     @FormUrlEncoded
    @POST("userchallenges")
    Call<ChallengesResponse> getMyChallenges(@Field("userId") String userId,@Field("limit") String limit,@Field("offset") String offset);

    @FormUrlEncoded
    @POST("bestposts")
    Call<BestPostsResponse>   getBestPosts(@Field("userId") String userId,@Field("limit") String limit,@Field("offset") String offset);

    @FormUrlEncoded
    @POST("newposts")
    Call<NewPostsResponse>    getNewPosts(@Field("userId") String userId, @Field("limit") String limit, @Field("offset") String offset);

    @FormUrlEncoded
    @POST("savechallenge")
    Call<ResponseBody> saveToMyChallenges(@Field("userId") String userId,@Field("challengeId") String challengeId,@Field("type") String type );


    @FormUrlEncoded
    @POST("reportChallenge")
    Call<ResponseBody> reportChallenge(@Field("userId") String userId,@Field("challengeId") String challengeId,@Field("type") String type );

    @FormUrlEncoded
    @POST("reportFeed")
    Call<ResponseBody> reportFeed(@Field("userId") String userId,@Field("postId") String postId );

    @FormUrlEncoded
    @POST()
    Call<ViewAllChallengesResponse> viewAllChallenges(@Url String apiname,
                                                      @Field("userId") String userId,
                                                      @Field("limit") String limit,
                                                      @Field("offset") String offset);
}

