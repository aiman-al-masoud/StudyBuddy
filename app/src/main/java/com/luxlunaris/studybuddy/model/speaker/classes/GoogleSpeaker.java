package com.luxlunaris.studybuddy.model.speaker.classes;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import com.luxlunaris.studybuddy.model.speaker.Speaker;
import com.luxlunaris.studybuddy.model.speaker.SpeakerListener;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.Locale;

public class GoogleSpeaker extends UtteranceProgressListener implements Speaker {

    private TextToSpeech tts;
    private SpeakerListener speakerListener;


    public GoogleSpeaker(Context context, SpeakerListener speakerListener){
        tts = new TextToSpeech(context, e->{
            //on init
        });

        tts.setOnUtteranceProgressListener(this);
        this.speakerListener  =speakerListener;

//        String locale = context.getResources().getConfiguration().locale.getCountry();
//        Log.d("GoogleSpeaker", "GoogleSpeaker: "+locale);

//        Arrays.stream(Locale.getAvailableLocales()).forEach(l->{
//            Log.d("GoogleSpeaker", "GoogleSpeaker: "+l);
//        });


        //TODO: check locale
//        tts.setLanguage(Locale.US);
//        tts.setLanguage()

//        Locale l = Locale.forLanguageTag("it");
//        tts.setLanguage(l);
//        tts.setLanguage(new Locale("it_IT"));

    }

    @Override
    public void speak(String speech) {
        speak(speech, NORMAL);
    }

    @Override
    public void speak(String speech, float speechRate) {
        tts.setSpeechRate(speechRate);
        tts.speak(speech, TextToSpeech.QUEUE_FLUSH, null, "1");
    }

    @Override
    public void stop() {
        this.tts.stop();
    }

    @Override
    public void onStart(String s) {
        Log.d("GoogleSpeaker", "onStart: "+s);
        speakerListener.startedSpeaking(s);
    }

    @Override
    public void onDone(String s) {
        Log.d("GoogleSpeaker", "onDone: "+s);
        speakerListener.stoppedSpeaking(s);
    }

    @Override
    public void onError(String s) {
        Log.d("GoogleSpeaker", "onError: "+s);
    }

}
