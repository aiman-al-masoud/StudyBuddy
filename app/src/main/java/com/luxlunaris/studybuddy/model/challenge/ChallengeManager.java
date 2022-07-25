package com.luxlunaris.studybuddy.model.challenge;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.luxlunaris.studybuddy.model.challenge.exceptions.NoSuchChallengeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ChallengeManager {

    public final int MATCHING_KEYWORDS_THRESHOLD = 10; // percent
    public final String ALL_FILES = "*";

    private List<Challenge> challenges;


    public ChallengeManager(){
        challenges = new ArrayList<Challenge>();
    }

//    public void addChallenge(Challenge challenge){
//        challenges.add(challenge);
//    }

    public void addAllChallenges(List<Challenge> challenges){
        this.challenges.addAll(challenges);
    }

    public Challenge getRandomChallenge(){
        int i = new Random().nextInt(challenges.size());
        return challenges.get(i);
    }

    /**
     * Returns a list of Challenges that pertain to the keywords
     * sorted by their relevance.
     *
     * @param keywords
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Challenge> getChallengesListByKeywords(List<String> keywords, String fileName){

        List<Challenge> challenges  = this.challenges;

        if(fileName.equals(ALL_FILES)){
            challenges = challenges.stream().filter(c->c.fileName().equals(fileName)).collect(Collectors.toList());
        }

        Log.d("ChallengeManager", "getChallengesListByKeywords: "+keywords);

       return challenges.stream().map(c->{

           Log.d("ChallengeManager", "getChallengesListByKeywords: "+c.question()+" "+c.allKeywords());

           // calculate relevance metric for each Challenge
            int i = keywords.stream().mapToInt(k -> c.allKeywords().contains(k) ? 1 : 0).sum();

           int percentageMatch = (int)(100*i/(double)keywords.size());
           Log.d("ChallengeManager", "getChallengesListByKeywords: percentageMatch"+percentageMatch);


           return Arrays.asList(c, percentageMatch);
        }).filter(e->{
            // filter out irrelevant Challenges
            return (int)e.get(1)  >= MATCHING_KEYWORDS_THRESHOLD;
        }).sorted((e1, e2)->{

            // sort relevant Challenges by relevance
            return (int)e1.get(1) - (int)e2.get(1);
        }).map(e->{

            // remove relevance metric
            return (Challenge)e.get(0);

        }).collect(Collectors.toList());

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Challenge> getChallengesListByKeywords(List<String> keywords) {
        return getChallengesListByKeywords(keywords, ALL_FILES);
    }

        @RequiresApi(api = Build.VERSION_CODES.N)
    public Challenge getChallengeByKeywords(List<String> keywords) throws NoSuchChallengeException {

        try{
            return getChallengesListByKeywords(keywords).get(0);
        }catch (IndexOutOfBoundsException e){
            throw new NoSuchChallengeException(keywords+"");
        }
    }






}
