package com.luxlunaris.studybuddy.model.studybuddy;

import android.content.Context;
import android.os.Handler;
import android.util.Log;


import com.luxlunaris.studybuddy.model.challenge.Challenge;
import com.luxlunaris.studybuddy.model.challenge.ChallengeBuilder;
import com.luxlunaris.studybuddy.model.challenge.ChallengeManager;
import com.luxlunaris.studybuddy.model.challenge.exceptions.NoSuchFileException;
import com.luxlunaris.studybuddy.model.challenge.exceptions.NoSuchKeywordsException;
import com.luxlunaris.studybuddy.model.examiner.Examiner;
import com.luxlunaris.studybuddy.model.examiner.Verdict;
import com.luxlunaris.studybuddy.model.scribe.Scribe;
import com.luxlunaris.studybuddy.model.scribe.ScribeListener;
import com.luxlunaris.studybuddy.model.speaker.Speaker;
import com.luxlunaris.studybuddy.model.speaker.SpeakerListener;
import com.luxlunaris.studybuddy.model.studybuddy.commands.Command;
import com.luxlunaris.studybuddy.model.studybuddy.commands.CommandTypes;
import com.luxlunaris.studybuddy.model.studybuddy.commands.classes.AnotherTimeCommand;
import com.luxlunaris.studybuddy.model.studybuddy.commands.classes.AskMeCommand;
import com.luxlunaris.studybuddy.model.studybuddy.commands.classes.BinaryCommand;
import com.luxlunaris.studybuddy.model.studybuddy.commands.classes.ComeAgainCommand;
import com.luxlunaris.studybuddy.model.studybuddy.commands.classes.TellMeCommand;

public class StudyBuddy implements ScribeListener, SpeakerListener {


    public static final String NO_SUCH_FILE = "The file you specified doesn't exist!";
    public static final String NO_SUCH_KEYWORDS = "No match found for that topic!";
    public static final String NO_PREVIOUS_CMD = "No previous command to re-run!";


    private final Context context;
    private final Examiner examiner;
    private final Scribe scribe;
    private final Speaker speaker;
    private final ChallengeBuilder cb;
    private final ChallengeManager cm;
    private final Handler mainHandler;
    private final Parser parser;
    private Challenge currentChallenge;
    private StudyBuddyModes currentMode;
    private Command previousCommand;
    private Command currentCommand;
    private Verdict currentVerdict;
    private boolean keyboardMode;
    private StudyBuddyListener listener;

    public StudyBuddy(Context context, StudyBuddyListener listener){
        this.context = context;
        currentMode = StudyBuddyModes.AWAIT_COMMAND;
        examiner = new Examiner();
        scribe = Scribe.getScribe(context, this);
        speaker = Speaker.getSpeaker(context, this);
        cb = new ChallengeBuilder();
        cm = new ChallengeManager();
        mainHandler = new Handler();
        parser = new Parser();
        keyboardMode = false;
        this.listener = listener;
    }

    public void addChallengesFile(String title, String body){
        cm.addAllChallenges(cb.fromText(body,title));
    }

    public void startTranscribing(){
        keyboardMode = false;
        scribe.startTranscribing();
    }

    public void stopTranscribing(){
        keyboardMode = true;
        scribe.stopTranscribing();
    }

    public boolean isKeyboardMode(){
        return keyboardMode;
    }

    private void output(String finalOutput){
        output(finalOutput, Speaker.NORMAL);
    }

    private void output(String finalOutput, float speechRate){
        speaker.speak(finalOutput, speechRate);
        listener.onOutput(finalOutput);
    }

    public void enterUserInput(final String userInput){

        // AnotherCommand type should never become the previous command
        previousCommand = (currentCommand instanceof AnotherTimeCommand)? previousCommand : currentCommand;

        currentCommand = parser.parse(userInput);

        switch (currentMode){
            case AWAIT_CONFIRM_TRY_AGAIN:
                onConfirmTryAgain(currentCommand);
                break;
            case AWAIT_ANSWER:
                onAnswer(currentCommand, userInput);
                break;
            case AWAIT_COMMAND:
                runCommand(currentCommand);
                break;
        }

    }


    private void runCommand(final Command cmd){

        Log.d("StudyBuddy", "runCommand: "+cmd);

        switch (cmd.getType()){
            case ASK_ME:

                AskMeCommand askMeCmd =  ((AskMeCommand)cmd);

                try{

                    if(askMeCmd.random){
                        currentChallenge = cm.getRandomChallenge(askMeCmd.fromFile);
                    }else {
                        currentChallenge = cm.getChallengeByKeywords(askMeCmd.keywords, askMeCmd.fromFile);
                    }

                    output(currentChallenge.question());
                    currentMode = StudyBuddyModes.AWAIT_ANSWER;

                }catch (NoSuchFileException e){
                    output(NO_SUCH_FILE);
                }catch (NoSuchKeywordsException e){
                    output(NO_SUCH_KEYWORDS);
                }

                return;
            case TELL_ME:

                TellMeCommand tellMeCmd = (TellMeCommand) cmd;

                try {
                    Challenge c = cm.getChallengeByKeywords(tellMeCmd.keywords);
                    output(c.question()+".\n"+c.answer());
                }catch (NoSuchFileException e){
                    output(NO_SUCH_FILE);
                }catch (NoSuchKeywordsException e){
                    output(NO_SUCH_KEYWORDS);
                }

                return;
            case EXIT:

                System.exit(0);
                return;
            case HELP:

                output("Help is coming....");
                return;
            case ANOTHER_TIME:

                if(  previousCommand!=null  &&  ! (previousCommand instanceof AnotherTimeCommand)   ){
                    runCommand(previousCommand);
                }else{
                    output(NO_PREVIOUS_CMD);
                }

                return;

            case UNDEFINED: // command not found
            default:
                output("I didn't get what you said.");
        }

    }


    private void onAnswer(Command cmd, String userInput){

        if(cmd.getType()== CommandTypes.COME_AGAIN){
            ComeAgainCommand comeAgainCmd = (ComeAgainCommand)cmd;
            output(currentChallenge.question(), comeAgainCmd.slowly? Speaker.SLOW : Speaker.NORMAL);
            return;
        }

        currentVerdict = examiner.getVerdict(currentChallenge, userInput);

        if(currentVerdict.isFail){
            currentMode = StudyBuddyModes.AWAIT_CONFIRM_TRY_AGAIN;
            output("Your answer is wrong, wish to try again? Yes or no?");
        }else{
            output(currentVerdict.text);
            currentMode = StudyBuddyModes.AWAIT_COMMAND;
        }

    }

    private void onConfirmTryAgain(Command cmd){

        try{

            BinaryCommand binaryCmd = (BinaryCommand)cmd;

            if(binaryCmd.yes){
                output(currentChallenge.question());
                currentMode = StudyBuddyModes.AWAIT_ANSWER;
            }else{
                output(currentVerdict.text);
                currentMode = StudyBuddyModes.AWAIT_COMMAND;
            }

        }catch (ClassCastException e){
            output("I didn't get what you said. Do you want to try again, yes or no?");
        }

    }


    @Override
    public void onTranscription(String transcription) {
        Log.d("StudyBuddy", "onTranscription: "+transcription);

        enterUserInput(transcription);
        listener.onUserVoiceInput(transcription);
    }

    @Override
    public void onError(int error) {
        Log.d("StudyBuddy.onError()", error+"");
        this.keyboardMode = true;
        listener.onError(error+"");
    }

    @Override
    public void startedSpeaking(String speechId) {

    }

    @Override
    public void stoppedSpeaking(String speechId) {

        if(keyboardMode){
            return;
        }

        mainHandler.post(scribe::startTranscribing);

    }



}
