package com.luxlunaris.studybuddy.model.studybuddy.commands;

import com.luxlunaris.studybuddy.model.challenge.ChallengeManager;

import java.util.List;

public interface Command {


    String ANY_FILE = ChallengeManager.ALL_FILES;
    CommandTypes getType();

}
