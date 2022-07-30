package com.luxlunaris.studybuddy.model;

import com.luxlunaris.studybuddy.model.studybuddy.commands.CommandTypes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Help {


    public static String getDocumentationFor(CommandTypes commandType){

        if(commandType==CommandTypes.ASK_ME){
            return "ask me";
        }

        return "help is ...";
    }

    public static int numberOfCommands(){
        return CommandTypes.values().length;
    }

    public static List<String> getDocumentations(){
        return Arrays.stream(CommandTypes.values())
                .map(Help::getDocumentationFor)
                .collect(Collectors.toList());
    }



}
