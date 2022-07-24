package com.luxlunaris.studybuddy.model.challenge.classes;

import com.luxlunaris.studybuddy.model.challenge.AbstractChallenge;
import com.luxlunaris.studybuddy.model.challenge.ChallengeTypes;
import com.luxlunaris.studybuddy.model.utils.Keywords;

import java.util.List;

public class SingleAnswerChallenge extends AbstractChallenge {

    public final String answer;

    public SingleAnswerChallenge(String question, String answer, String fileName) {
        super(ChallengeTypes.SINGLE_ANSWER, question, fileName);
        this.answer = answer;
        this.answerKeywords.addAll(Keywords.extractKeywords(answer));
    }

    public String getAnswer(){
        return answer;
    }


}
