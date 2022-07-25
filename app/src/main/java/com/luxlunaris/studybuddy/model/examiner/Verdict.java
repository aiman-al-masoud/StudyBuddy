package com.luxlunaris.studybuddy.model.examiner;

public class Verdict {

    public final int GOOD_ANSWER = 70;
    public final int MEDIOCRE_ANSWER = 50;
    public final int BAD_ANSWER = 40;

    public boolean isFail;
    public final String text;

    public Verdict(boolean isFail, String text){
        this.isFail = isFail;
        this.text = text;
    }

}
