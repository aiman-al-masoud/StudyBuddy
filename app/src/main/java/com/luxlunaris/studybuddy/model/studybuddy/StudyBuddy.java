package com.luxlunaris.studybuddy.model.studybuddy;

import android.content.Context;

import com.luxlunaris.studybuddy.model.scribe.ScribeListener;

public class StudyBuddy implements ScribeListener {

    Context context;


    public StudyBuddy(Context context){
        this.context = context;


    }



    @Override
    public void onTranscription(String transcription) {

    }

    @Override
    public void onError(int error) {

    }


}
