package com.luxlunaris.studybuddy.view.editor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.luxlunaris.studybuddy.R;
import com.luxlunaris.studybuddy.model.utils.FileManager;

import java.io.IOException;
import java.util.EmptyStackException;
import java.util.Stack;

public class TextEditorActivity extends AppCompatActivity {

    public static final String TEXT_INPUT = "TEXT_EXTRA";
    public static final String EDITED_FILE_NAME = "EDITED_FILE_NAME";


    private EditText editText;
    private String text;
    private String fileName;
    private Toolbar toolbar;
    private Stack<String> stack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_editor);

        toolbar = (Toolbar) findViewById(R.id.textEditorToolbar);
        toolbar.setOnMenuItemClickListener(new TextEditorToolbarMenuClickListener(this));
        editText = (EditText) findViewById(R.id.textEditorEditText);
        text = getIntent().getExtras().getString(TEXT_INPUT);
        fileName = getIntent().getExtras().getString(EDITED_FILE_NAME);

        stack = new Stack<String>();


        toolbar.setTitle(fileName);
        editText.setText(text);


        editText.setOnKeyListener((a,b,c)->{
            Log.d("TextEditorActivity", "onKey: "+a);

            if(isEdited()){
                toolbar.setTitle(fileName+"*");
            }else{
                toolbar.setTitle(fileName);
            }

            return false;
        });

    }


    @Override
    public void finish() {
        Log.d("TextEditorActivity", "finish: I'm done!");

        if(isEdited()){
            askExitWithoutSavePrompt();
        }else{
            super.finish();
        }

    }


    private boolean isEdited(){
        return !editText.getText().toString().equals(text);
    }

    protected void saveChanges(){

        try {
            String newText = editText.getText().toString();
            FileManager.overwriteTextFileInRootDir(fileName, newText);

            stack.push(text);
            text = newText;

            toolbar.setTitle(fileName);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected void undo()  {

//        try{

        try{
            String t = stack.pop();
            editText.setText(t);
            toolbar.setTitle(fileName+"*");
        }catch (EmptyStackException  e){

        }

//           FileManager.overwriteTextFileInRootDir(fileName, text);
//       }catch (IOException e){
//
//       }

    }

    protected void redo(){

    }




    private void askExitWithoutSavePrompt(){

        AlertDialog.Builder builder =new AlertDialog.Builder(this);

        builder.setTitle("Exit without saving?");

        builder.setPositiveButton("Ok", (d, w)->{
            Log.d("TextEditorActivity", "askExitWithoutSavePrompt: yes<!!!+!P");
            super.finish();
        });

        builder.setNegativeButton("Cancel", (d, w)->{
            d.cancel();
        });

        builder.show();
    }




}