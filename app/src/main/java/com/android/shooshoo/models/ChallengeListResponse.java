package com.android.shooshoo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChallengeListResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("past")
    @Expose
    private AllChallenge past;
    @SerializedName("latest")
    @Expose
    private AllChallenge latest;

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

    public AllChallenge getPast() {
        return past;
    }

    public void setPast(AllChallenge past) {
        this.past = past;
    }

    public AllChallenge getLatest() {
        return latest;
    }

    public void setLatest(AllChallenge latest) {
        this.latest = latest;
    }
}
