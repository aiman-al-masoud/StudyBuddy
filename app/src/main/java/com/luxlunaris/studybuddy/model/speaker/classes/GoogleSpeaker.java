package com.luxlunaris.studybuddy.model.speaker.classes;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.luxlunaris.studybuddy.model.speaker.Speaker;

import java.util.Locale;

public class GoogleSpeaker implements Speaker {

    private TextToSpeech tts;

    public GoogleSpeaker(Context context){
        tts = new TextToSpeech(context, e->{
            //on init
            Log.d("GoogleSpeaker()", "on init");
        });

        //TODO: check locale
//        tts.setLanguage(Locale.US);
    }

    @Override
    public void speak(String speech) {
        Log.d("GoogleSpeaker.speak()", "speech: "+speech);
        tts.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
    }

}
