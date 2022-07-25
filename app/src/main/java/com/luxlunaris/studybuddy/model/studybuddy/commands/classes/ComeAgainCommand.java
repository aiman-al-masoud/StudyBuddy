package com.luxlunaris.studybuddy.model.studybuddy.commands.classes;

import com.luxlunaris.studybuddy.model.studybuddy.commands.AbstractCommand;
import com.luxlunaris.studybuddy.model.studybuddy.commands.CommandTypes;

public class ComeAgainCommand extends AbstractCommand {

    public final boolean slowly;

    public ComeAgainCommand(boolean slowly) {
        super(CommandTypes.COME_AGAIN);
        this.slowly = slowly;
    }


}
