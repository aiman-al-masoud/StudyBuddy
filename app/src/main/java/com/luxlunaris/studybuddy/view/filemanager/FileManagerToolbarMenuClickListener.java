package com.luxlunaris.studybuddy.view.filemanager;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import com.luxlunaris.studybuddy.R;

public class FileManagerToolbarMenuClickListener implements Toolbar.OnMenuItemClickListener{

    Context context;

    public FileManagerToolbarMenuClickListener(Context context){
        this.context = context;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()){



        }

        return false;
    }



}
