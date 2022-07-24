package com.luxlunaris.studybuddy.model.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Permissions {

    public static final int RECORD_AUDIO_PERMISSION_CODE = 1;
    public static final int WRITE_EXTERNAL_STORAGE_PERMISSION_CODE = 2;


    public static boolean isRecordAudioPermissionGranted(Context context) {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO_PERMISSION_CODE);
            return false;
        }

        return true;
    }

    public static boolean isStoragePermissionGranted(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // already granted
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            }

            // has to be requested
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_PERMISSION_CODE);
            return false;

        } else {
            return true; //granted upon installation on pre-M
        }
    }


}
