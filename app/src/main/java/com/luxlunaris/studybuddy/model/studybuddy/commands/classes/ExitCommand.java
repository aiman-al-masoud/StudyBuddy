package com.luxlunaris.studybuddy.model.studybuddy.commands.classes;

import com.luxlunaris.studybuddy.model.studybuddy.commands.AbstractCommand;
import com.luxlunaris.studybuddy.model.studybuddy.commands.CommandTypes;

public class ExitCommand extends AbstractCommand {
    public ExitCommand() {
        super(CommandTypes.EXIT);
    }
}
