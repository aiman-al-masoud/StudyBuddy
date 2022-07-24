package com.luxlunaris.studybuddy.model.challenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public abstract class AbstractChallenge implements Challenge{

    public final ChallengeTypes type;
    public final String question;
    public final String fileName;
    public final List<String> allKeywords;
    public final List<String> questionKeywords;
    public final List<String> answerKeywords;


    public AbstractChallenge(ChallengeTypes type, String question, String fileName){
        this.type = type;
        this.question = question;
        this.fileName = fileName;
        questionKeywords = extractKeywords(question);
        answerKeywords = new ArrayList<String>();
        allKeywords = new ArrayList<String>();
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

    public static List<String> extractKeywords(String text) {
        List<String> stopWords = Arrays.asList("the", "a");
        HashSet<String> set = new HashSet<String>(Arrays.asList(text.split("\\s+")));
        set.removeAll(stopWords);
        return new ArrayList<String>(set);
    }


}
