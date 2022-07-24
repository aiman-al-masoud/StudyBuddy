package com.luxlunaris.studybuddy.model.scribe;

import android.speech.RecognitionListener;

public interface ScribeListener {

    void onTranscription(String transcription);
    void onError(int error);

}
