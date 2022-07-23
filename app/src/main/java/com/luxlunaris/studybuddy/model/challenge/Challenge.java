package com.luxlunaris.studybuddy.model.challenge;

import java.util.List;

public interface Challenge {

    String getQuestion();
    List<String> getKeywords();
    ChallengeTypes getType();
    String getFileName();

}
