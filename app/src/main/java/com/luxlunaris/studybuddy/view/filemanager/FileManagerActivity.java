package com.luxlunaris.studybuddy.view.filemanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.luxlunaris.studybuddy.R;
import com.luxlunaris.studybuddy.model.utils.FileManager;
import com.luxlunaris.studybuddy.view.filemanager.list.FileList;

import java.io.IOException;

public class FileManagerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FileList rowAdapter;
    private FloatingActionButton addFileFab;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_manager);

        recyclerView = (RecyclerView) findViewById(R.id.fileManagerRecyclerView);
        addFileFab = (FloatingActionButton) findViewById(R.id.fileManagerAddFileFab);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        rowAdapter = new FileList(this);
        recyclerView.setAdapter(rowAdapter);
        addFileFab.setOnClickListener(this::askNewFileName);

        toolbar = (Toolbar) findViewById(R.id.fileManagerToolbar);
        toolbar.setOnMenuItemClickListener(new FileManagerToolbarMenuClickListener(toolbar.getMenu(), this));


        addFileFab.setOnLongClickListener(e->{
            Toast.makeText(this, R.string.click_to_create_new_file, Toast.LENGTH_SHORT).show();
            return true;
        });

    }

    private void askNewFileName(View view) {

        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        input.setText(R.string.new_file_name);
        input.requestFocus();
        builder.setTitle(R.string.create_new_file);

        builder.setView(input);

        builder.setPositiveButton(R.string.ok, (d, w)->{
            Log.d("FileManagerActivity", "askNewFileName: "+input.getText().toString());

            try {
                FileManager.writeTextFileToRootDir(input.getText().toString().replace(".txt", ""), "");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        builder.setNegativeButton(R.string.cancel, (d, w)->{
            d.cancel();
        });

        builder.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_OK){
            Log.d("FileManagerActivity", "onActivityResult: error: "+resultCode);
            return;
        }

        if(data == null){
            Log.d("FileManagerActivity", "onActivityResult: data: "+data);
            return;
        }

        if(requestCode == FileManager.PICK_TEXT_FILE_REQUEST_CODE){
            try {
                String title = FileManager.fileNameFromUri(data.getData()).replace(".txt", "");
                String body = FileManager.uriToText(this, data.getData());

                Log.d("FileManagerActivity", "onActivityResult: "+title);
                Log.d("FileManagerActivity", "onActivityResult: "+body);

                FileManager.writeTextFileToRootDir(title, body);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }








}