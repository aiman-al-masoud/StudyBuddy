package com.luxlunaris.studybuddy.view.filemanager.editor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.luxlunaris.studybuddy.R;

public class TextEditorActivity extends AppCompatActivity {

    public static final String TEXT_EXTRA = "TEXT_EXTRA";
    public static final String TEXT_OUTPUT = "TEXT_OUTPUT";
    public static final int TEXT_OUTPUT_RES_CODE = 1;


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


    @Override
    public void finish() {
        Log.d("TextEditorActivity", "finish: I'm done!");
        Intent i = new Intent();
        i.putExtra(TEXT_OUTPUT, editText.getText().toString());
        setResult(RESULT_OK, i);
        super.finish();
    }


}