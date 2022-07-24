package com.luxlunaris.studybuddy.model.studybuddy;

import android.content.Context;

import com.luxlunaris.studybuddy.model.examiner.Examiner;
import com.luxlunaris.studybuddy.model.scribe.Scribe;
import com.luxlunaris.studybuddy.model.scribe.ScribeListener;
import com.luxlunaris.studybuddy.model.speaker.Speaker;

public class StudyBuddy implements ScribeListener {

    private Context context;
    private Examiner examiner;
    private Scribe scribe;
    private Speaker speaker;


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
