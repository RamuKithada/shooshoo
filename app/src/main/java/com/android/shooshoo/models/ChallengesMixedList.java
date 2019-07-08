package com.android.shooshoo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChallengesMixedList {

    @SerializedName("sponsor")
    @Expose
    private List<Challenge> sponsor = null;
    @SerializedName("jackpot")
    @Expose
    private List<Challenge> jackpot = null;

    public List<Challenge> getSponsor() {
        return sponsor;
    }

    public void setSponsor(List<Challenge> sponsor) {
        this.sponsor = sponsor;
    }

    public List<Challenge> getJackpot() {
        return jackpot;
    }

    public void setJackpot(List<Challenge> jackpot) {
        this.jackpot = jackpot;
    }
}
