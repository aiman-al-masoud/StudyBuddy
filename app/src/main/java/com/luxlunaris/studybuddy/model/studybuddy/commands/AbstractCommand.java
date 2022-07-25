package com.luxlunaris.studybuddy.model.studybuddy.commands;

public abstract class AbstractCommand implements Command{

    private CommandTypes type;

    public AbstractCommand(CommandTypes type){
        this.type = type;
    }

    public CommandTypes getType() {
        return type;
    }



}
