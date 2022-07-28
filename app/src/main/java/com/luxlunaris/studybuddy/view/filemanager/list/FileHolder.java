package com.luxlunaris.studybuddy.view.filemanager.list;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.luxlunaris.studybuddy.R;
import com.luxlunaris.studybuddy.model.utils.FileManager;
import com.luxlunaris.studybuddy.view.filemanager.editor.TextEditorActivity;

import java.io.IOException;


public class FileHolder extends RecyclerView.ViewHolder {

    public TextView textView;

    public FileHolder(@NonNull View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.fileHolderTextView);

        textView.setOnClickListener(e->{
            try {
                String text = FileManager.readTextFileFromRootDir(textView.getText().toString().replace(".txt", ""));

                Intent i = new Intent(e.getContext(), TextEditorActivity.class);
                i.putExtra(TextEditorActivity.TEXT_EXTRA, text);
                e.getContext().startActivity(i);

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }


        });

    }

}
