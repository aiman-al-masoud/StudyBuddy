package com.luxlunaris.studybuddy.model.speaker.classes;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import com.luxlunaris.studybuddy.model.speaker.Speaker;

import java.util.Locale;

public class GoogleSpeaker implements Speaker {

    private TextToSpeech tts;

    public GoogleSpeaker(Context context){
        tts = new TextToSpeech(context, e->{
            //on init
        });

        tts.setLanguage(Locale.US);
    }

    @Override
    public void speak(String speech) {
        tts.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
    }

}
