package com.luxlunaris.studybuddy.model.challenge;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import com.luxlunaris.studybuddy.model.challenge.exceptions.NoSuchKeywordsException;
import com.luxlunaris.studybuddy.model.challenge.exceptions.NoSuchFileException;
import com.luxlunaris.studybuddy.model.utils.FileManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ChallengeManager {

    public static final String ALL_FILES = "*";

    public final int MATCHING_KEYWORDS_THRESHOLD = 10; // percent
    private final List<Challenge> challenges;

    public ChallengeManager() {
        challenges = new ArrayList<Challenge>();
    }

    /**
     * Adds a list of challenges to the manager.
     *
     * @param challenges
     */
    public void addAllChallenges(List<Challenge> challenges) {
        this.challenges.addAll(challenges);
    }

    /**
     * Returns a list of challenges filtered by file name.
     *
     * @param fileName
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Challenge> getChallengesByFileName(final String fileName) throws NoSuchFileException{

        List<Challenge> challenges = this.challenges;

        if (!fileName.equals(ALL_FILES)) {
            String closestFileName = FileManager.getClosestMatchingFileName(fileName);
            Log.d("ChallengeManager", "getChallengesByFileName: "+closestFileName);

            challenges = challenges.stream().filter(c -> c.fileName().equals(  closestFileName )).collect(Collectors.toList());
        }

        if(challenges.size()==0){
            throw new NoSuchFileException(fileName);
        }

        return challenges;
    }

    /**
     * Get a random challenge from a specific file.
     *
     * @param fileName
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Challenge getRandomChallenge(String fileName) throws NoSuchFileException {

        try {
            List<Challenge> challenges = getChallengesByFileName(fileName);
            int i = new Random().nextInt(challenges.size());
            return challenges.get(i);
        }catch (IndexOutOfBoundsException e){
            throw new NoSuchFileException(fileName);
        }
    }

    /**
     * Get a random challenge from any file.
     *
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Challenge getRandomChallenge() throws NoSuchFileException {
        return getRandomChallenge(ALL_FILES);
    }


    /**
     * Returns a list of challenges that pertain to the keywords
     * sorted by their relevance.
     *
     * @param keywords
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Challenge> getChallengesListByKeywords(final List<String> keywords, final  String fileName) throws NoSuchFileException, NoSuchKeywordsException{

        List<Challenge> challenges = getChallengesByFileName(fileName);

        challenges = challenges.stream().map(c -> {

            // calculate relevance metric for each Challenge
            int i = keywords.stream().mapToInt(k -> c.allKeywords().contains(k) ? 1 : 0).sum();
            int percentageMatch = (int) (100 * i / (double) keywords.size());

            return Arrays.asList(c, percentageMatch);
        }).filter(e -> {
            // filter out irrelevant Challenges
            return (int) e.get(1) >= MATCHING_KEYWORDS_THRESHOLD;
        }).sorted((e1, e2) -> {

            // sort relevant Challenges by relevance
            return (int) e1.get(1) - (int) e2.get(1);
        }).map(e -> {

            // remove relevance metric
            return (Challenge) e.get(0);

        }).collect(Collectors.toList());

        if(challenges.size()==0){
            throw new NoSuchKeywordsException(keywords+"");
        }

        return challenges;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Challenge> getChallengesListByKeywords(List<String> keywords) throws NoSuchKeywordsException, NoSuchFileException {
        return getChallengesListByKeywords(keywords, ALL_FILES);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Challenge getChallengeByKeywords(List<String> keywords, String fileName) throws NoSuchKeywordsException, NoSuchFileException {

        try {
            return getChallengesListByKeywords(keywords, fileName).get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchKeywordsException(keywords + "");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Challenge getChallengeByKeywords(List<String> keywords) throws NoSuchFileException, NoSuchKeywordsException {
        return getChallengeByKeywords(keywords, ALL_FILES);
    }


}
