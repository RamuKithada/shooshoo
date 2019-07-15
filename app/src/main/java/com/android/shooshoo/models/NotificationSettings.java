package com.android.shooshoo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationSettings {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("pFollows")
    @Expose
    private String pFollows;
    @SerializedName("pLikes")
    @Expose
    private String pLikes;
    @SerializedName("pComments")
    @Expose
    private String pComments;
    @SerializedName("pMessages")
    @Expose
    private String pMessages;
    @SerializedName("cCategory")
    @Expose
    private String cCategory;
    @SerializedName("cRadar")
    @Expose
    private String cRadar;
    @SerializedName("cFollows")
    @Expose
    private String cFollows;
    @SerializedName("cInvitation")
    @Expose
    private String cInvitation;
    @SerializedName("rPeople")
    @Expose
    private String rPeople;
    @SerializedName("createdOn")
    @Expose
    private String createdOn;
    @SerializedName("updatedOn")
    @Expose
    private String updatedOn;
    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPFollows() {
        return pFollows;
    }

    public void setPFollows(String pFollows) {
        this.pFollows = pFollows;
    }

    public String getPLikes() {
        return pLikes;
    }

    public void setPLikes(String pLikes) {
        this.pLikes = pLikes;
    }

    public String getPComments() {
        return pComments;
    }

    public void setPComments(String pComments) {
        this.pComments = pComments;
    }

    public String getPMessages() {
        return pMessages;
    }

    public void setPMessages(String pMessages) {
        this.pMessages = pMessages;
    }

    public String getCCategory() {
        return cCategory;
    }

    public void setCCategory(String cCategory) {
        this.cCategory = cCategory;
    }

    public String getCRadar() {
        return cRadar;
    }

    public void setCRadar(String cRadar) {
        this.cRadar = cRadar;
    }

    public String getCFollows() {
        return cFollows;
    }

    public void setCFollows(String cFollows) {
        this.cFollows = cFollows;
    }

    public String getCInvitation() {
        return cInvitation;
    }

    public void setCInvitation(String cInvitation) {
        this.cInvitation = cInvitation;
    }

    public String getRPeople() {
        return rPeople;
    }

    public void setRPeople(String rPeople) {
        this.rPeople = rPeople;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}