package com.android.shooshoo.views;

import com.android.shooshoo.models.Category;
import com.android.shooshoo.models.City;
import com.android.shooshoo.models.Country;
import com.android.shooshoo.models.Language;
import com.android.shooshoo.models.Region;
import com.android.shooshoo.presenters.DataLoadPresenter;

import java.util.List;

public interface DataLoadView  extends BaseView {
    /**
     * to get cities list onCitiesData is used by {@link DataLoadPresenter}
     * @param cities resulted Cites of selected country
     */
    void onCitiesData(List<City> cities);

    /**onCountryData is used by {@link DataLoadPresenter}
     * onCountryData country list is passed on this method
     * @param countries county list
     */
    void onCountryData(List<Country> countries);

    /**
     * onAllCategories is used by {@link DataLoadPresenter}
     * @param categories are all categories and sub brands of the Category
     */
    void onAllCategories(List<Category> categories);

    /**
     * onRegionList is called from {@link DataLoadPresenter}
     * @param regions list of the regions
     */
    void onRegionList(List<Region> regions);

    /**
     * onRegionList is called from {@link DataLoadPresenter}
     * @param languages list of the languages
     */
    void onLanguages(List<Language> languages);





}
