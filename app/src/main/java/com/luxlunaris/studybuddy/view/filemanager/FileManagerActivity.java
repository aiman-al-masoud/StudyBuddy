package com.luxlunaris.studybuddy.view.filemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.luxlunaris.studybuddy.R;
import com.luxlunaris.studybuddy.view.filemanager.list.FileList;

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
}