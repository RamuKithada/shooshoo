package com.android.shooshoo.views;

import com.android.shooshoo.models.Category;
import com.android.shooshoo.models.City;
import com.android.shooshoo.models.Country;

import java.util.List;

public interface DataLoadView  extends BaseView {

    void onCitiesData(List<City> cities);
    void onCountryData(List<Country> countries);
    void onAllCategories(List<Category> categories);

}
