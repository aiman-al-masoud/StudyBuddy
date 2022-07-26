package com.luxlunaris.studybuddy.model.studybuddy.commands.classes;

import com.luxlunaris.studybuddy.model.challenge.ChallengeManager;
import com.luxlunaris.studybuddy.model.studybuddy.commands.AbstractCommand;
import com.luxlunaris.studybuddy.model.studybuddy.commands.CommandTypes;

import java.util.List;

public class AskMeCommand extends AbstractCommand {

    public static final List<String> RANDOM = null;
    public final List<String> keywords;
    public final String fromFile;
    public final boolean random;

    public AskMeCommand(List<String> keywords, String fromFile) {
        super(CommandTypes.ASK_ME);

        this.fromFile = fromFile!=null ? fromFile : ANY_FILE;
        this.keywords = keywords;

        if(keywords==RANDOM){
            random = true;
        }else {
            random = false;
        }

    }

    @Override
    public String toString() {
        return "AskMeCommand{" +
                "keywords=" + keywords +
                ", fromFile='" + fromFile + '\'' +
                ", random=" + random +
                '}';
    }


}
