package com.luxlunaris.studybuddy.model.speaker;

public interface SpeakerListener {

    void startedSpeaking(String speechId);
    void stoppedSpeaking(String speechId);

}
