package com.luxlunaris.studybuddy.model.challenge;

import java.util.List;

public interface Challenge {

    String question();
    List<String> allKeywords();
    List<String> questionKeywords();
    List<String> answerKeywords();
    ChallengeTypes type();
    String fileName();
    String answer();

}
