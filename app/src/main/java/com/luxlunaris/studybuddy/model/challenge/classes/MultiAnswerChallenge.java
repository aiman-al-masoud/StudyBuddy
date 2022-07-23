package com.luxlunaris.studybuddy.model.challenge.classes;

import com.luxlunaris.studybuddy.model.challenge.AbstractChallenge;
import com.luxlunaris.studybuddy.model.challenge.ChallengeTypes;

import java.util.List;

public class MultiAnswerChallenge extends AbstractChallenge {

    public final List<String> answersList;

    public MultiAnswerChallenge(String question, List<String> keywords, List<String> answersList, String fileName) {
        super(ChallengeTypes.MULTI_ANSWER, question, keywords, fileName);
        this.answersList = answersList;
    }

    public List<String> getAnswersList() {
        return answersList;
    }

}
