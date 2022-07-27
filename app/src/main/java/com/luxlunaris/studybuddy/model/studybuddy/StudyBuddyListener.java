package com.luxlunaris.studybuddy.model.studybuddy;

public interface StudyBuddyListener {

    void onOutput(String output);
    void onUserVoiceInput(String voiceInput);
    void onError(String error);

}
