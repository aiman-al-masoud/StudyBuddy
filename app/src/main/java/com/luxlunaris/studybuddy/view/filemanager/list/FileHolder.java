package com.luxlunaris.studybuddy.view.filemanager.list;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

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
                String fileName = textView.getText().toString().replace(".txt", "");
                String text = FileManager.readTextFileFromRootDir(fileName);

                Intent i = new Intent(e.getContext(), TextEditorActivity.class);

                i.putExtra(TextEditorActivity.TEXT_INPUT, text);
                i.putExtra(TextEditorActivity.EDITED_FILE_NAME, fileName);

                ((Activity)e.getContext()).startActivityForResult(i, TextEditorActivity.TEXT_OUTPUT_RES_CODE);


            } catch (IOException ioException) {
                ioException.printStackTrace();
            }


        });

    }

}
