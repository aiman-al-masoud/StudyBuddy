package com.luxlunaris.studybuddy.view;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.PermissionChecker;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.PermissionRequest;
import android.widget.Toast;

import com.luxlunaris.studybuddy.R;
import com.luxlunaris.studybuddy.model.studybuddy.StudyBuddy;
import com.luxlunaris.studybuddy.model.utils.Async;
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

        if(Permissions.isPermissionGranted(this)){
            init();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void init(){
        FileManager.createRootDir();
        studyBuddy = new StudyBuddy(this);
        String testBody = "Who built the pyramids?\nAliens\n\nWhat do cats eat?\nTuna.";
        studyBuddy.addChallengesFile("foo", testBody);
        studyBuddy.start();
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("onRequestPermissionsRes", "onRequestPermissionsResult: "+requestCode+" "+ Arrays.asList(permissions) +" "+ Arrays.asList(grantResults));

        if(Arrays.stream(grantResults).allMatch(p-> p == PackageManager.PERMISSION_GRANTED)){
            init();
        }else{
            Toast.makeText(this, "Sorry, you must grant all of the permissions to use the app.", Toast.LENGTH_LONG).show();
            Async.setTimeout(()->System.exit(0), 3000);
        }
    }


}