package com.luxlunaris.studybuddy.model.studybuddy;

import android.content.Context;

import com.luxlunaris.studybuddy.R;
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

import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static KeywordSet TELL_ME;
    public static KeywordSet ASK_ME;
    public static KeywordSet COME_AGAIN;
    public static KeywordSet ANOTHER;
    public static KeywordSet EXIT;
    public static KeywordSet HELP;
    public static KeywordSet YES;
    public static KeywordSet NO;
    public static KeywordSet FROM;
    public static KeywordSet RANDOM;


    //    public static final List<String> COME_AGAIN_KWS_1 = Arrays.asList("come", "again");
//    public static final List<String> COME_AGAIN_KWS_2 = Arrays.asList("repeat");
//    public static final List<String> ANOTHER_KWS_1 = Arrays.asList("another", "time");
//    public static final List<String> ANOTHER_KWS_2 = Arrays.asList("one", "more");
//    public static final List<String> EXIT_KWS_1 = Arrays.asList("exit");
//    public static final List<String> HELP_KWS_1 = Arrays.asList("help");
//    public static final List<String> BINARY_1 = Arrays.asList("yes");
//    public static final List<String> BINARY_2 = Arrays.asList("no");//
//    public static final String RANDOM = "random";
//    public static final String FROM = "from";
//    public static final String YES = "yes";

    public Parser(Context context) {
        TELL_ME = new KeywordSet(context.getResources().getStringArray(R.array.tell_me_keywords));
        ASK_ME = new KeywordSet(context.getResources().getStringArray(R.array.ask_me_keywords));
        COME_AGAIN = new KeywordSet(context.getResources().getStringArray(R.array.come_again_keywords));
        ANOTHER = new KeywordSet(context.getResources().getStringArray(R.array.another_time_keywords));
        EXIT = new KeywordSet(context.getResources().getStringArray(R.array.exit_keywords));
        HELP = new KeywordSet(context.getResources().getStringArray(R.array.help_keywords));
        YES = new KeywordSet(context.getResources().getStringArray(R.array.yes_keywords));
        NO = new KeywordSet(context.getResources().getStringArray(R.array.no_keywords));
        FROM = new KeywordSet(context.getResources().getStringArray(R.array.from_keywords));
        RANDOM = new KeywordSet(context.getResources().getStringArray(R.array.random_keywords));
    }


    public Command parse(String userInput) {

        userInput = userInput.toLowerCase();
        List<String> kws = Keywords.extractKeywords(userInput);
        CommandTypes cmdType = determineCommandType(kws);

        switch (cmdType) {

            case TELL_ME: {
                kws.removeAll(TELL_ME.getSubset(TELL_ME.matches(kws)));

                String fromFile = AskMeCommand.ANY_FILE;
                List<String> keywords = kws;

                fromFile = findFromFile(userInput);

                if (RANDOM.matches(keywords) >= 0) {
                    keywords = AskMeCommand.RANDOM;
                }

                return new TellMeCommand(keywords, fromFile);
            }
            case ASK_ME:

//                kws.removeAll(ASK_ME_KWS);
                kws.removeAll(ASK_ME.getSubset(ASK_ME.matches(kws)));


                String fromFile = AskMeCommand.ANY_FILE;
                List<String> keywords = kws;

               fromFile = findFromFile(userInput);

                if (RANDOM.matches(keywords) >= 0) {
                    keywords = AskMeCommand.RANDOM;
                }

                return new AskMeCommand(keywords, fromFile);

            case COME_AGAIN:

                return new ComeAgainCommand(userInput.contains("slowly") | userInput.contains("slow") | userInput.contains("slower"));

            case ANOTHER_TIME:

                return new AnotherTimeCommand();

            case HELP:

                return new HelpCommand();
            case EXIT:

                return new ExitCommand();
            case BINARY:

//                boolean yes = userInput.contains(YES);
                boolean yes = YES.matches(kws) >= 0;
                return new BinaryCommand(yes);

            default:
                return new UndefinedCommand();
        }

    }


    private CommandTypes determineCommandType(List<String> kws) {

        boolean b;

        // 1. tell me
        b = TELL_ME.matches(kws) >= 0;
        if (b) {
            return CommandTypes.TELL_ME;
        }

        // 2. ask me
//        b = kws.containsAll(ASK_ME_KWS);
        b = ASK_ME.matches(kws) >= 0;
        if (b) {
            return CommandTypes.ASK_ME;
        }

        // 3. come again
//        b = kws.containsAll(COME_AGAIN_KWS_1) || kws.containsAll(COME_AGAIN_KWS_2);
        b = COME_AGAIN.matches(kws) >= 0;
        if (b) {
            return CommandTypes.COME_AGAIN;
        }

        // 4. another
//        b = kws.containsAll(ANOTHER_KWS_1) | kws.containsAll(ANOTHER_KWS_2);
        b = ANOTHER.matches(kws) >= 0;

        if (b) {
            return CommandTypes.ANOTHER_TIME;
        }


        // 5. exit
//        b = kws.containsAll(EXIT_KWS_1);
        b = EXIT.matches(kws) >= 0;

        if (b) {
            return CommandTypes.EXIT;
        }

        // binary
//        b = kws.containsAll(BINARY_1) || kws.containsAll(BINARY_2);
        b = YES.matches(kws) >= 0 | NO.matches(kws) >= 0;
        if (b) {
            return CommandTypes.BINARY;
        }

        // 6. help
//        b = kws.containsAll(HELP_KWS_1);
        b = HELP.matches(kws) >= 0;
        if (b) {
            return CommandTypes.HELP;
        }

        return CommandTypes.UNDEFINED;
    }

    private String findFromFile(String userInput){

        String from = new ArrayList<String>(FROM.getSubset(0)).get(0);

        int c;
        String fromFile = Command.ANY_FILE;
        if ((c = userInput.indexOf(from)) != -1) {
            fromFile = userInput.substring(c + from.length());
        }

        return fromFile;
    }


}
