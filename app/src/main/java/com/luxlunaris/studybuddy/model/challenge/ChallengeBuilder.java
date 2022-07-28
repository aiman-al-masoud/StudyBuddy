package com.luxlunaris.studybuddy.model.challenge;

import com.luxlunaris.studybuddy.model.challenge.classes.MultiAnswerChallenge;
import com.luxlunaris.studybuddy.model.challenge.classes.SingleAnswerChallenge;
import com.luxlunaris.studybuddy.model.challenge.exceptions.WrongFormatException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ChallengeBuilder {

    /**
     * Text format requires distinct answer-question blocks to be separated
     * by at least one line intermediate whitespace.
     *
     * @param text
     * @param fileName
     * @return
     */
    public List<Challenge> fromText(String text, String fileName) throws WrongFormatException {

        if(text.replaceAll("\\s+", "").isEmpty()){
            throw new WrongFormatException("Empty corpus!");
        }

        String[] pars = text.split("(\\n){2,}");
        return IntStream.range(0, pars.length).mapToObj(i -> buildChallenge(i + 1, pars[i], fileName)).collect(Collectors.toList());
    }

    /**
     * Builds a Challenge from a paragraph with a question and an answer
     * separated by a question mark.
     *
     * @param challengeParagraph
     * @param fileName
     * @return
     */
    private Challenge buildChallenge(final int paragraphIndex, final String challengeParagraph, final String fileName) throws WrongFormatException {

        if (!challengeParagraph.contains("?")) {
            throw new WrongFormatException("Missing (?) in paragraph: " + paragraphIndex);
        }

        String answer;
        String[] parts;
        String question;
        parts = challengeParagraph.split("\\?");

        question = parts[0];

        if (question.replaceAll("\\s+", "").length() < 1) {
            throw new WrongFormatException("Empty question in paragraph: " + paragraphIndex);
        }

        try {
            answer = parts[1];
        } catch (IndexOutOfBoundsException e) {
            throw new WrongFormatException("Empty answer in paragraph: " + paragraphIndex);
        }

        if(answer.replaceAll("\\s+", "").isEmpty()){
            throw new WrongFormatException("Empty answer in paragraph: " + paragraphIndex);
        }



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

        List<String> lines = Arrays.stream(answerText.split("\\n")).filter(l -> l.length() > 1).collect(Collectors.toList());

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
