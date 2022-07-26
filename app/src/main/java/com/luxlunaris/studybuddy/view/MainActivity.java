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

import java.io.File;
import java.io.IOException;
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

        
        // async load from text files in root dir
        Async.runTask(()->{

            try {
                FileManager.lsRootDir().stream().map(n->n.split("\\.")[0]).forEach(n->{
                    try {
                        String b = FileManager.readTextFileFromRootDir(n);
                        studyBuddy.addChallengesFile(n, b);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            }catch (IOException e){

            }
        });

        // start study buddy
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
            Toast.makeText(this, "Sorry, you must grant all of the permissions or the app won't work.", Toast.LENGTH_LONG).show();
            Async.setTimeout(()->System.exit(0), 3000);
        }
    }


}