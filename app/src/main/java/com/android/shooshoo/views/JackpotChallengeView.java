package com.android.shooshoo.views;

import com.android.shooshoo.models.GameMaster;
import com.android.shooshoo.models.GameMasterResult;

public interface JackpotChallengeView extends BaseView {
    /**
     * after successful call, it gives  {@link GameMaster} object i.e, used to store the Jackpot Challenge
     * @param result is Information of Jackpot Challenge.
     */
    void onGameMasterCreate(GameMaster result);
}
