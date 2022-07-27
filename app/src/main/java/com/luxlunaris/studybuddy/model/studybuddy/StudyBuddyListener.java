package com.luxlunaris.studybuddy.model.studybuddy;

public interface StudyBuddyListener {

    void onOutput(String output);
    void onUserInput(String userInput);
    void onError(String error);

}
