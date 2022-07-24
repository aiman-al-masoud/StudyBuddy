package com.luxlunaris.studybuddy.model.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Keywords {

    public static List<String> extractKeywords(String text) {
        List<String> stopWords = Arrays.asList("the", "a");
        HashSet<String> set = new HashSet<String>(Arrays.asList(text.split("\\s+")));
        set.removeAll(stopWords);
        return new ArrayList<String>(set);
    }
}


