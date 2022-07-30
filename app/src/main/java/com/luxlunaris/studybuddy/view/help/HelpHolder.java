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

import org.w3c.dom.Text;

public class HelpHolder extends RecyclerView.ViewHolder {

    public TextView textView;

    public HelpHolder(@NonNull View itemView) {
        super(itemView);
        textView = (TextView) itemView;
    }

    public void setContent(String content){
        textView.setText(content);
    }



}
