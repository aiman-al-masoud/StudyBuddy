package com.luxlunaris.studybuddy.view.editor;

import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.luxlunaris.studybuddy.R;

public class TextEditorToolbarMenuClickListener implements Toolbar.OnMenuItemClickListener{

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
            case R.id.undoItem:
                textEditorActivity.undo();
                break;

        }

        return false;
    }



}

