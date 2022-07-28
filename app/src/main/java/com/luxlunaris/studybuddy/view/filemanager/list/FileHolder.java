package com.luxlunaris.studybuddy.view.filemanager.list;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.luxlunaris.studybuddy.R;



public class FileHolder extends RecyclerView.ViewHolder {

    public TextView textView;

    public FileHolder(@NonNull View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.fileHolderTextView);

        textView.setOnClickListener(e->{
            Toast.makeText(e.getContext(), "you clicked on: "+textView.getText(), Toast.LENGTH_SHORT).show();
        });

    }

}
