package com.luxlunaris.studybuddy.model.studybuddy;

import com.luxlunaris.studybuddy.model.studybuddy.commands.Command;
import com.luxlunaris.studybuddy.model.studybuddy.commands.CommandTypes;
import com.luxlunaris.studybuddy.model.studybuddy.commands.classes.AnotherTimeCommand;
import com.luxlunaris.studybuddy.model.studybuddy.commands.classes.AskMeCommand;
import com.luxlunaris.studybuddy.model.studybuddy.commands.classes.BinaryCommand;
import com.luxlunaris.studybuddy.model.studybuddy.commands.classes.ComeAgainCommand;
import com.luxlunaris.studybuddy.model.studybuddy.commands.classes.ExitCommand;
import com.luxlunaris.studybuddy.model.studybuddy.commands.classes.HelpCommand;
import com.luxlunaris.studybuddy.model.studybuddy.commands.classes.TellMeCommand;
import com.luxlunaris.studybuddy.model.studybuddy.commands.classes.UndefinedCommand;
import com.luxlunaris.studybuddy.model.utils.Keywords;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Parser {

    public static final List<String> TELL_ME_KWS = Arrays.asList("tell", "me");
    public static final List<String> ASK_ME_KWS = Arrays.asList("ask", "me");
    public static final List<String> COME_AGAIN_KWS_1 = Arrays.asList("come", "again");
    public static final List<String> COME_AGAIN_KWS_2 = Arrays.asList("repeat");
    public static final List<String> ANOTHER_KWS_1 = Arrays.asList("another", "time");
    public static final List<String> ANOTHER_KWS_2 = Arrays.asList("one", "more");
    public static final List<String> EXIT_KWS_1 = Arrays.asList("exit");
    public static final List<String> HELP_KWS_1 = Arrays.asList("help");
    public static final List<String> BINARY_1 = Arrays.asList("yes");
    public static final List<String> BINARY_2 = Arrays.asList("no");//

    public Command parse(final String userInput){

        List<String> kws = Keywords.extractKeywords(userInput);
        CommandTypes cmdType = determineCommandType(kws);

        switch (cmdType){

            case TELL_ME:

                kws.removeAll(TELL_ME_KWS);
                return new TellMeCommand(kws);

            case ASK_ME:

                kws.removeAll(ASK_ME_KWS);

                String fromFile = AskMeCommand.ANY_FILE;
                List<String> keywords = kws;

                int c;
                if( (c = userInput.indexOf("from")) != -1 ){
                    fromFile = userInput.substring(c+"from".length());
                }

                if(userInput.toLowerCase().contains("random") ){
                    keywords = AskMeCommand.RANDOM;
                }

                return new AskMeCommand(keywords,  fromFile);

            case COME_AGAIN:

                return new ComeAgainCommand(userInput.contains("slowly")|userInput.contains("slow")|userInput.contains("slower"));

            case ANOTHER_TIME:

                return new AnotherTimeCommand();

            case HELP:

                return new HelpCommand();
            case EXIT:

                return new ExitCommand();
            case BINARY:

                boolean yes = userInput.contains(BINARY_1.get(0));
                return new BinaryCommand(yes);

            default:
                return new UndefinedCommand();
        }

    }


    private CommandTypes determineCommandType(List<String> kws){

        boolean b;

        // 1. tell me
        b = kws.containsAll(TELL_ME_KWS);
        if (b){
            return CommandTypes.TELL_ME;
        }

        // 2. ask me
        b = kws.containsAll(ASK_ME_KWS);
        if(b){
            return CommandTypes.ASK_ME;
        }

        // 3. come again
        b = kws.containsAll(COME_AGAIN_KWS_1) || kws.containsAll(COME_AGAIN_KWS_2);
        if(b){
            return CommandTypes.COME_AGAIN;
        }

        // 4. another
        b = kws.containsAll(ANOTHER_KWS_1) | kws.containsAll(ANOTHER_KWS_2);
        if(b){
            return CommandTypes.ANOTHER_TIME;
        }



        // 5. exit
        b = kws.containsAll(EXIT_KWS_1);
        if(b){
            return CommandTypes.EXIT;
        }

        // binary
        b = kws.containsAll(BINARY_1) || kws.containsAll(BINARY_2);
        if(b){
            return CommandTypes.BINARY;
        }


        // 6. help
        b = kws.containsAll(HELP_KWS_1);
        if(b){
            return CommandTypes.HELP;
        }

        return CommandTypes.UNDEFINED;
    }




}
