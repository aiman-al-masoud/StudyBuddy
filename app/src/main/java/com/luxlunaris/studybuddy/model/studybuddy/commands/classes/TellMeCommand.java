package com.luxlunaris.studybuddy.model.studybuddy.commands.classes;

import com.luxlunaris.studybuddy.model.studybuddy.commands.AbstractCommand;
import com.luxlunaris.studybuddy.model.studybuddy.commands.CommandTypes;

import java.util.List;

public class TellMeCommand extends AbstractCommand {

    public final List<String> keywords;

    public TellMeCommand(List<String> keywords) {
        super(CommandTypes.TELL_ME);
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return "TellMeCommand{" +
                "keywords=" + keywords +
                '}';
    }
}
