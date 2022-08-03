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
                return new Help(context.getResources().getStringArray(R.array.doc_tell_me));
            case COME_AGAIN:
                return new Help(context.getResources().getStringArray(R.array.doc_come_again));
            case ANOTHER_TIME:
                return new Help(context.getResources().getStringArray(R.array.doc_another_time));
            case EXIT:
                return new Help(context.getResources().getStringArray(R.array.doc_exit));
            case BINARY:
                return new Help(context.getResources().getStringArray(R.array.doc_binary));
            case UNDEFINED:
                return new Help(context.getResources().getStringArray(R.array.doc_undefined));
            case HELP:
                return new Help(context.getResources().getStringArray(R.array.doc_help));
            case I_DONT_KNOW:
                return new Help(context.getResources().getStringArray(R.array.doc_i_dont_know));
            case WHAT_TIME:
                return new Help(context.getResources().getStringArray(R.array.doc_what_time));

        }

        return new Help(context.getResources().getStringArray(R.array.doc_help));
    }

    public static int numberOfCommands(){
        return CommandTypes.values().length-1;
    }

    public static List<Help> getDocumentations(Context context){
        return Arrays.stream(CommandTypes.values())
                .filter(v->v!=CommandTypes.UNDEFINED)
                .map(c->Help.getDocumentationFor(context, c))
                .collect(Collectors.toList());
    }





}
