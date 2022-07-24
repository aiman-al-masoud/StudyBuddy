package com.luxlunaris.studybuddy.model.examiner;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.luxlunaris.studybuddy.model.challenge.Challenge;
import com.luxlunaris.studybuddy.model.challenge.classes.MultiAnswerChallenge;
import com.luxlunaris.studybuddy.model.challenge.classes.SingleAnswerChallenge;
import com.luxlunaris.studybuddy.model.utils.Keywords;

import java.util.List;
import java.util.stream.Collectors;

public class Examiner {

    public final int EXCELLENT_ANSWER = 90;
    public final int GOOD_ANSWER = 70;
    public final int MEDIOCRE_ANSWER = 50;
    public final int BAD_ANSWER = 40;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getVerdict(Challenge challenge, String userInput){

        // TODO: differentiate between subclasses of challenge
        List<String> userKeywords = Keywords.extractKeywords(userInput);
        List<String> correctKeywords = challenge.answerKeywords();

        List<String> missedKeywords = correctKeywords.stream().filter(k->!userKeywords.contains(k)).collect(Collectors.toList());

        int rememberedKeywords = correctKeywords.size() - missedKeywords.size();
        int percentageKeywords = (int)(rememberedKeywords/(double)correctKeywords.size());

        String mk = missedKeywords.stream().reduce((k1, k2) -> k1+", "+k2).get();

        switch (percentageKeywords){
            case EXCELLENT_ANSWER:
                return "True! You guessed it!";
            case GOOD_ANSWER:
                return "Fair enough, but do any of these keywords tell you anything? "+mk;
            case MEDIOCRE_ANSWER:
                return  "You got close, but you missed a lot of information. Try recalling these keywords: "+mk;
            case BAD_ANSWER:
                String s = "You're totally off track! The correct answer is: ";
                try{
                    s+=((SingleAnswerChallenge)challenge).answer;
                }catch (ClassCastException e){
                    s+=((MultiAnswerChallenge)challenge).getAnswersList().stream().reduce((a1,a2)->a1+".\n"+a2).get();
                }
                return s;
        }

        return "An error occurred in Examiner.getVerdict()!";
    }





}
