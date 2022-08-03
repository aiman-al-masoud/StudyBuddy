package com.luxlunaris.studybuddy.model.studybuddy.commands.classes;

import com.luxlunaris.studybuddy.model.studybuddy.commands.AbstractCommand;
import com.luxlunaris.studybuddy.model.studybuddy.commands.CommandTypes;

public class WhatTimeCommand extends AbstractCommand {

    public WhatTimeCommand() {
        super(CommandTypes.WHAT_TIME);
    }
}
