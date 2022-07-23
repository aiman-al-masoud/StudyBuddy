package com.luxlunaris.studybuddy.model.challenge;

import java.util.List;

public abstract class AbstractChallenge implements Challenge{

    public final ChallengeTypes type;
    public final String question;
    public final List<String> keywords;
    public final String fileName;

    public AbstractChallenge(ChallengeTypes type, String question, List<String> keywords, String fileName){
        this.type = type;
        this.question = question;
        this.keywords = keywords;
        this.fileName = fileName;
    }

    @Override
    public ChallengeTypes getType() {
        return type;
    }


    @Override
    public List<String> getKeywords() {
        return keywords;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public String getFileName() {
        return fileName;
    }
}
