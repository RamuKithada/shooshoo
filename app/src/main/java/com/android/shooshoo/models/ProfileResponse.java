
package com.android.shooshoo.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("bankDetails")
    UserBankDetails bankDetails;

    @SerializedName("userinfo")
    @Expose
    private UserInfo userInfo;
    @SerializedName("brands")
    @Expose
    private List<Brand> brands = null;
    @SerializedName("posts")
    @Expose
    private List<Post> posts = null;

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

    public UserBankDetails getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(UserBankDetails bankDetails) {
        this.bankDetails = bankDetails;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

}
