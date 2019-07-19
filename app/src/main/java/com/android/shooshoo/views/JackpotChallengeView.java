package com.android.shooshoo.views;

import com.android.shooshoo.models.Challenge;

public interface JackpotChallengeView extends BaseView {
    /**
     * after successful call, it gives  {@link Challenge} object i.e, used to store the Jackpot Challenge
     * @param result is Information of Jackpot Challenge.
     */
    void onChallengeUpdated(Challenge result);
}
