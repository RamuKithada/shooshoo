package com.android.shooshoo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.NotificationSettings;
import com.android.shooshoo.models.UserInfo;
import com.android.shooshoo.models.Visibility;
import com.google.gson.Gson;

/****
 * Preference to store User data
 */
public class UserSession {
    private static final String PREF_NAME = "shooshoo";
    // Shared Preferences
    private SharedPreferences pref;
    // Editor for Shared preferences
    private   SharedPreferences.Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    static int PRIVATE_MODE = 0;

    private static String USER_ID ="user_id";
    private static String IS_LOGIN="login";
    private static final String PASSWORD = "password";
    private static final String TOKEN="token";
    private static final String NOTIFICATION_STATE="notification_state";
    private static final String CAT_IDS="cat_ids";
    private static final String SPONSOR_CHALLENGE_ID="sponsor_challenge_id";
    private static final String SPONSOR_BY="sponsor_by";
    private static final String AUD_SIZE="aud_size";


    public UserSession(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**saved to future usage
     *
     * @param userId is logged in user id
     */
    public  void setUserId(String userId) {
            putString(USER_ID,userId);
    }

    public  String getUserId() {
        return getString(USER_ID);
    }

    public  Boolean isLogin() {
        return pref.getBoolean(IS_LOGIN,false);
    }


    public void  login(){
        editor.putBoolean(IS_LOGIN,true);
        editor.commit();
    }


    private void putString(String key, String value){
        String storing;
        try {
           storing= AnotherEncryptClass.encryptStrAndToBase64(key,value);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        editor.putString(key,storing);
        editor.commit();
    }



    private String getString(String key){
        String storing=null;
        String value=pref.getString(key,null);

        if(value==null)
            return storing;
        try {
            storing= AnotherEncryptClass.decryptStrAndFromBase64(key,value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return storing;
    }
public  void logout(){
        pref.edit().clear().commit();
    }

    public void setPassword(String password) {
       putString(PASSWORD,password);

    }

    public  String getPassword() {
        return getString(PASSWORD);
    }

    public void storeToken(String token) {
        editor.putString(TOKEN,token);
        editor.commit();

    }
    public String getToken(){
        return  pref.getString(TOKEN,"123456789");

    }
    public int getNotificationState(){
      return   pref.getInt(NOTIFICATION_STATE,1);
    }
    public void setNotificationState(int state){
        editor.putInt(NOTIFICATION_STATE,state);

        editor.commit();
    }

    /**
     *
     * @param cats ids of the categories that are selected
     */
    public void setCats(String cats){
        putString(CAT_IDS,cats);
    }
    public String getCats() {
        return getString(CAT_IDS);
    }
    String SponsorChallenge;

    /**
     *
     * @return JsonString of the Sponsor Challenge
     */
    public String getSponsorChallenge() {
        return getString(SPONSOR_CHALLENGE_ID);
    }

    /**
     *
     * @param sponsorChallenge Json Fromated String to store in preferences
     */
    public void setSponsorChallenge(String sponsorChallenge) {
        if(sponsorChallenge==null)
            pref.edit().remove(SPONSOR_CHALLENGE_ID).commit();

      putString(SPONSOR_CHALLENGE_ID,sponsorChallenge);
    }
    public void savaChallenge(Challenge company) {
        if(company==null)
           pref.edit().remove("sponsor_challenge").commit();

        putString("sponsor_challenge", new Gson().toJson((Object) company));
    }

    public Challenge getChallnge() {
        return (Challenge) new Gson().fromJson(getString("sponsor_challenge"), Challenge.class);
    }

    /** add to preferences company ids
     *
     * @param sponsorIds Sponsored comapany id
     */
    public void setSponsorIds(String sponsorIds){
            pref.edit().putString(SPONSOR_BY,sponsorIds).commit();

    }

    /**
     *
     * @return ids of the companies ids those r sponsor the challenge
     */
    public String getSponsorIds(){
       return pref.getString(SPONSOR_BY,null);
    }

    /***
     *  save Jackpot challenge in preference
     * @param master
     */
    public void saveUserInfo(UserInfo master) {

        if(master==null)
            pref.edit().remove("user_info").commit();

        putString("user_info", new Gson().toJson((Object) master));
    }

    /***
     * this is used to get the Jackpot challenge reference to use all over step of the Challenge registration
     * @return Jackpot challenge
     */
    public UserInfo getUserInfo() {
        return (UserInfo) new Gson().fromJson(getString("user_info"), UserInfo.class);
    }

    /***
     *  save Jackpot challenge in preference
     * @param master
     */
    public void saveJackpot(Challenge master) {

        if(master==null)
            pref.edit().remove("jackpot_challenge").commit();

        putString("jackpot_challenge", new Gson().toJson((Object) master));
    }


    /***
     * this is used to get the Jackpot challenge reference to use all over step of the Challenge registration
     * @return Jackpot challenge
     */
    public Challenge getJackpot() {
        return (Challenge) new Gson().fromJson(getString("jackpot_challenge"), Challenge.class);
    }

    public void setAudSize(String audienceSize) {
        putString(AUD_SIZE,audienceSize);
    }

    public  String getAudSize() {
        return getString(AUD_SIZE) ;
    }
    public void setVisibility(Visibility visibility){
        putString("visibility", new Gson().toJson((Object) visibility));

    }

    public Visibility getVisibility() {
        return (Visibility) new Gson().fromJson(getString("visibility"), Visibility.class);
    }
    public void setNotification(NotificationSettings visibility){
        putString("notification_settings", new Gson().toJson((Object) visibility));

    }

    public NotificationSettings getNotification() {
        return (NotificationSettings) new Gson().fromJson(getString("notification_settings"), NotificationSettings.class);
    }

}
