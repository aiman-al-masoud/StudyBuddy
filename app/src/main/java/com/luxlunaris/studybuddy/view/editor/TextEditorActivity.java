package com.luxlunaris.studybuddy.view.editor;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.luxlunaris.studybuddy.R;
import com.luxlunaris.studybuddy.model.challenge.ChallengeBuilder;
import com.luxlunaris.studybuddy.model.challenge.exceptions.WrongFormatException;
import com.luxlunaris.studybuddy.model.utils.FileManager;

import java.io.IOException;
import java.util.EmptyStackException;
import java.util.Stack;

public class TextEditorActivity extends AppCompatActivity {

    public static final String TEXT_INPUT = "TEXT_EXTRA";
    public static final String EDITED_FILE_NAME = "EDITED_FILE_NAME";

    protected EditText editText;
    protected String textOnDisk;
    protected String fileName;
    protected Toolbar toolbar;
    protected Stack<String> stack;
    protected TextView formatErrorTextView; //error bar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_editor);

        toolbar = (Toolbar) findViewById(R.id.textEditorToolbar);
        toolbar.setOnMenuItemClickListener(new TextEditorToolbarMenuClickListener(this));
        editText = (EditText) findViewById(R.id.textEditorEditText);
        textOnDisk = getIntent().getExtras().getString(TEXT_INPUT);
        fileName = getIntent().getExtras().getString(EDITED_FILE_NAME);
        stack = new Stack<String>();
        formatErrorTextView = (TextView) findViewById(R.id.formatErrorTextView);

        toolbar.setTitle(fileName);
        editText.setText(textOnDisk);
        stack.push(textOnDisk);
        checkFormat(textOnDisk);

        editText.setOnKeyListener((a, b, c) -> {


            String newText = editText.getText().toString();
            checkFormat(newText);


            if (isEdited()) {
                stack.push(newText);
                toolbar.getMenu().findItem(R.id.undoItem).setVisible(true);
                toolbar.setTitle(fileName + "*");
            } else {
                toolbar.setTitle(fileName);
                toolbar.getMenu().findItem(R.id.undoItem).setVisible(false);

            }

            return false;
        });

    }


    @Override
    public void finish() {

        if (isEdited()) {
            askExitWithoutSavePrompt();
        } else {
            Log.d("TextEditorActivity", "finish: I'm done!");
            super.finish();
        }

    }


    private boolean isEdited() {
        return !editText.getText().toString().equals(textOnDisk);
    }

    protected void saveChanges() {


        try {
            String newText = editText.getText().toString();
            FileManager.overwriteTextFileInRootDir(fileName, newText);

            textOnDisk = newText;
            stack.clear();
            toolbar.setTitle(fileName);
            toolbar.getMenu().findItem(R.id.undoItem).setVisible(false);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected void undo() {

        try {

            editText.setText(stack.pop());

            if (!isEdited()) {
                toolbar.getMenu().findItem(R.id.undoItem).setVisible(false);
                toolbar.setTitle(fileName);
            }

        } catch (EmptyStackException e) {

        }

    }


    private void askExitWithoutSavePrompt() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Exit without saving?");

        builder.setPositiveButton("Ok", (d, w) -> {
            Log.d("TextEditorActivity", "finish: I'm done!");
            super.finish();
        });

        builder.setNegativeButton("Cancel", (d, w) -> {
            d.cancel();
        });

        builder.show();
    }


    private void checkFormat(final String text) {
        ChallengeBuilder cb = new ChallengeBuilder();

        try {
            cb.fromText(text, "name");
            formatErrorTextView.setText("");
            formatErrorTextView.setVisibility(View.GONE);
        } catch (WrongFormatException e) {
            formatErrorTextView.setText(e.getMessage());
            formatErrorTextView.setVisibility(View.VISIBLE);
            Log.d("TextEditorActivity", "onCreate: " + e.getMessage());
        }
    }


}