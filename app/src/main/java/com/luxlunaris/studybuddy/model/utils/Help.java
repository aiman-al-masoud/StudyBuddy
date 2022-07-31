package com.luxlunaris.studybuddy.model.utils;

import android.content.Context;

import com.luxlunaris.studybuddy.R;
import com.luxlunaris.studybuddy.model.studybuddy.commands.CommandTypes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Help {

    public final String name;
    public final String description;
    public final String syntax;

    private Help(String name, String description, String syntax){
        this.name = name;
        this.description = description;
        this.syntax = syntax;
    }

    private Help(String[] doc){
        this.name = doc[0];
        this.description = doc[1];
        this.syntax = doc[2];
    }


    public static Help getDocumentationFor(Context context, CommandTypes commandType){

        switch (commandType){
            case ASK_ME:
                return new Help(context.getResources().getStringArray(R.array.doc_ask_me));
            case TELL_ME:
                return new Help("Tell Me", "Use it to ask Study Buddy to talk about a topic.", "tell me [keywords|random] from [filename]");
            case COME_AGAIN:
                return new Help("Come Again", "Prompts study buddy to repeat the question.", "come again [slowly]");
            case ANOTHER_TIME:
                return new Help("Another Time", "Prompts study buddy to re-run a Tell Me command with the same arguments.", "another time");
            case EXIT:
                return new Help("Exit", "Exits the app", "exit");
            case BINARY:
                return new Help("Binary", "Use it when Study Buddy asks a binary question.", "yes|no");
            case UNDEFINED:
                return new Help("Undefined","","");
            case HELP:
                return new Help("Help","","help");

        }



        return new Help("--", "--", "--");
    }

    public static int numberOfCommands(){
        return CommandTypes.values().length;
    }

    public static List<Help> getDocumentations(Context context){
        return Arrays.stream(CommandTypes.values())
                .map(c->Help.getDocumentationFor(context, c))
                .collect(Collectors.toList());
    }





}
