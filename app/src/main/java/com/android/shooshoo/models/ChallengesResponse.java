package com.android.shooshoo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChallengesResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("entered")
    @Expose
    private ChallengesMixedList entered;
    @SerializedName("created")
    @Expose
    private ChallengesMixedList created;
    @SerializedName("saved")
    @Expose
    private ChallengesMixedList saved;

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

    public ChallengesMixedList getEntered() {
        return entered;
    }

    public void setEntered(ChallengesMixedList entered) {
        this.entered = entered;
    }

    public ChallengesMixedList getCreated() {
        return created;
    }

    public void setCreated(ChallengesMixedList created) {
        this.created = created;
    }

    public ChallengesMixedList getSaved() {
        return saved;
    }

    public void setSaved(ChallengesMixedList saved) {
        this.saved = saved;
    }
}
