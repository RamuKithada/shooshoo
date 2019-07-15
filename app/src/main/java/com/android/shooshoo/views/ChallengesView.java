package com.android.shooshoo.views;

import com.android.shooshoo.models.Challenge;

import java.util.List;

public interface ChallengesView extends BaseView{

   void onSavedChallenges(List<Challenge> challengeList);
   void onEnteredChallenges(List<Challenge> challengeList);
   void onCreatedChallenges(List<Challenge> challengeList);
}
