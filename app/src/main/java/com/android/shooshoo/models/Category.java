package com.android.shooshoo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Category {

    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("createdOn")
    @Expose
    private String createdOn;
    @SerializedName("updatedOn")
    @Expose
    private String updatedOn;
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("brands")
    @Expose
    private List<Brand> brands = null;

    private int brandPosition;
    private int selected=0;

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public int getBrandPosition() {
        return brandPosition;
    }

    public void setBrandPosition(int brandPosition) {
        this.brandPosition = brandPosition;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public List<Brand> getBrands() {
        return brands;
    }

    public List<String> getBrandNames(){
        ArrayList<String> names=new ArrayList<String>();
        for (Brand brand:getBrands())
            names.add(brand.getBrandName());
       return names;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }
}