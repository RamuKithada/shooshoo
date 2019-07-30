
package com.android.shooshoo.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("sponsor")
    @Expose
    private List<Challenge> sponsorChallenges = null;

    @SerializedName("jackpot")
    @Expose
    private List<Challenge> jackpotsChallenges = null;

    @SerializedName("private")
    @Expose
    private List<Challenge> privateChallenges = null;

    @SerializedName("finalcall")
    @Expose
    private List<Challenge> finalcall = null;
    @SerializedName("brands")
    @Expose
    private List<Company> brands = null;
    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Challenge> getSponsorChallenges() {
                  return sponsorChallenges;
    }

    public void setSponsorChallenges(List<Challenge> sponsors) {
        this.sponsorChallenges = sponsors;
    }

    public List<Challenge> getJackpotsChallenges() {
        return jackpotsChallenges;
    }

    public void setJackpotsChallenges(List<Challenge> jackpotsChallenges) {
        this.jackpotsChallenges = jackpotsChallenges;
    }

    public List<Challenge> getPrivateChallenges() {
        return privateChallenges;
    }

    public void setPrivateChallenges(List<Challenge> privateChallenges) {
        this.privateChallenges = privateChallenges;
    }

    public List<Challenge> getFinalcall() {
        return finalcall;
    }

    public void setFinalcall(List<Challenge> finalcall) {
        this.finalcall = finalcall;
    }

    public List<Company> getBrands() {
        return brands;
    }

    public void setBrands(List<Company> brands) {
        this.brands = brands;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
