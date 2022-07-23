package com.luxlunaris.studybuddy.model.challenge;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ChallengeBuilder {


    /**
     * Text format requires distinct answer-question blocks to be separated
     * by at least one line intermediate whitespace.
     * @param text
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Challenge> fromText(String text){
        String[] parags = text.split("(\\n|\\s){2,}");
        return null;
    }

    private Challenge buildChallenge(String challengeText){
        return null;
    }






}
