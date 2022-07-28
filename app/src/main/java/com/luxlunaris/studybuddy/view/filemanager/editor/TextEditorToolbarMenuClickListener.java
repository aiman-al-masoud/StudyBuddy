package com.luxlunaris.studybuddy.view.filemanager.editor;

import android.content.Context;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.luxlunaris.studybuddy.R;
import com.luxlunaris.studybuddy.view.filemanager.FileManagerActivity;

public class TextEditorToolbarMenuClickListener implements Toolbar.OnMenuItemClickListener{

//    private Context context;
    private TextEditorActivity textEditorActivity;

    public TextEditorToolbarMenuClickListener(TextEditorActivity textEditorActivity){
        this.textEditorActivity = textEditorActivity;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()){


            case R.id.saveEditedTextItem:
                Toast.makeText(textEditorActivity, "Changes Saved! :-)", Toast.LENGTH_SHORT).show();
                textEditorActivity.saveChanges();
                break;

        }

        return false;
    }



}

