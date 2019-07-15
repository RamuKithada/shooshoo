package com.android.shooshoo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
public class CompanyDetails {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("categories")
    @Expose
    private String categories;
    @SerializedName("recent")
    @Expose
    private List<Challenge> recent = null;
    @SerializedName("challenges")
    @Expose
    private List<Challenge> challenges = null;
    @SerializedName("finished")
    @Expose
    private List<Challenge> finished = null;




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

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public List<Challenge> getRecent() {
        return recent;
    }

    public void setRecent(List<Challenge> recent) {
        this.recent = recent;
    }

    public List<Challenge> getFinished() {
        return finished;
    }

    public void setFinished(List<Challenge> finished) {
        this.finished = finished;
    }

    public List<Challenge> getChallenges() {
        return challenges;
    }

    public void setChallenges(List<Challenge> challenges) {
        this.challenges = challenges;
    }
}