package com.luxlunaris.studybuddy.model.challenge;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.luxlunaris.studybuddy.model.challenge.classes.MultiAnswerChallenge;
import com.luxlunaris.studybuddy.model.challenge.classes.SingleAnswerChallenge;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ChallengeBuilder {


    /**
     * Text format requires distinct answer-question blocks to be separated
     * by at least one line intermediate whitespace.
     *
     * @param text
     * @param fileName
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Challenge> fromText(String text, String fileName) {

        System.out.println("this is the text "+text);

//        String[] pars = text.split("(\\n|\\s){2,}");
        String[] pars = text.split("(\\n){2,}");


        System.out.println("these are the pars "+Arrays.asList(pars));


        return Arrays.stream(pars).map(c -> buildChallenge(c, fileName)).collect(Collectors.toList());
    }

    /**
     * Builds a Challenge from a paragraph with a question and an answer.
     * @param challengeParagraph
     * @param fileName
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private Challenge buildChallenge(String challengeParagraph, String fileName) {
        String[] parts = challengeParagraph.split("\\?");
        String question = parts[0];
        String answer = parts[1];

        List<String> answerList = isAnswerMutli(answer);

        if (answerList != null) {
            return new MultiAnswerChallenge(question, answerList, fileName);
        }

        return new SingleAnswerChallenge(question, answer, fileName);
    }

    /**
     * Returns list of strings if answer is a list,
     * else returns null.
     *
     * @param answerText
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<String> isAnswerMutli(String answerText) {

        List<String> lines = Arrays.stream(answerText.split("\\n")).filter(l->l.length()>1).collect(Collectors.toList());
//        String[] lines = answerText.split("\\n");

        if (lines.size() == 1) {
            return null;
        }

        String one = lines.get(0).replaceAll("\\s+", "");
        String two = lines.get(1).replaceAll("\\s+", "");

        if (one.charAt(0) == '1' && two.charAt(0) == '2') {
            return lines;
        }

        if (one.charAt(0) == '0' && two.charAt(0) == '1') {
            return lines;
        }

        return null;
    }



}
