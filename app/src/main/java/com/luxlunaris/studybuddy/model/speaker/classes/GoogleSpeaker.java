package com.luxlunaris.studybuddy.model.speaker.classes;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import com.luxlunaris.studybuddy.model.speaker.Speaker;

import java.util.Locale;

public class GoogleSpeaker extends UtteranceProgressListener implements Speaker {

    private TextToSpeech tts;
    private volatile boolean isSpeaking;


    public GoogleSpeaker(Context context){
        tts = new TextToSpeech(context, e->{
            //on init
        });

        tts.setOnUtteranceProgressListener(this);
        isSpeaking  = false;

        //TODO: check locale
//        tts.setLanguage(Locale.US);
    }

    @Override
    public void speak(String speech) {
        Log.d("GoogleSpeaker.speak()", "speech: "+speech);
        tts.speak(speech, TextToSpeech.QUEUE_FLUSH, null, "1");
    }

    @Override
    public boolean isSpeaking() {
        Log.d("GoogleSpeaker", "isSpeaking: "+tts.isSpeaking());
        return isSpeaking;
    }

    @Override
    public void onStart(String s) {
        Log.d("GoogleSpeaker", "onStart: "+s);
        isSpeaking = true;

    }

    @Override
    public void onDone(String s) {
        Log.d("GoogleSpeaker", "onDone: "+s);
        isSpeaking = false;
    }

    @Override
    public void onError(String s) {
        Log.d("GoogleSpeaker", "onError: "+s);
    }
}
