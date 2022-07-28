package com.luxlunaris.studybuddy.view.filemanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.luxlunaris.studybuddy.R;
import com.luxlunaris.studybuddy.model.utils.FileManager;
import com.luxlunaris.studybuddy.view.filemanager.editor.TextEditorActivity;
import com.luxlunaris.studybuddy.view.filemanager.list.FileList;

import java.io.IOException;

public class FileManagerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FileList rowAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_manager);

        recyclerView = (RecyclerView) findViewById(R.id.fileManagerRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        rowAdapter = new FileList(this);
        recyclerView.setAdapter(rowAdapter);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String newText = data.getStringExtra(TextEditorActivity.TEXT_OUTPUT);
        String fileName = data.getStringExtra(TextEditorActivity.EDITED_FILE_NAME);


        Log.d("FileManagerActivity", "onActivityResult: "+fileName);

        Log.d("FileManagerActivity", "onActivityResult: "+newText);

        try {
            FileManager.overwriteTextFileInRootDir(fileName, newText);
            Toast.makeText(this, "Saved Changes!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

}