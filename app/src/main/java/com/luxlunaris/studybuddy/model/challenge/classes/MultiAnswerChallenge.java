package com.luxlunaris.studybuddy.model.challenge.classes;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.luxlunaris.studybuddy.model.challenge.AbstractChallenge;
import com.luxlunaris.studybuddy.model.challenge.ChallengeTypes;
import com.luxlunaris.studybuddy.model.utils.Keywords;

import java.util.List;

public class MultiAnswerChallenge extends AbstractChallenge {

    public final List<String> answersList;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public MultiAnswerChallenge(String question, List<String> answersList, String fileName) {
        super(ChallengeTypes.MULTI_ANSWER, question, fileName, answersList.stream().reduce((s1, s2)->s1+"\n"+s2).get());
        this.answersList = answersList;
        String s = answersList.stream().reduce((s1, s2)-> s1+" "+s2).get();
        answerKeywords.addAll(Keywords.extractKeywords(s));
        allKeywords.addAll(answerKeywords);
    }

    public List<String> getAnswersList() {
        return answersList;
    }

}
