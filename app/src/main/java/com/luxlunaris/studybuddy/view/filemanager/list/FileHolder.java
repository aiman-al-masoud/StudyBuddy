package com.luxlunaris.studybuddy.view.filemanager.list;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.luxlunaris.studybuddy.R;
import com.luxlunaris.studybuddy.model.utils.FileManager;
import com.luxlunaris.studybuddy.view.editor.TextEditorActivity;

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

                e.getContext().startActivity(i);

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }


        });

        textView.setOnLongClickListener(e->{
            String fileName = textView.getText().toString();

            if(FileManager.getSelectedFiles().contains(fileName)){
                FileManager.unselectFile(fileName);
                textView.setTextColor(Color.WHITE);
            }else{
                FileManager.selectFile(fileName);
                textView.setTextColor(Color.RED);
            }

            return true;
        });

    }

}
