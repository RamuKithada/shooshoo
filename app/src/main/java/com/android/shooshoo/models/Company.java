package com.android.shooshoo.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Company {

        @SerializedName("companyId")
        @Expose
        private String companyId;
        @SerializedName("companyLogo")
        @Expose
        private String companyLogo;
        @SerializedName("companyName")
        @Expose
        private String companyName;
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
        @SerializedName("taxNumber")
        @Expose
        private String taxNumber;
        @SerializedName("firstName")
        @Expose
        private String firstName;
        @SerializedName("lastName")
        @Expose
        private String lastName;
        @SerializedName("emailId")
        @Expose
        private String emailId;
        @SerializedName("companyEmail")
        @Expose
        private String companyEmail;

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

        public String getCompanyId() {
        return companyId;
    }

        public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

        public String getCompanyLogo() {
        return companyLogo;
    }

        public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

        public String getCompanyName() {
        return companyName;
    }

        public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

        public String getTaxNumber() {
        return taxNumber;
    }

        public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
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

        public String getEmailId() {
        return emailId;
    }

        public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
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

    }