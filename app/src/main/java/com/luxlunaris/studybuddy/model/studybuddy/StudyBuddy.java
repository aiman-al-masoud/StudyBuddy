package com.luxlunaris.studybuddy.model.studybuddy;

import android.content.Context;
import android.util.Log;

import com.luxlunaris.studybuddy.model.challenge.ChallengeBuilder;
import com.luxlunaris.studybuddy.model.challenge.ChallengeManager;
import com.luxlunaris.studybuddy.model.examiner.Examiner;
import com.luxlunaris.studybuddy.model.scribe.Scribe;
import com.luxlunaris.studybuddy.model.scribe.ScribeListener;
import com.luxlunaris.studybuddy.model.speaker.Speaker;

public class StudyBuddy implements ScribeListener {

    private Context context;
    private Examiner examiner;
    private Scribe scribe;
    private Speaker speaker;
    private ChallengeBuilder cb;
    private ChallengeManager cm;


    public StudyBuddy(Context context){
        this.context = context;
        examiner = new Examiner();
        scribe = Scribe.getScribe(context);
        speaker = Speaker.getSpeaker(context);
        cb = new ChallengeBuilder();
        cm = new ChallengeManager();
    }

    public void enterUserInput(String userInput){

    }

    @Override
    public void onTranscription(String transcription) {
        enterUserInput(transcription);
    }

    @Override
    public void onError(int error) {
        Log.d("StudyBuddy.onError()", error+"");
    }


}
