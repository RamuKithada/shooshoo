
package com.android.shooshoo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Winner {

    @SerializedName("rank")
    @Expose
    private Integer rank;
    @SerializedName("amount")
    @Expose
    private String money;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("image")
    @Expose
    private String userImage;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("postId")
    @Expose
    private String postId;
    @SerializedName("challengeId")
    @Expose
    private String challengeId;
    @SerializedName("challengeName")
    @Expose
    private String challengeName;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("likes")
    @Expose
    private String likes;
    @SerializedName("views")
    @Expose
    private String views;

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId;
    }

    public String getChallengeName() {
        return challengeName;
    }

    public void setChallengeName(String challengeName) {
        this.challengeName = challengeName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

}