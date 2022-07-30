package com.luxlunaris.studybuddy.model;

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


    public static Help getDocumentationFor(CommandTypes commandType){

        if(commandType==CommandTypes.ASK_ME){
            return new Help("Ask Me", "Use it to trigger a question from Study Buddy.", "ask me [keyword|random] from [filename]");
        }

        return new Help("--", "--", "--");
    }

    public static int numberOfCommands(){
        return CommandTypes.values().length;
    }

    public static List<Help> getDocumentations(){
        return Arrays.stream(CommandTypes.values())
                .map(Help::getDocumentationFor)
                .collect(Collectors.toList());
    }





}
