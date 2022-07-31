package com.luxlunaris.studybuddy.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.luxlunaris.studybuddy.R;
import com.luxlunaris.studybuddy.model.challenge.exceptions.WrongFormatException;
import com.luxlunaris.studybuddy.model.studybuddy.StudyBuddy;
import com.luxlunaris.studybuddy.model.studybuddy.StudyBuddyListener;
import com.luxlunaris.studybuddy.model.studybuddy.commands.CommandTypes;
import com.luxlunaris.studybuddy.model.utils.Async;
import com.luxlunaris.studybuddy.model.utils.FileManager;
import com.luxlunaris.studybuddy.model.utils.FileManagerListener;
import com.luxlunaris.studybuddy.model.utils.Language;
import com.luxlunaris.studybuddy.model.utils.Permissions;
//import com.luxlunaris.studybuddy.model.utils.Settings;
import com.luxlunaris.studybuddy.view.chatui.RowAdapter;
import com.luxlunaris.studybuddy.view.help.HelpActivity;
import com.luxlunaris.studybuddy.view.intro.IntroActivity;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements StudyBuddyListener, FileManagerListener {


    private StudyBuddy studyBuddy;
    private EditText inputText;
    private FloatingActionButton micButton;
    private FloatingActionButton speakerButton;
    private RowAdapter rowAdapter;
    private RecyclerView recyclerView;
    private Toolbar myToolbar;
    private Boolean isInit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FileManager.createRootDir();
        boolean permissionsGranted = Permissions.checkPermissions(this);
        if(!permissionsGranted){
            return;
        }

        boolean langOk = Language.checkLanguage(this);

        if(!langOk){
            return;
        }

        boolean introSeen = FileManager.isIntroSeen();
        Log.d("MainActivity", "onCreate: "+introSeen);

        if(!introSeen){
            startActivityForResult(new Intent(this, IntroActivity.class), IntroActivity.SHOW_INTRO);
        }else{
            init();
        }

    }


    private void init(){

        if(isInit!=null && isInit){
            return;
        }

        isInit=true;

        // Logic
        FileManager.createRootDir();
        FileManager.createExampleCorpus(this);
        studyBuddy = new StudyBuddy(this, this);
        loadChallengesFromDisk();
        studyBuddy.startTranscribing();

        // UI
        FileManager.addListener(this);
        inputText = (EditText) findViewById(R.id.inputText);
        micButton = (FloatingActionButton) findViewById(R.id.micButton);
        speakerButton = (FloatingActionButton) findViewById(R.id.speakerButton);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        myToolbar = (Toolbar) findViewById(R.id.myToolbar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        rowAdapter = new RowAdapter(this);
        recyclerView.setAdapter(rowAdapter);
        inputText.setZ(10000);
        myToolbar.setOnMenuItemClickListener(new ToolbarMenuClickListener(this));


        micButton.setOnClickListener(e->{
            if(!studyBuddy.isKeyboardMode()){
                studyBuddy.stopTranscribing();
                micButton.setImageDrawable(getDrawable(android.R.drawable.stat_notify_call_mute));
            }else{
                studyBuddy.startTranscribing();
                micButton.setImageDrawable(getDrawable(android.R.drawable.ic_btn_speak_now));
            }

        });

        micButton.setOnLongClickListener(e->{
            Toast.makeText(this, R.string.mic_long_press, Toast.LENGTH_SHORT).show();
            return true;
        });

        inputText.setOnEditorActionListener((v, actionId, event)->{

            if( actionId == EditorInfo.IME_ACTION_SEND){
                studyBuddy.enterUserInput(v.getText().toString());
                v.setText("");
                return true;
            }

            return false;
        });

        speakerButton.setOnClickListener(e->{
            if(studyBuddy.isLoudspeakerMode()){
                studyBuddy.setLoudspeakerMode(false);
                speakerButton.setImageDrawable(getDrawable(android.R.drawable.ic_lock_silent_mode));

            }else{
                studyBuddy.setLoudspeakerMode(true);
                speakerButton.setImageDrawable(getDrawable(android.R.drawable.ic_lock_silent_mode_off));
            }
        });

        speakerButton.setOnLongClickListener(e->{
            Toast.makeText(this, R.string.speaker_long_press, Toast.LENGTH_SHORT).show();
            return true;
        });
    }




    /**
     * Async loads challenges from text files in root dir
     */
    private void loadChallengesFromDisk(){
        Async.runTask(()->{

            try {


                Log.d("MainActivity", "loadChallengesFromDisk: "+FileManager.lsRootDir());

                FileManager.lsRootDir().stream().map(n->n.split("\\.")[0]).forEach(n->{

                    Log.d("MainActivity", "loadChallengesFromDisk: "+n);
                    try {
                        String b = FileManager.readTextFileFromRootDir(n);
                        studyBuddy.setChallenges(n, b);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

            }catch (IOException e){
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("onRequestPermissionsRes", "onRequestPermissionsResult: "+requestCode+" "+ Arrays.asList(permissions) +" "+ Arrays.asList(grantResults));

        if(Arrays.stream(grantResults).allMatch(p-> p == PackageManager.PERMISSION_GRANTED)){

            if(!Language.checkLanguage(this)){
                return;
            }

            if(!FileManager.isIntroSeen()){
                startActivityForResult(new Intent(this, IntroActivity.class), IntroActivity.SHOW_INTRO);
                return;
            }

            init();

        }else{
            System.exit(0);
        }
    }

    @Override
    public void onOutput(String output) {
        Log.d("MainActivity", "onOutput: "+output);
        rowAdapter.addRow(output);
        recyclerView.scrollToPosition(rowAdapter.getItemCount()-1);
    }

    @Override
    public void onUserInput(String voiceInput) {
        Log.d("MainActivity", "onUserVoiceInput: "+voiceInput);
        rowAdapter.addRow(voiceInput);
        recyclerView.scrollToPosition(rowAdapter.getItemCount()-1);
    }

    @Override
    public void onError(String error) {
        Log.d("MainActivity", "onError: "+error);
        micButton.setImageDrawable(getDrawable(android.R.drawable.stat_notify_call_mute));
    }

    @Override
    public void onHelpCalled(CommandTypes commandTypes) {
        startActivity(new Intent(this, HelpActivity.class));
    }

    @Override
    public void onFileChanged(String title, String newBody) {
        Log.d("MainActivity", "onFileChanged: "+title);

        try{
            studyBuddy.setChallenges(title, newBody);
        }catch (WrongFormatException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onFileDeleted(String title, int oldPosition) {

    }

    @Override
    public void onFileSelected(String title) {

    }

    @Override
    public void onFileDeSelected(String title) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==IntroActivity.SHOW_INTRO){
            init();
        }

    }



}