package com.luxlunaris.studybuddy.model.studybuddy;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.luxlunaris.studybuddy.model.challenge.Challenge;
import com.luxlunaris.studybuddy.model.challenge.ChallengeBuilder;
import com.luxlunaris.studybuddy.model.challenge.ChallengeManager;
import com.luxlunaris.studybuddy.model.examiner.Examiner;
import com.luxlunaris.studybuddy.model.scribe.Scribe;
import com.luxlunaris.studybuddy.model.scribe.ScribeListener;
import com.luxlunaris.studybuddy.model.speaker.Speaker;
import com.luxlunaris.studybuddy.model.speaker.SpeakerListener;
import com.luxlunaris.studybuddy.model.studybuddy.commands.Command;
import com.luxlunaris.studybuddy.model.utils.Async;

public class StudyBuddy implements ScribeListener, SpeakerListener {

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

        Log.d("StudyBuddy", "enterUserInput: "+userInput);

        Command c = parser.parse(userInput);
        Log.d("StudyBuddy", "enterUserInput: "+c);

        //TODO: employ parser for full command parsing logic

        switch (currentMode){
            case AWAIT_ANSWER:

                String verdict = examiner.getVerdict(currentChallenge, userInput);
                speaker.speak(verdict);
                currentMode = StudyBuddyModes.AWAIT_COMMAND;

                break;
            case AWAIT_COMMAND:

                if(userInput.contains("random")){
                    currentChallenge = cm.getRandomChallenge();
                    speaker.speak(currentChallenge.question());
                    currentMode = StudyBuddyModes.AWAIT_ANSWER;
                    return;
                }

                // command not found
                speaker.speak("I didn't get what you said.");
                break;

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
