package com.android.shooshoo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
public class ChallengeResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Challenge challenge;
    @SerializedName("past")
    @Expose
    private List<Challenge> past;
    @SerializedName("latest")
    @Expose
    private List<Challenge> latest;


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

    public Challenge getData() {
        return challenge;
    }

    public void setData(Challenge data) {
        this.challenge = data;
    }

    public List<Challenge> getPast() {
        return past;
    }

    public void setPast(List<Challenge> past) {
        this.past = past;
    }

    public List<Challenge> getLatest() {
        return latest;
    }

    public void setLatest(List<Challenge> latest) {
        this.latest = latest;
    }
}