package com.luxlunaris.studybuddy.model.examiner;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getVerdict(Challenge challenge, String userInput) {

        try{
            evaluateMultiAnswerChallenge(userInput, (MultiAnswerChallenge)challenge);
        }catch (ClassCastException e){

        }

        // TODO: differentiate between subclasses of challenge
        List<String> userKeywords = Keywords.extractKeywords(userInput);
        List<String> correctKeywords = challenge.answerKeywords();

        Log.d("Examiner.getVerdict():", "userKeywords: " + userKeywords);
        Log.d("Examiner.getVerdict():", "correctKeywords: " + correctKeywords);

        List<String> missedKeywords = correctKeywords.stream().filter(k -> !userKeywords.contains(k)).collect(Collectors.toList());
        int rememberedKeywords = correctKeywords.size() - missedKeywords.size();
        int percentageKeywords = (int) (100 * rememberedKeywords / (double) correctKeywords.size());

        Log.d("Examiner.getVerdict():", "percentageKeywords: " + percentageKeywords);

        if (percentageKeywords <= BAD_ANSWER) {
            String s = "You're totally off track! The correct answer is: ";
            try {
                s += ((SingleAnswerChallenge) challenge).answer;
            } catch (ClassCastException e) {
                s += ((MultiAnswerChallenge) challenge).getAnswersList().stream().reduce((a1, a2) -> a1 + ".\n" + a2).get();
            }
            return s;
        }

        if (percentageKeywords <= MEDIOCRE_ANSWER) {
            String mk = missedKeywords.stream().reduce((k1, k2) -> k1 + ", " + k2).get();
            return "You got close, but you missed a lot of information. Try recalling these keywords: " + mk;
        }

        if (percentageKeywords <= GOOD_ANSWER) {
            String mk = missedKeywords.stream().reduce((k1, k2) -> k1 + ", " + k2).get();
            return "Fair enough, but do any of these keywords tell you anything? " + mk;
        }

        return "True! You guessed it!";
    }


    private String evaluateSingleAnswerChallenge(String userInput, SingleAnswerChallenge sac) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String evaluateMultiAnswerChallenge(String userInput, MultiAnswerChallenge mac) {

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
            return "Perfect!";
        }

        StringBuilder sb = new StringBuilder();
        String s = "You forgot to mention the following points:\n";
        missedPoints.forEach(e->{
            sb.append(e);
            sb.append(".\n");
        });

        return sb.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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
