package com.android.shooshoo.models;

public class CategoryModel {
    int category=0,subcategory=0;

    public CategoryModel(int category, int subcategory) {
        this.category = category;
        this.subcategory = subcategory;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(int subcategory) {
        this.subcategory = subcategory;
    }
}
