package com.luxlunaris.studybuddy.model.speaker;

import android.content.Context;

import com.luxlunaris.studybuddy.model.speaker.classes.GoogleSpeaker;

public interface Speaker {

    float FAST = 2.0f;
    float NORMAL = 1.0f;
    float SLOW = 0.5f;

    void speak(String speech);
    void speak(String speech, float speechRate);

    static Speaker getSpeaker(Context context, SpeakerListener speakerListener){
        return new GoogleSpeaker(context, speakerListener);
    }
}
