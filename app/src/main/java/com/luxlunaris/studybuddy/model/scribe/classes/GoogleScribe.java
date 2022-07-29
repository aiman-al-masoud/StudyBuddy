package com.luxlunaris.studybuddy.model.scribe.classes;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import com.luxlunaris.studybuddy.model.scribe.Scribe;
import com.luxlunaris.studybuddy.model.scribe.ScribeListener;
import com.luxlunaris.studybuddy.model.utils.Language;

import java.util.List;

public class GoogleScribe implements Scribe, RecognitionListener {

    private Context context;
    private SpeechRecognizer speechRecognizer;
    private boolean isListening;
    private Intent speechRecognizerIntent;
    private ScribeListener scribeListener;



    public GoogleScribe(Context context, ScribeListener scribeListener){
        this.context = context;
        this.scribeListener  = scribeListener;

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);

        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Language.getLanguage());
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, Language.getLanguage());


        // check if offline usage is still supported
        if(isGoogleSearchVersionCivilized()){
            speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true);
        }

        speechRecognizer.setRecognitionListener(this);

    }

    @Override
    public void startTranscribing() {

        speechRecognizer.startListening(speechRecognizerIntent);
        isListening = true;

    }

    @Override
    public void stopTranscribing() {
        speechRecognizer.stopListening();
        isListening = false;
    }

    @Override
    public boolean isTranscribing() {
        return isListening;
    }


    /**
     * Newer versions (11<=) of this stupid API prevent speech recognition
     * from working offline. Never trust an update from Google.
     *
     * https://stackoverflow.com/questions/64708403/android-speech-recognizer-no-longer-working-offline
     * @return
     */
    private boolean isGoogleSearchVersionCivilized(){

        try{

            String versionName = context.getPackageManager().getPackageInfo("com.google.android.googlequicksearchbox", 0).versionName;
            int v = Integer.parseInt(versionName.split("\\.")[0]);

            // latest civilized version: 10.99.8
            if( v <= 10 ){
                return true;
            }

        }catch (PackageManager.NameNotFoundException | NumberFormatException e){
            /* sigh */
        }

        return false; // screw you Google.
    }


    @Override
    public void onReadyForSpeech(Bundle bundle) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float v) {

    }

    @Override
    public void onBufferReceived(byte[] bytes) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int i) {
        scribeListener.onError(i);
    }

    @Override
    public void onResults(Bundle bundle) {
        List<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        scribeListener.onTranscription(data.toString());
    }

    @Override
    public void onPartialResults(Bundle bundle) {

    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }


}
