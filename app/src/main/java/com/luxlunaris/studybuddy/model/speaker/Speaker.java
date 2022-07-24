package com.luxlunaris.studybuddy.model.speaker;

import android.content.Context;

import com.luxlunaris.studybuddy.model.speaker.classes.GoogleSpeaker;

public interface Speaker {

    void speak(String speech);
//    boolean isSpeaking();

    static Speaker getSpeaker(Context context, SpeakerListener speakerListener){
        return new GoogleSpeaker(context, speakerListener);
    }
}
