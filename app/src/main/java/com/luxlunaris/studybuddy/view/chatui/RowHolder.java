package com.luxlunaris.studybuddy.view.chatui;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luxlunaris.studybuddy.R;

public class RowHolder extends RecyclerView.ViewHolder {

    public TextView rowText;

    public RowHolder(@NonNull View itemView) {
        super(itemView);
        rowText = itemView.findViewById(R.id.rowText);


        itemView.setOnLongClickListener(e->{
            ClipboardManager clipboard = (ClipboardManager) itemView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("", rowText.getText());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(e.getContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
            return true;
        });


    }

}
