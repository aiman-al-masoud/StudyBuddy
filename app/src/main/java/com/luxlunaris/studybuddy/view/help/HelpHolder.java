package com.luxlunaris.studybuddy.view.help;



import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luxlunaris.studybuddy.R;
import com.luxlunaris.studybuddy.model.Help;


public class HelpHolder extends RecyclerView.ViewHolder {


    private TextView titleView;
    private TextView descriptionView;
    private TextView syntaxView;

    public HelpHolder(@NonNull View itemView) {
        super(itemView);
        titleView = itemView.findViewById(R.id.commandTitleTextView);
        descriptionView = itemView.findViewById(R.id.commandDescriptionTextView);
        syntaxView = itemView.findViewById(R.id.commandSyntaxTextView);

    }

    public void setContent(Help content){
        titleView.setText(content.name);
        descriptionView.setText(content.description);
        syntaxView.setText(content.syntax);
    }



}
