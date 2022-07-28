package com.luxlunaris.studybuddy.view.filemanager.editor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.luxlunaris.studybuddy.R;
import com.luxlunaris.studybuddy.view.ToolbarMenuClickListener;

public class TextEditorActivity extends AppCompatActivity {

    public static final String TEXT_INPUT = "TEXT_EXTRA";
    public static final String TEXT_OUTPUT = "TEXT_OUTPUT";
    public static final String EDITED_FILE_NAME = "EDITED_FILE_NAME";
    public static final int TEXT_OUTPUT_RES_CODE = 1;


    private EditText editText;
    private String text;
    private String fileName;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_editor);

        toolbar = (Toolbar) findViewById(R.id.textEditorToolbar);
        toolbar.setOnMenuItemClickListener(new TextEditorToolbarMenuClickListener(this));
        editText = (EditText) findViewById(R.id.textEditorEditText);
        text = getIntent().getExtras().getString(TEXT_INPUT);
        fileName = getIntent().getExtras().getString(EDITED_FILE_NAME);
        editText.setText(text);
    }


    @Override
    public void finish() {
        Log.d("TextEditorActivity", "finish: I'm done!");
        Intent i = new Intent();
        i.putExtra(TEXT_OUTPUT, editText.getText().toString());
        i.putExtra(EDITED_FILE_NAME, fileName);
        setResult(RESULT_OK, i);
        super.finish();
    }


}