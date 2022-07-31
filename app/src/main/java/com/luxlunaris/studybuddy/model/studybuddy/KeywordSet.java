package com.luxlunaris.studybuddy.model.studybuddy;

import android.util.Log;

import com.luxlunaris.studybuddy.model.utils.Keywords;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Represents a list of sets, where each set contains
 * a number of keywords. All of the sets are equivalent
 * in the sense that they produce the same command.
 */
public class KeywordSet {

    private List<Set<String>> sets;

    public KeywordSet(String[] array) {

        sets = Arrays.stream(array)
                .map(Keywords::extractKeywords)
                .map(HashSet::new)
                .collect(Collectors.toList());

        Log.d("KeywordSet", "KeywordSet: " + sets);
    }

    /**
     * Returns the index of the first subset that matches
     * the input keywords.
     * Returns -1 if no match is found.
     *
     * @param keywords
     * @return
     */
    public int matches(final List<String> keywords) {

        OptionalInt indexOpt = IntStream.range(0, sets.size())
                .filter(i -> keywords.containsAll(sets.get(i)))
                .findFirst();


        if (indexOpt.isPresent()) {
            return indexOpt.getAsInt();
        }

        return -1;
    }


    public Set<String> getSubset(int num) {
        return sets.get(num);
    }


}
