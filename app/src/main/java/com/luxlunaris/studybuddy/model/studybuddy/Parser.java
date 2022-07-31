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
    public static KeywordSet SLOW;



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
        SLOW = new KeywordSet(context.getResources().getStringArray(R.array.slow_keywords));

    }


    public Command parse(String userInput) {

        userInput = userInput.toLowerCase();
        List<String> kws = Keywords.extractKeywords(userInput);
        CommandTypes cmdType = determineCommandType(kws);

        switch (cmdType) {

            case TELL_ME: {
                kws.removeAll(TELL_ME.getSubset(TELL_ME.matches(kws)));

                List<String> keywords = kws;

                if (RANDOM.matches(keywords) >= 0) {
                    keywords = AskMeCommand.RANDOM;
                }

                return new TellMeCommand(keywords, findFromFile(userInput));
            }
            case ASK_ME:

                kws.removeAll(ASK_ME.getSubset(ASK_ME.matches(kws)));

                List<String> keywords = kws;

                if (RANDOM.matches(keywords) >= 0) {
                    keywords = AskMeCommand.RANDOM;
                }

                return new AskMeCommand(keywords, findFromFile(userInput));

            case COME_AGAIN:

                return new ComeAgainCommand(SLOW.matches(kws)>=0);

            case ANOTHER_TIME:

                return new AnotherTimeCommand();

            case HELP:

                return new HelpCommand();
            case EXIT:

                return new ExitCommand();
            case BINARY:

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
        b = ASK_ME.matches(kws) >= 0;
        if (b) {
            return CommandTypes.ASK_ME;
        }

        // 3. come again
        b = COME_AGAIN.matches(kws) >= 0;
        if (b) {
            return CommandTypes.COME_AGAIN;
        }

        // 4. another
        b = ANOTHER.matches(kws) >= 0;

        if (b) {
            return CommandTypes.ANOTHER_TIME;
        }


        // 5. exit
        b = EXIT.matches(kws) >= 0;

        if (b) {
            return CommandTypes.EXIT;
        }

        // binary
        b = YES.matches(kws) >= 0 | NO.matches(kws) >= 0;
        if (b) {
            return CommandTypes.BINARY;
        }

        // 6. help
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
