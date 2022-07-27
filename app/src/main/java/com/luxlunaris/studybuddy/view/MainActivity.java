package com.luxlunaris.studybuddy.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.luxlunaris.studybuddy.R;
import com.luxlunaris.studybuddy.model.studybuddy.StudyBuddy;
import com.luxlunaris.studybuddy.model.studybuddy.StudyBuddyListener;
import com.luxlunaris.studybuddy.model.utils.Async;
import com.luxlunaris.studybuddy.model.utils.FileManager;
import com.luxlunaris.studybuddy.model.utils.Permissions;

import java.io.IOException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements StudyBuddyListener {

    private StudyBuddy studyBuddy;
    private EditText inputText;
    private FloatingActionButton micButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Permissions.checkPermissions(this)){
            init();
        }


        inputText = (EditText) findViewById(R.id.inputText);
        micButton = (FloatingActionButton) findViewById(R.id.micButton);


        micButton.setOnClickListener(e->{
            if(!studyBuddy.isKeyboardMode()){
                studyBuddy.stopTranscribing();
                micButton.setImageDrawable(getDrawable(android.R.drawable.stat_notify_call_mute));
            }else{
                studyBuddy.startTranscribing();
                micButton.setImageDrawable(getDrawable(android.R.drawable.ic_btn_speak_now));
            }

        });

        inputText.setOnEditorActionListener((v, actionId, event)->{

            if( actionId == EditorInfo.IME_ACTION_SEND){
                studyBuddy.enterUserInput(v.getText().toString());
                v.setText("");
                return true;
            }

            return false;
        });


    }

    private void init(){
        FileManager.createRootDir();
        studyBuddy = new StudyBuddy(this, this);
        loadChallengesFromDisk();
        studyBuddy.startTranscribing();
    }


    /**
     * Async loads challenges from text files in root dir
     */
    private void loadChallengesFromDisk(){
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
    }

    @Override
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


    @Override
    public void onOutput(String output) {
        Log.d("MainActivity", "onOutput: "+output);
    }

    @Override
    public void onUserVoiceInput(String voiceInput) {
        Log.d("MainActivity", "onUserVoiceInput: "+voiceInput);

    }

    @Override
    public void onError(String error) {
        Log.d("MainActivity", "onError: "+error);
    }

}