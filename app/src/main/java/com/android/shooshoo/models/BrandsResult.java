package com.android.shooshoo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BrandsResult{
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("count")
        @Expose
        private Integer count;
        @SerializedName("brands")
        @Expose
        private List<Company> brands = null;
        @SerializedName("companies")
        @Expose
        private List<Company> companies = null;

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

        public Integer getCount() {
           return count;
            }

        public void setCount(Integer count) {
          this.count = count;
        }

        public List<Company> getBrands() {
            return brands;
        }

        public void setBrands(List<Company> brands) {
            this.brands = brands;
        }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }
}