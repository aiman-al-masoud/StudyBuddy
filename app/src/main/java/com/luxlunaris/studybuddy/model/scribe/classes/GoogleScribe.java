package com.luxlunaris.studybuddy.model.scribe.classes;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import com.luxlunaris.studybuddy.model.scribe.Scribe;
import com.luxlunaris.studybuddy.model.scribe.ScribeListener;

import java.util.List;

public class GoogleScribe implements Scribe, RecognitionListener {

    private Context context;
    private SpeechRecognizer speechRecognizer;
    private boolean isListening;
    private Intent speechRecognizerIntent;


    /**
     * The param context should also implement ScribeListener.
     * @param context
     */
    public GoogleScribe(Context context){
        this.context = context;
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);

        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en-US");

        // check if offline usage is still supported
        if(isGoogleSearchVersionCivilized()){
            speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true);
        }

    }

    @Override
    public void startTranscribing() {

        // mute beep-bop mic de/activation noises
        AudioManager aManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        aManager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
        aManager.setStreamMute(AudioManager.STREAM_ALARM, true);
        aManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
        aManager.setStreamMute(AudioManager.STREAM_RING, true);
        aManager.setStreamMute(AudioManager.STREAM_SYSTEM, true);

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
        ((ScribeListener)context).onError(i);
    }

    @Override
    public void onResults(Bundle bundle) {
        List<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        ((ScribeListener)context).onTranscription(data.toString());
        startTranscribing();
    }

    @Override
    public void onPartialResults(Bundle bundle) {

    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }


}
