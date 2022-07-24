package com.luxlunaris.studybuddy.model.speaker;

import android.content.Context;

import com.luxlunaris.studybuddy.model.speaker.classes.GoogleSpeaker;

public interface Speaker {

    void speak(String speech);

    static Speaker getSpeaker(Context context){
        return new GoogleSpeaker(context);
    }
}
