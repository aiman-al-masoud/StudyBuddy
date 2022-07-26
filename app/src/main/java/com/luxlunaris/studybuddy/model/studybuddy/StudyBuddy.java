package com.luxlunaris.studybuddy.model.studybuddy;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.RequiresApi;

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
import com.luxlunaris.studybuddy.model.studybuddy.commands.classes.AnotherCommand;
import com.luxlunaris.studybuddy.model.studybuddy.commands.classes.AskMeCommand;
import com.luxlunaris.studybuddy.model.studybuddy.commands.classes.BinaryCommand;
import com.luxlunaris.studybuddy.model.studybuddy.commands.classes.ComeAgainCommand;
import com.luxlunaris.studybuddy.model.studybuddy.commands.classes.TellMeCommand;
import com.luxlunaris.studybuddy.model.utils.FileManager;

public class StudyBuddy implements ScribeListener, SpeakerListener {


    public static final String NO_SUCH_FILE = "The file you specified doesn't exist!";
    public static final String NO_SUCH_KEYWORDS = "The file you specified doesn't exist!";
    public static final String NO_PREVIOUS_CMD = "No previous command to re-run!";


    private Context context;
    private Examiner examiner;
    private Scribe scribe;
    private Speaker speaker;
    private ChallengeBuilder cb;
    private ChallengeManager cm;
    private Challenge currentChallenge;
    private StudyBuddyModes currentMode;
    private Handler mainHandler;
    private Parser parser;
    private Command previousCommand;
    private Command currentCommand;
    Verdict currentVerdict;

    public StudyBuddy(Context context){
        this.context = context;
        currentMode = StudyBuddyModes.AWAIT_COMMAND;
        examiner = new Examiner();
        scribe = Scribe.getScribe(context, this);
        speaker = Speaker.getSpeaker(context, this);
        cb = new ChallengeBuilder();
        cm = new ChallengeManager();
        mainHandler = new Handler();
        parser = new Parser();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addChallengesFile(String title, String body){
        cm.addAllChallenges(cb.fromText(body,title));
    }

    public void start(){
        scribe.startTranscribing();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void runCommand(Command cmd, String userInput){

        Log.d("StudyBuddy", "runCommand: "+cmd);

        switch (currentMode){

            case AWAIT_CONFIRM_TRY_AGAIN:

                try{

                    BinaryCommand binaryCmd = (BinaryCommand)cmd;

                    if(binaryCmd.yes){
                        speaker.speak(currentChallenge.question());
                        currentMode = StudyBuddyModes.AWAIT_ANSWER;
                    }else{
                        speaker.speak(currentVerdict.text);
                        currentMode = StudyBuddyModes.AWAIT_COMMAND;
                    }

                }catch (ClassCastException e){
                    speaker.speak("I didn't get what you said. Do you want to try again, yes or no?");
                }

                break;

            case AWAIT_ANSWER:

                if(cmd.getType()== CommandTypes.COME_AGAIN){
                    ComeAgainCommand comeAgainCmd = (ComeAgainCommand)cmd;
                    speaker.speak(currentChallenge.question(), comeAgainCmd.slowly? Speaker.SLOW : Speaker.NORMAL );
                    return;
                }

                currentVerdict = examiner.getVerdict(currentChallenge, userInput);

                if(currentVerdict.isFail){
                    currentMode = StudyBuddyModes.AWAIT_CONFIRM_TRY_AGAIN;
                    speaker.speak("Your answer is wrong, wish to try again? Yes or no?");
                }else{
                    speaker.speak(currentVerdict.text);
                    currentMode = StudyBuddyModes.AWAIT_COMMAND;
                }

                break;

            case AWAIT_COMMAND:

                switch (cmd.getType()){
                    case ASK_ME:

                        AskMeCommand askMeCmd =  ((AskMeCommand)cmd);

                        try{

                            if(askMeCmd.random){
                                currentChallenge = cm.getRandomChallenge(askMeCmd.fromFile);
                            }else {
                                currentChallenge = cm.getChallengeByKeywords(askMeCmd.keywords, askMeCmd.fromFile);
                            }

                            speaker.speak(currentChallenge.question());
                            currentMode = StudyBuddyModes.AWAIT_ANSWER;

                        }catch (NoSuchFileException e){
                            speaker.speak(NO_SUCH_FILE);
                        }catch (NoSuchKeywordsException e){
                            speaker.speak(NO_SUCH_KEYWORDS);
                        }

                        return;
                    case TELL_ME:

                        TellMeCommand tellMeCmd = (TellMeCommand) cmd;

                        try {
                            Challenge c = cm.getChallengeByKeywords(tellMeCmd.keywords);
                            speaker.speak(c.question()+".\n"+c.answer());
                        }catch (NoSuchFileException e){
                            speaker.speak(NO_SUCH_FILE);
                        }catch (NoSuchKeywordsException e){
                            speaker.speak(NO_SUCH_KEYWORDS);
                        }

                        return;
                    case EXIT:
                        System.exit(0);
                        return;
                    case HELP:
                        speaker.speak("Help is coming....");
                        return;
                    case ANOTHER:

                        if(  previousCommand!=null  &&  ! (previousCommand instanceof AnotherCommand)   ){
                            runCommand(previousCommand, userInput);
                        }else{
                            speaker.speak(NO_PREVIOUS_CMD);
                        }

                        return;

                    default: // command not found
                        speaker.speak("I didn't get what you said.");
                        return;
                }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void enterUserInput(String userInput){

        // AnotherCommand type should never become the previous command
        if( ! (currentCommand instanceof AnotherCommand) ){
            previousCommand = currentCommand;
        }

        currentCommand = parser.parse(userInput);
        runCommand(currentCommand, userInput);
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onTranscription(String transcription) {
        Log.d("StudyBuddy", "onTranscription: "+transcription);
        enterUserInput(transcription);
    }

    @Override
    public void onError(int error) {
        Log.d("StudyBuddy.onError()", error+"");
    }


    @Override
    public void startedSpeaking(String speechId) {

    }

    @Override
    public void stoppedSpeaking(String speechId) {

        mainHandler.post(()->{
            scribe.startTranscribing();
        });

    }



}
