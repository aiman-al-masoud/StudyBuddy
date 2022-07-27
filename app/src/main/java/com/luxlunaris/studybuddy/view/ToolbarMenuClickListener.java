package com.luxlunaris.studybuddy.view;

import android.content.Context;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

public class ToolbarMenuClickListener implements Toolbar.OnMenuItemClickListener {

    Context context;

    public ToolbarMenuClickListener(Context context){
        this.context = context;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()){


        }

        return false;
    }



}
