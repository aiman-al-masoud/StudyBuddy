package com.luxlunaris.studybuddy.model.scribe;

import android.content.Context;

import com.luxlunaris.studybuddy.model.scribe.classes.GoogleScribe;

public interface Scribe {

    void startTranscribing();
    void stopTranscribing();
    boolean isTranscribing();

    /**
     * Get an object with the default Scribe implementation.
     * @param context
     * @return
     */
    static Scribe getScribe(Context context, ScribeListener scribeListener){
        return new GoogleScribe(context, scribeListener);
    };


}
