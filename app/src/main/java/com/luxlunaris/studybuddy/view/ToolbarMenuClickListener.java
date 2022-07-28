package com.luxlunaris.studybuddy.view;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.luxlunaris.studybuddy.R;
import com.luxlunaris.studybuddy.view.filemanager.FileManagerActivity;

public class ToolbarMenuClickListener implements Toolbar.OnMenuItemClickListener {

    Context context;

    public ToolbarMenuClickListener(Context context){
        this.context = context;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()){

//            case R.id.settingsItem:
//                Toast.makeText(context, "you clicked on settings...", Toast.LENGTH_SHORT).show();
//                break;
            case R.id.fileManagerItem:
                Intent i = new Intent(context, FileManagerActivity.class);
                context.startActivity(i);
                break;

        }

        return false;
    }



}
