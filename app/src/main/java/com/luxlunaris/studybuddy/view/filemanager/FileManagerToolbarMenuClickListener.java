package com.luxlunaris.studybuddy.view.filemanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import com.luxlunaris.studybuddy.R;
import com.luxlunaris.studybuddy.model.utils.FileManager;
import com.luxlunaris.studybuddy.model.utils.FileManagerListener;

public class FileManagerToolbarMenuClickListener implements Toolbar.OnMenuItemClickListener, FileManagerListener {

    Menu menu;

    public FileManagerToolbarMenuClickListener(Menu menu){
        this.menu = menu;
        FileManager.addListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()){

            case R.id.deleteItem:
                FileManager.deleteAll(FileManager.getSelectedFiles());
                break;
        }

        return false;
    }


    @Override
    public void onFileChanged(String title, String newBody) {

    }

    @Override
    public void onFileDeleted(String title, int oldPosition) {

    }

    @Override
    public void onFileSelected(String title) {
        menu.findItem(R.id.deleteItem).setVisible(true);
    }

    @Override
    public void onFileDeSelected(String title) {
        Log.d("FileManagerToolbarMenuClLis", "onFileDeSelected: "+FileManager.getSelectedFiles().size());
        if(FileManager.getSelectedFiles().size()==0){
            menu.findItem(R.id.deleteItem).setVisible(false);
        }
    }


}
