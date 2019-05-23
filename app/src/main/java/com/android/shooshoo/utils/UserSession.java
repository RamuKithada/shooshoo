package com.android.shooshoo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.shooshoo.models.Challenge;
import com.android.shooshoo.models.Company;
import com.android.shooshoo.models.GameMaster;
import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;

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


    public UserSession(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public  void setUserId(String employeeId) {
            putString(USER_ID,employeeId);
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
        return  pref.getString(TOKEN,null);

    }
    public int getNotificationState(){
      return   pref.getInt(NOTIFICATION_STATE,1);
    }
    public void setNotificationState(int state){
        editor.putInt(NOTIFICATION_STATE,state);

        editor.commit();
    }
    public void setCats(String cats){
        putString(CAT_IDS,cats);
    }
    public String getCats() {
        return getString(CAT_IDS);
    }
    String SponsorChallenge;

    public String getSponsorChallenge() {
        return getString(SPONSOR_CHALLENGE_ID);
    }


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


    public void addSponsor(String sponsorId){
        if(sponsorId==null){
            pref.edit().putStringSet(SPONSOR_BY,null).commit();
        }


        if(pref.contains(SPONSOR_BY))
        {
           Set<String> sponsors=pref.getStringSet(SPONSOR_BY,null);
           if(sponsors==null)
               sponsors=new HashSet<String>();
               sponsors.add(sponsorId);
           pref.edit().putStringSet(SPONSOR_BY,sponsors).commit();
        }else {
            Set<String> sponsors=new HashSet<String>();
            pref.edit().putStringSet(SPONSOR_BY,sponsors).commit();
        }
    }
    public Set<String> getSponsors(){
       return pref.getStringSet(SPONSOR_BY,null);
    }

    public void saveGameMaster(GameMaster master) {

        if(master==null)
            pref.edit().remove("jackpot_challenge").commit();

        putString("jackpot_challenge", new Gson().toJson((Object) master));
    }

    public GameMaster getGameMaster() {
        return (GameMaster) new Gson().fromJson(getString("jackpot_challenge"), GameMaster.class);
    }
}
