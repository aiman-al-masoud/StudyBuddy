package com.luxlunaris.studybuddy.view.editor;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;

import com.luxlunaris.studybuddy.R;
import com.luxlunaris.studybuddy.model.challenge.ChallengeBuilder;
import com.luxlunaris.studybuddy.model.challenge.exceptions.WrongFormatException;
import com.luxlunaris.studybuddy.model.utils.FileManager;

import java.io.IOException;
import java.util.Arrays;
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
            Toast.makeText(this, "Saved! :-)", Toast.LENGTH_SHORT).show();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected void undo() {

        try {

            editText.setText(stack.pop());
            checkFormat(editText.getText().toString());

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
            cb.fromText(text, "doesntmatter");
            formatErrorTextView.setText("");
            formatErrorTextView.setVisibility(View.GONE);
            removeHighlights();

        } catch (WrongFormatException e) {
            formatErrorTextView.setText(e.getMessage());
            formatErrorTextView.setTextColor(Color.RED);
            formatErrorTextView.setVisibility(View.VISIBLE);

            try{
                int parNum = Integer.parseInt(e.getMessage().replaceAll("\\D*",""));
                highlightParagraph(parNum);
            }catch (NumberFormatException ex ){
                ex.printStackTrace();
            }

        }
    }


    private Pair<Integer, Integer> paragraphNumToSpanBounds(int paragraphNum){

        try{
            String[] pars = editText.getText().toString().split("\\n{2,}");

            Log.d("TextEditorActivity", "paragraphNumToSpanBounds: "+ Arrays.asList(pars));


            String par = pars[paragraphNum-1];

            Log.d("TextEditorActivity", "paragraphNumToSpanBounds: "+par);


            int start = editText.getText().toString().indexOf(par);
            int end = start + par.length();
            return new Pair<>(start, end);
        }catch (IndexOutOfBoundsException e ){

        }

        return  new Pair<>(0,0);
    }

    private void highlightParagraph(int paragraphNumber){

        int selStart = editText.getSelectionStart();


        Pair<Integer, Integer> p = paragraphNumToSpanBounds(paragraphNumber);

        Log.d("TextEditorActivity", "highlightParagraph: "+p);

        if(p.first==0 && p.second==0){
            return;
        }

        SpannableString s = new SpannableString(editText.getText().toString());
        s.setSpan(new BackgroundColorSpan(Color.rgb( 100, 10, 10)), p.first, p.second, 0);
        editText.setText(s);

        editText.setSelection(selStart);
    }

    private void removeHighlights(){
        int selStart = editText.getSelectionStart();

        editText.setText(editText.getText().toString());
        editText.setSelection(selStart);

    }



}