package com.android.shooshoo.views;

import com.android.shooshoo.models.GameMaster;
import com.android.shooshoo.models.GameMasterResult;

public interface JackpotChallengeView extends BaseView {
    void onGameMasterCreate(GameMaster result);
}
