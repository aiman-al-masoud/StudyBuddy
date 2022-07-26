package com.luxlunaris.studybuddy.model.examiner;

public class Verdict {

    public boolean isFail;
    public final String text;

    public Verdict(boolean isFail, String text){
        this.isFail = isFail;
        this.text = text;
    }

}
