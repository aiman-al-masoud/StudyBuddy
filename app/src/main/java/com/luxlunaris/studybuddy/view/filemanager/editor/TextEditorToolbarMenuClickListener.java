package com.luxlunaris.studybuddy.view.filemanager.editor;

import android.content.Context;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.luxlunaris.studybuddy.R;
import com.luxlunaris.studybuddy.view.filemanager.FileManagerActivity;

public class TextEditorToolbarMenuClickListener implements Toolbar.OnMenuItemClickListener{

    private Context context;

    public TextEditorToolbarMenuClickListener(Context context){
        this.context = context;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()){

//            case R.id.settingsItem:
//                Toast.makeText(context, "you clicked on settings...", Toast.LENGTH_SHORT).show();
//                break;
            case R.id.saveEditedTextItem:
                Toast.makeText(context, "Changes Saved! :-)", Toast.LENGTH_SHORT).show();
                break;

        }

        return false;
    }



}

