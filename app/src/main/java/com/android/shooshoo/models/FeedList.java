package com.android.shooshoo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeedList {
    @SerializedName("sponsor")
    @Expose
    private List<Feed> sponsor = null;

    @SerializedName("jackpot")
    @Expose
    private List<Feed> jackpot = null;


    @SerializedName("friends")
    @Expose
    private List<Feed> friends = null;

    @SerializedName("new")
    @Expose
    private List<Feed> newList = null;

    @SerializedName("popular")
    @Expose
    private List<Feed> popular = null;


    @SerializedName("random")
    @Expose
    private List<Feed> random = null;

    public List<Feed> getSponsor() {
        return sponsor;
    }

    public void setSponsor(List<Feed> sponsor) {
        this.sponsor = sponsor;
    }

    public List<Feed> getJackpot() {
        return jackpot;
    }

    public void setJackpot(List<Feed> jackpot) {
        this.jackpot = jackpot;
    }

    public List<Feed> getFriends() {
        return friends;
    }

    public void setFriends(List<Feed> friends) {
        this.friends = friends;
    }

    public List<Feed> getNewList() {
        return newList;
    }

    public void setNewList(List<Feed> newList) {
        this.newList = newList;
    }

    public List<Feed> getPopular() {
        return popular;
    }

    public void setPopular(List<Feed> popular) {
        this.popular = popular;
    }

    public List<Feed> getRandom() {
        return random;
    }

    public void setRandom(List<Feed> random) {
        this.random = random;
    }
}
