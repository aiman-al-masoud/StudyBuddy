package com.luxlunaris.studybuddy.model.utils;

public interface FileManagerListener {

    void onFileChanged(String title, String newBody);
    void onFileDeleted(String title, int oldPosition);
    void onFileSelected(String title);
    void onFileDeSelected(String title);


}
