package com.luxlunaris.studybuddy.model.challenge;

import com.luxlunaris.studybuddy.model.utils.Keywords;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractChallenge implements Challenge{

    public final ChallengeTypes type;
    public final String question;
    public final String fileName;
    public final List<String> allKeywords;
    public final List<String> questionKeywords;
    public final List<String> answerKeywords;
    public final String answer;


    public AbstractChallenge(ChallengeTypes type, String question, String fileName, String answer){
        this.type = type;
        this.question = question;
        this.fileName = fileName;
        this.answer = answer;
        answerKeywords = new ArrayList<String>();
        allKeywords = new ArrayList<String>();
        questionKeywords = Keywords.extractKeywords(question);
        allKeywords.addAll(questionKeywords);
    }

    @Override
    public ChallengeTypes type() {
        return type;
    }


    @Override
    public List<String> allKeywords() {
        return allKeywords;
    }

    @Override
    public String question() {
        return question;
    }

    @Override
    public String fileName() {
        return fileName;
    }

    @Override
    public List<String> questionKeywords() {
        return questionKeywords;
    }

    @Override
    public List<String> answerKeywords() {
        return answerKeywords;
    }


    public String answer(){
        return answer;
    }


}
