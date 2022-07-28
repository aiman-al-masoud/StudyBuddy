package com.luxlunaris.studybuddy.model.challenge;

import android.os.Build;


import com.luxlunaris.studybuddy.model.challenge.classes.MultiAnswerChallenge;
import com.luxlunaris.studybuddy.model.challenge.classes.SingleAnswerChallenge;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
    public List<Challenge> fromText(String text, String fileName) {

        if(!isFormatOk(text)){
            return Collections.emptyList();
        }

        System.out.println("this is the text "+text);

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
    private List<String> isAnswerMutli(String answerText) {

        List<String> lines = Arrays.stream(answerText.split("\\n")).filter(l->l.length()>1).collect(Collectors.toList());

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


    public static boolean isFormatOk(String body){

        String[] pars = body.split("(\\n){2,}");

        if(pars.length < 1){
            return false;
        }

        return Arrays.stream(pars).allMatch(s->{
            String[] qna = s.split("\\?");
            return (qna.length == 2) && (qna[0].length() > 0) && (qna[1].length() > 0);
        });

    }



}
