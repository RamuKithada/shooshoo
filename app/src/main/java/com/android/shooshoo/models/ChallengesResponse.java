package com.android.shooshoo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChallengesResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("entered")
    @Expose
    private List<Challenge> entered ;
    @SerializedName("created")
    @Expose
    private List<Challenge>  created;
    @SerializedName("saved")
    @Expose
    private List<Challenge>  saved;

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

    public List<Challenge> getEntered() {
        return entered;
    }

    public void setEntered(List<Challenge> entered) {
        this.entered = entered;
    }

    public List<Challenge> getCreated() {
        return created;
    }

    public void setCreated(List<Challenge> created) {
        this.created = created;
    }

    public List<Challenge> getSaved() {
        return saved;
    }

    public void setSaved(List<Challenge> saved) {
        this.saved = saved;
    }
}
