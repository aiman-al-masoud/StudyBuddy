package com.luxlunaris.studybuddy.model.studybuddy.commands.classes;

import com.luxlunaris.studybuddy.model.studybuddy.commands.AbstractCommand;
import com.luxlunaris.studybuddy.model.studybuddy.commands.CommandTypes;

public class BinaryCommand extends AbstractCommand {

    public final boolean yes;

    public BinaryCommand(boolean yes) {
        super(CommandTypes.BINARY);
        this.yes = yes;
    }


}
