package com.android.shooshoo.models;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Challenge implements Parcelable {

    @SerializedName("challengeId")
    @Expose
    private String challengeId;
    @SerializedName("challengeName")
    @Expose
    private String challengeName;
    @SerializedName("type")
    @Expose
    private String type;
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
    @SerializedName("prizesInfo")
    @Expose
    private String prizesInfo;
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
    @SerializedName("audCountry")
    @Expose
    private String audCountry;
    @SerializedName("audCity")
    @Expose
    private String audCity;
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
    private String sponsoredBy=null;
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
    @SerializedName("userFirst")
    @Expose
    private String userFirst;
    @SerializedName("userLast")
    @Expose
    private String userLast;


    /***
     *   These are related to jackpot challenges parameters
     *
     */

    @SerializedName("masterLogo")
    @Expose
    private String masterLogo;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("zipcode")
    @Expose
    private String zipcode;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("streetNumber")
    @Expose
    private String streetNumber;
    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;

    @SerializedName("limited")
    @Expose
    private String limited;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("audGender")
    @Expose
    private String audGender;
///Sponsored CompanyList
    @SerializedName("companies")
    @Expose
    private List<Company> companies = null;

    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("fullName")
    @Expose
    private String fullName;

    protected Challenge(Parcel in) {
        challengeId = in.readString();
        challengeName = in.readString();
        type = in.readString();
        startDate = in.readString();
        startTime = in.readString();
        endDate = in.readString();
        endTime = in.readString();
        description = in.readString();
        photoEntries = in.readString();
        videoEntries = in.readString();
        bannerImage = in.readString();
        challengeVideo = in.readString();
        maxLength = in.readString();
        amount = in.readString();
        keyDescription = in.readString();
        prizesInfo = in.readString();
        totalPrize = in.readString();
        winners = in.readString();
        radar = in.readString();
        audZipcode = in.readString();
        audMiles = in.readString();
        personalAddress = in.readString();
        categories = in.readString();
        brands = in.readString();
        ageStart = in.readString();
        ageEnd = in.readString();
        gender = in.readString();
        participants = in.readString();
        budget = in.readString();
        summery = in.readString();
        sponsoredBy = in.readString();
        createdBy = in.readString();
        createdOn = in.readString();
        updatedOn = in.readString();
        status = in.readString();
        userFirst = in.readString();
        userLast = in.readString();
        masterLogo = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        dob = in.readString();
        country = in.readString();
        city = in.readString();
        zipcode = in.readString();
        street = in.readString();
        streetNumber = in.readString();
        mobileNumber = in.readString();
        limited = in.readString();
        address = in.readString();
        audGender = in.readString();
        userName = in.readString();
        fullName = in.readString();
    }

    public static final Creator<Challenge> CREATOR = new Creator<Challenge>() {
        @Override
        public Challenge createFromParcel(Parcel in) {
            return new Challenge(in);
        }

        @Override
        public Challenge[] newArray(int size) {
            return new Challenge[size];
        }
    };

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

    public String getPrizesInfo() {
        return prizesInfo;
    }

    public void setPrizesInfo(String prizesInfo) {
        this.prizesInfo = prizesInfo;
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

    public String getMasterLogo() {
        return masterLogo;
    }

    public void setMasterLogo(String masterLogo) {
        this.masterLogo = masterLogo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }



    public String getLimited() {
        return limited;
    }

    public void setLimited(String limited) {
        this.limited = limited;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAudGender() {
        return audGender;
    }

    public void setAudGender(String audGender) {
        this.audGender = audGender;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(challengeId);
        dest.writeString(challengeName);
        dest.writeString(type);
        dest.writeString(startDate);
        dest.writeString(startTime);
        dest.writeString(endDate);
        dest.writeString(endTime);
        dest.writeString(description);
        dest.writeString(photoEntries);
        dest.writeString(videoEntries);
        dest.writeString(bannerImage);
        dest.writeString(challengeVideo);
        dest.writeString(maxLength);
        dest.writeString(amount);
        dest.writeString(keyDescription);
        dest.writeString(prizesInfo);
        dest.writeString(totalPrize);
        dest.writeString(winners);
        dest.writeString(radar);
        dest.writeString(audZipcode);
        dest.writeString(audMiles);
        dest.writeString(personalAddress);
        dest.writeString(categories);
        dest.writeString(brands);
        dest.writeString(ageStart);
        dest.writeString(ageEnd);
        dest.writeString(gender);
        dest.writeString(participants);
        dest.writeString(budget);
        dest.writeString(summery);
        dest.writeString(sponsoredBy);
        dest.writeString(createdBy);
        dest.writeString(createdOn);
        dest.writeString(updatedOn);
        dest.writeString(status);
        dest.writeString(userFirst);
        dest.writeString(userLast);
        dest.writeString(masterLogo);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(dob);
        dest.writeString(country);
        dest.writeString(city);
        dest.writeString(zipcode);
        dest.writeString(street);
        dest.writeString(streetNumber);
        dest.writeString(mobileNumber);
        dest.writeString(limited);
        dest.writeString(address);
        dest.writeString(audGender);
        dest.writeString(userName);
        dest.writeString(fullName);
    }
}