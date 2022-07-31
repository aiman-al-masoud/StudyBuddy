package com.luxlunaris.studybuddy.model.studybuddy;

import com.luxlunaris.studybuddy.model.studybuddy.commands.CommandTypes;

public interface StudyBuddyListener {

    void onOutput(String output);
    void onUserInput(String userInput);
    void onError(String error);
    void onHelpCalled(CommandTypes commandTypes);


}
