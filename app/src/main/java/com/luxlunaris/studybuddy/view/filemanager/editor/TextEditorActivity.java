package com.luxlunaris.studybuddy.view.filemanager.editor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.luxlunaris.studybuddy.R;

public class TextEditorActivity extends AppCompatActivity {

    public static final String TEXT_EXTRA = "TEXT_EXTRA";
    EditText editText;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_editor);

        editText = (EditText) findViewById(R.id.textEditorEditText);
        text = getIntent().getExtras().getString(TEXT_EXTRA);
        editText.setText(text);
    }








}