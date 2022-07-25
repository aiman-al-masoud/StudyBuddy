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
import com.luxlunaris.studybuddy.model.scribe.Scribe;
import com.luxlunaris.studybuddy.model.scribe.ScribeListener;
import com.luxlunaris.studybuddy.model.speaker.Speaker;
import com.luxlunaris.studybuddy.model.speaker.SpeakerListener;
import com.luxlunaris.studybuddy.model.studybuddy.commands.Command;
import com.luxlunaris.studybuddy.model.studybuddy.commands.CommandTypes;
import com.luxlunaris.studybuddy.model.studybuddy.commands.classes.AskMeCommand;
import com.luxlunaris.studybuddy.model.studybuddy.commands.classes.TellMeCommand;
import com.luxlunaris.studybuddy.model.utils.FileManager;

public class StudyBuddy implements ScribeListener, SpeakerListener {


    public static final String NO_SUCH_FILE = "The file you specified doesn't exist!";
    public static final String NO_SUCH_KEYWORDS = "The file you specified doesn't exist!";


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

    public void start(){
        scribe.startTranscribing();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void enterUserInput(String userInput){

        Command cmd = parser.parse(userInput);
        Log.d("StudyBuddy", "enterUserInput: "+userInput);
        Log.d("StudyBuddy", "enterUserInput: "+cmd);


        switch (currentMode){

            case AWAIT_ANSWER:

                if(cmd.getType()== CommandTypes.COME_AGAIN){
                    speaker.speak(currentChallenge.question());
                }else{
                    String verdict = examiner.getVerdict(currentChallenge, userInput);
                    speaker.speak(verdict);
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
                        // a bit more complicated
                        return;

                    default: // command not found
                        speaker.speak("I didn't get what you said.");
                        return;
                }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addChallengesFile(String title, String body){
        cm.addAllChallenges(cb.fromText(body,title));
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
