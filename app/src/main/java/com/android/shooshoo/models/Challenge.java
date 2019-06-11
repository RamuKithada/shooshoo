package com.android.shooshoo.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Challenge {

    @SerializedName("challengeId")
    @Expose
    private String challengeId;
    @SerializedName("challengeName")
    @Expose
    private String challengeName;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("startTime")
    @Expose
    private String startTime;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("endTime")
    @Expose
    private String endTime;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("photoEntries")
    @Expose
    private String photoEntries;
    @SerializedName("videoEntries")
    @Expose
    private String videoEntries;
    @SerializedName("bannerImage")
    @Expose
    private String bannerImage;
    @SerializedName("challengeVideo")
    @Expose
    private String challengeVideo;
    @SerializedName("maxLength")
    @Expose
    private String maxLength;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("keyDescription")
    @Expose
    private String keyDescription;
    @SerializedName("priceWorth")
    @Expose
    private String priceWorth;
    @SerializedName("totalPrize")
    @Expose
    private String totalPrize;
    @SerializedName("winners")
    @Expose
    private String winners;
    @SerializedName("radar")
    @Expose
    private String radar;
    @SerializedName("audZipcode")
    @Expose
    private String audZipcode;
    @SerializedName("audMiles")
    @Expose
    private String audMiles;
    @SerializedName("personalAddress")
    @Expose
    private String personalAddress;
    @SerializedName("categories")
    @Expose
    private String categories;
    @SerializedName("brands")
    @Expose
    private String brands;
    @SerializedName("ageStart")
    @Expose
    private String ageStart;
    @SerializedName("ageEnd")
    @Expose
    private String ageEnd;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("participants")
    @Expose
    private String participants;
    @SerializedName("budget")
    @Expose
    private String budget;
    @SerializedName("summery")
    @Expose
    private String summery;
    @SerializedName("sponsoredBy")
    @Expose
    private String sponsoredBy;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("createdOn")
    @Expose
    private String createdOn;
    @SerializedName("updatedOn")
    @Expose
    private String updatedOn;
    @SerializedName("status")
    @Expose
    private String status;

    private String userFirst;
    @SerializedName("userLast")
    @Expose
    private String userLast;
    @SerializedName("companies")
    @Expose
    private List<Company> companies = null;

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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoEntries() {
        return photoEntries;
    }

    public void setPhotoEntries(String photoEntries) {
        this.photoEntries = photoEntries;
    }

    public String getVideoEntries() {
        return videoEntries;
    }

    public void setVideoEntries(String videoEntries) {
        this.videoEntries = videoEntries;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public String getChallengeVideo() {
        return challengeVideo;
    }

    public void setChallengeVideo(String challengeVideo) {
        this.challengeVideo = challengeVideo;
    }

    public String getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(String maxLength) {
        this.maxLength = maxLength;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getKeyDescription() {
        return keyDescription;
    }

    public void setKeyDescription(String keyDescription) {
        this.keyDescription = keyDescription;
    }

    public String getPriceWorth() {
        return priceWorth;
    }

    public void setPriceWorth(String priceWorth) {
        this.priceWorth = priceWorth;
    }

    public String getTotalPrize() {
        return totalPrize;
    }

    public void setTotalPrize(String totalPrize) {
        this.totalPrize = totalPrize;
    }

    public String getWinners() {
        return winners;
    }

    public void setWinners(String winners) {
        this.winners = winners;
    }

    public String getRadar() {
        return radar;
    }

    public void setRadar(String radar) {
        this.radar = radar;
    }

    public String getAudZipcode() {
        return audZipcode;
    }

    public void setAudZipcode(String audZipcode) {
        this.audZipcode = audZipcode;
    }

    public String getAudMiles() {
        return audMiles;
    }

    public void setAudMiles(String audMiles) {
        this.audMiles = audMiles;
    }

    public String getPersonalAddress() {
        return personalAddress;
    }

    public void setPersonalAddress(String personalAddress) {
        this.personalAddress = personalAddress;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getBrands() {
        return brands;
    }

    public void setBrands(String brands) {
        this.brands = brands;
    }

    public String getAgeStart() {
        return ageStart;
    }

    public void setAgeStart(String ageStart) {
        this.ageStart = ageStart;
    }

    public String getAgeEnd() {
        return ageEnd;
    }

    public void setAgeEnd(String ageEnd) {
        this.ageEnd = ageEnd;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getSummery() {
        return summery;
    }

    public void setSummery(String summery) {
        this.summery = summery;
    }

    public String getSponsoredBy() {
        return sponsoredBy;
    }

    public void setSponsoredBy(String sponsoredBy) {
        this.sponsoredBy = sponsoredBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    public String getUserFirst() {
        return userFirst;
    }

    public void setUserFirst(String userFirst) {
        this.userFirst = userFirst;
    }

    public String getUserLast() {
        return userLast;
    }

    public void setUserLast(String userLast) {
        this.userLast = userLast;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }
}