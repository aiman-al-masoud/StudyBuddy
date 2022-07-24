package com.luxlunaris.studybuddy.view;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.luxlunaris.studybuddy.R;
import com.luxlunaris.studybuddy.model.studybuddy.StudyBuddy;
import com.luxlunaris.studybuddy.model.utils.FileManager;
import com.luxlunaris.studybuddy.model.utils.Permissions;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private StudyBuddy studyBuddy;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Permissions.isPermissionGranted(this);

        studyBuddy = new StudyBuddy(this);
        studyBuddy.addChallengesFile("foo", "Who built the pyramids?\\nAliens\\n\\nWhat do cats eat?\\n\\nTuna.");
        studyBuddy.start();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("onRequestPermissionsRes", "onRequestPermissionsResult: "+requestCode+" "+ Arrays.asList(permissions) +" "+ Arrays.asList(grantResults));
        FileManager.createRootDir();
    }


}