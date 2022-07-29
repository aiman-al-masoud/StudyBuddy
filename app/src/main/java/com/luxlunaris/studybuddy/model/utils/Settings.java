package com.luxlunaris.studybuddy.model.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Settings {

    private static Settings instance;

    private HashMap<String, String> map;

    public static final String LANGUAGE = "language";

    public static Settings instance(){
        return instance==null? (instance = new Settings()) : instance;
    }

    private Settings(){
        map = new HashMap<>();
        map.put(LANGUAGE, "en-US");
    }

    public String getString(String key) {
        return map.get(key);
    }

    public String setString(String key, String val) {
        return map.put(key, val);
    }

    public List<String> getAvailableLanguages(){
        return Arrays.asList("en");
    }



}
