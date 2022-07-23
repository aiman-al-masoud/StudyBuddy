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
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Challenge> fromText(String text, String fileName) {
        String[] pars = text.split("(\\n|\\s){2,}");
        return Arrays.stream(pars).map(c -> buildChallenge(c, fileName)).collect(Collectors.toList());
    }

    /**
     * Builds a Challenge from a paragraph with a question and an answer.
     *
     * @param challengeParagraph
     * @return
     */
    private Challenge buildChallenge(String challengeParagraph, String fileName) {
        String[] parts = challengeParagraph.split("\\?");
        String question = parts[0];
        String answer = parts[1];

        List<String> keywords = extractKeywords(question + " " + answer);
        List<String> answerList = isAnswerMutli(answer);

        if (answerList != null) {
            return new MultiAnswerChallenge(question, keywords, answerList, fileName);
        }

        return new SingleAnswerChallenge(question, keywords, answer, fileName);
    }

    /**
     * Returns list of strings if answer is a list,
     * else returns null.
     *
     * @param answerText
     * @return
     */
    private List<String> isAnswerMutli(String answerText) {

        String[] lines = answerText.split("\\n");

        if (lines.length == 1) {
            return null;
        }

        String one = lines[0].replaceAll("\\s+", "");
        String two = lines[1].replaceAll("\\s+", "");

        if (one.charAt(0) == '1' && two.charAt(0) == '2') {
            return Arrays.asList(lines);
        }

        if (one.charAt(0) == '0' && two.charAt(0) == '1') {
            return Arrays.asList(lines);
        }

        return null;
    }

    private List<String> extractKeywords(String text) {
        List<String> stopWords = Arrays.asList("the", "a");
        HashSet<String> set = new HashSet<String>(Arrays.asList(text.split("\\s+")));
        set.removeAll(stopWords);
        return new ArrayList<String>(set);
    }


}
