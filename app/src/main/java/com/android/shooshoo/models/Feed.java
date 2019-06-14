package com.android.shooshoo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Feed {
        @SerializedName("id")
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
        @SerializedName("createdOn")
        @Expose
        private String createdOn;

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

        public String getCreatedOn() {
        return createdOn;
    }

        public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }
}
