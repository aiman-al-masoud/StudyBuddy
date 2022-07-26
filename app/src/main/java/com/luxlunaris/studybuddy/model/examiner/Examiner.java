package com.luxlunaris.studybuddy.model.examiner;

import android.os.Build;
import com.luxlunaris.studybuddy.model.challenge.Challenge;
import com.luxlunaris.studybuddy.model.challenge.classes.MultiAnswerChallenge;
import com.luxlunaris.studybuddy.model.challenge.classes.SingleAnswerChallenge;
import com.luxlunaris.studybuddy.model.utils.Keywords;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Examiner {

    public final int GOOD_ANSWER = 70;
    public final int MEDIOCRE_ANSWER = 50;
    public final int BAD_ANSWER = 40;


    public Verdict getVerdict(Challenge challenge, String userInput) {

        try{
            return evaluateMultiAnswerChallenge(userInput, (MultiAnswerChallenge)challenge);
        }catch (ClassCastException e){
            /* Do nothing*/
        }

        return evaluateSingleAnswerChallenge(userInput, (SingleAnswerChallenge) challenge);
    }

    private Verdict evaluateSingleAnswerChallenge(String userInput, SingleAnswerChallenge sac) {

        Verdict v;

        List<String> userKeywords = Keywords.extractKeywords(userInput);
        List<String> correctKeywords = sac.answerKeywords();

        List<String> missedKeywords = correctKeywords.stream().filter(k -> !userKeywords.contains(k)).collect(Collectors.toList());
        int rememberedKeywords = correctKeywords.size() - missedKeywords.size();
        int percentageKeywords = (int) (100 * rememberedKeywords / (double) correctKeywords.size());

        if (percentageKeywords <= BAD_ANSWER) {
            String s = "The correct answer is: ";
            s += sac.answer;
            v = new Verdict(true, s);
            return v;
        }

        if (percentageKeywords <= MEDIOCRE_ANSWER) {
            String mk = missedKeywords.stream().reduce((k1, k2) -> k1 + ", " + k2).get();
            String s = "You got close, but you missed a lot of information. Try recalling these keywords: " + mk;
            v = new Verdict(true, s);
            return v;
        }

        if (percentageKeywords <= GOOD_ANSWER) {
            String mk = missedKeywords.stream().reduce((k1, k2) -> k1 + ", " + k2).get();
            String s = "Fair enough, but you missed these keywords:" + mk;
            v = new Verdict(false, s);
            return v;
        }

        return new Verdict(false, "True! You guessed it!");
    }

    private Verdict evaluateMultiAnswerChallenge(String userInput, MultiAnswerChallenge mac) {

        List<String> missedPoints = mac.getAnswersList().stream().map(a -> {
            return Arrays.asList(a, Keywords.extractKeywords(a));
        }).filter(t -> {
            List<String> k = (List<String>) t.get(1);
            return getRememberedKeywordsPercentage(getMissedKeywords(userInput, k), k) <= BAD_ANSWER;
        }).map(t -> {
            String a = (String) t.get(0);
            return a;
        }).collect(Collectors.toList());

        if(missedPoints.size()==0){
            return new Verdict(false, "Flawless!");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("You forgot the following points:\n");
        missedPoints.forEach(e->{
            sb.append(e);
            sb.append(".\n");
        });

        return new Verdict(true, sb.toString());
    }

    private static List<String> getMissedKeywords(String userInput, List<String> correctKeywords) {
        List<String> userKeywords = Keywords.extractKeywords(userInput);
        List<String> missedKeywords = correctKeywords.stream().filter(k -> !userKeywords.contains(k)).collect(Collectors.toList());
        return missedKeywords;
    }

    private static int getRememberedKeywordsPercentage(List<String> missedKeywords, List<String> correctKeywords) {
        int rememberedKeywords = correctKeywords.size() - missedKeywords.size();
        int percentageKeywords = (int) (100 * rememberedKeywords / (double) correctKeywords.size());
        return percentageKeywords;
    }


}
