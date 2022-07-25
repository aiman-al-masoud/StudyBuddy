package com.luxlunaris.studybuddy.model.studybuddy.commands;

public abstract class AbstractCommand implements Command{

    protected CommandTypes type;

    public AbstractCommand(CommandTypes type){
        this.type = type;
    }

    public CommandTypes getType() {
        return type;
    }


    @Override
    public String toString() {
        return "AbstractCommand{" +
                "type=" + type +
                '}';
    }

}
