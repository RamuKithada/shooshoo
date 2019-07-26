package com.android.shooshoo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ViewAllChallengesResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("sponsors")
    @Expose
    private List<Challenge> sponsors = null;
    @SerializedName("privates")
    @Expose
    private List<Challenge> privates = null;
    @SerializedName("jackpots")
    @Expose
    private List<Challenge> jackpots = null;
    @SerializedName("entered")
    @Expose
    private List<Challenge> entered = null;
    @SerializedName("saved")
    @Expose
    private List<Challenge> saved = null;
    @SerializedName("created")
    @Expose
    private List<Challenge> created = null;
    @SerializedName("count")
    @Expose
    private Integer count;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Challenge> getSponsors() {
        return sponsors;
    }

    public void setSponsors(List<Challenge> sponsors) {
        this.sponsors = sponsors;
    }

    public List<Challenge> getPrivates() {
        return privates;
    }

    public void setPrivates(List<Challenge> privates) {
        this.privates = privates;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Challenge> getJackpots() {
        return jackpots;
    }

    public void setJackpots(List<Challenge> jackpots) {
        this.jackpots = jackpots;
    }

    public List<Challenge> getEntered() {
        return entered;
    }

    public void setEntered(List<Challenge> entered) {
        this.entered = entered;
    }

    public List<Challenge> getSaved() {
        return saved;
    }

    public void setSaved(List<Challenge> saved) {
        this.saved = saved;
    }

    public List<Challenge> getCreated() {
        return created;
    }

    public void setCreated(List<Challenge> created) {
        this.created = created;
    }
}
