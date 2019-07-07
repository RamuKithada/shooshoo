package com.android.shooshoo.views;

import com.android.shooshoo.models.Winner;

import java.util.List;

/**
 * {@link WinnersListView} is implemented by {@link com.android.shooshoo.presenters.WinnersListActivity}
 * to interact with activity
 */
public interface WinnersListView extends BaseView {
    void onWinnersListresult(List<Winner> winners);
}
