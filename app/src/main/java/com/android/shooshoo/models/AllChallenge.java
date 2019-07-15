package com.android.shooshoo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AllChallenge {
    @SerializedName("jackpot")
    @Expose
    private List<Challenge> jackpot;
    @SerializedName("sponsor")
    @Expose
    private List<Challenge> sponsor;

    private List<Challenge> challengeList;

    public List<Challenge> getJackpot() {
        return jackpot;
    }

    public void setJackpot(List<Challenge> jackpot) {
        this.jackpot = jackpot;
    }

    public List<Challenge> getSponsor() {
        return sponsor;
    }

    public void setSponsor(List<Challenge> sponsor) {
        this.sponsor = sponsor;
    }

    public List<Challenge> getChallengeList() {
        challengeList=new ArrayList<Challenge>();
          if(getJackpot()!=null)
              challengeList.addAll(getJackpot());

        if(getSponsor()!=null)
            challengeList.addAll(getSponsor());

        return challengeList;
    }

    public void setChallengeList(List<Challenge> challengeList) {
        this.challengeList = challengeList;
    }
}
