package com.luxlunaris.studybuddy.model.studybuddy;

/**
 * Study Buddy's behavior can be modelled by a FSM,
 * these are its states:
 */
public enum StudyBuddyModes {
    AWAIT_COMMAND, AWAIT_ANSWER, AWAIT_CONFIRM_TRY_AGAIN;
}
