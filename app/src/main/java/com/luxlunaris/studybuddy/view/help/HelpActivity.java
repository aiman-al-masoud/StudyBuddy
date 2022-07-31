package com.luxlunaris.studybuddy.view.help;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.luxlunaris.studybuddy.R;

public class HelpActivity extends AppCompatActivity {

    private HelpAdapter rowAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        recyclerView = (RecyclerView) findViewById(R.id.helpRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        rowAdapter = new HelpAdapter(this);
        recyclerView.setAdapter(rowAdapter);

    }


}