package com.luxlunaris.studybuddy.view.chatui;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luxlunaris.studybuddy.R;

public class RowHolder extends RecyclerView.ViewHolder {

    public TextView rowText;

    public RowHolder(@NonNull View itemView) {
        super(itemView);
        rowText = itemView.findViewById(R.id.rowText);
    }

}
