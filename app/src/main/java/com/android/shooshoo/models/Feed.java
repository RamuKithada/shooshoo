package com.android.shooshoo.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static com.android.shooshoo.utils.ApiUrls.SPONSOR_FEEDS_VIDEO_URL;
public class Feed implements Parcelable {

    @SerializedName("postId")
    @Expose
    private String id;
    @SerializedName("challengeId")
    @Expose
    private String challengeId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("postDescription")
    @Expose
    private String postDescription;
    @SerializedName("createdOn")
    @Expose
    private String createdOn;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("likes")
    @Expose
    private String likes;

    @SerializedName("views")
    @Expose
    private String views;

    @SerializedName("followers")
    @Expose
    private  String followers;

    @SerializedName("likestatus")
    @Expose
    private  String likestatus;

    @SerializedName("follwerstatus")
    @Expose
    private  String follwerstatus;

    @SerializedName("viewstatus")
    @Expose
    private  String viewstatus;

    protected Feed(Parcel in) {
        id = in.readString();
        challengeId = in.readString();
        userId = in.readString();
        type = in.readString();
        url = in.readString();
        thumbnail = in.readString();
        postDescription = in.readString();
        createdOn = in.readString();
        userName = in.readString();
        image = in.readString();
        likes = in.readString();
        views = in.readString();
        followers = in.readString();
        likestatus = in.readString();
        follwerstatus = in.readString();
        viewstatus = in.readString();
    }

    public static final Creator<Feed> CREATOR = new Creator<Feed>() {
        @Override
        public Feed createFromParcel(Parcel in) {
            return new Feed(in);
        }

        @Override
        public Feed[] newArray(int size) {
            return new Feed[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getLikestatus() {
        return likestatus;
    }

    public void setLikestatus(String likestatus) {
        this.likestatus = likestatus;
    }

    public String getFollwerstatus() {
        return follwerstatus;
    }

    public void setFollwerstatus(String follwerstatus) {
        this.follwerstatus = follwerstatus;
    }

    public String getViewstatus() {
        return viewstatus;
    }

    public void setViewstatus(String viewstatus) {
        this.viewstatus = viewstatus;
    }

    public String baseUrl(){
        return SPONSOR_FEEDS_VIDEO_URL+getType()+"/"+getChallengeId()+"/";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(challengeId);
        dest.writeString(userId);
        dest.writeString(type);
        dest.writeString(url);
        dest.writeString(thumbnail);
        dest.writeString(postDescription);
        dest.writeString(createdOn);
        dest.writeString(userName);
        dest.writeString(image);
        dest.writeString(likes);
        dest.writeString(views);
        dest.writeString(followers);
        dest.writeString(likestatus);
        dest.writeString(follwerstatus);
        dest.writeString(viewstatus);
    }
}