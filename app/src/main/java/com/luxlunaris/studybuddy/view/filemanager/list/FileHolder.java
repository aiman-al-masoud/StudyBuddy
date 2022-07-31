package com.luxlunaris.studybuddy.view.filemanager.list;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.luxlunaris.studybuddy.R;
import com.luxlunaris.studybuddy.model.utils.FileManager;
import com.luxlunaris.studybuddy.view.editor.TextEditorActivity;

import java.io.IOException;


public class FileHolder extends RecyclerView.ViewHolder {

    private int normalTextColor;
    private String fileName;
    private Button button;

    public FileHolder(@NonNull View itemView) {
        super(itemView);
        button =  itemView.findViewById(R.id.fileRowButton);
        normalTextColor = button.getCurrentTextColor();

        button.setOnClickListener(e->{
            try {
                String fileTitle = fileName.replace(".txt", "");
                String text = FileManager.readTextFileFromRootDir(fileTitle);
                Intent i = new Intent(e.getContext(), TextEditorActivity.class);
                i.putExtra(TextEditorActivity.TEXT_INPUT, text);
                i.putExtra(TextEditorActivity.EDITED_FILE_NAME, fileName);
                e.getContext().startActivity(i);

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }


        });

        button.setOnLongClickListener(e->{

            if(FileManager.getSelectedFiles().contains(fileName)){
                FileManager.unselectFile(fileName);
                button.setTextColor(normalTextColor);
            }else{
                FileManager.selectFile(fileName);
                button.setTextColor(Color.RED);
            }

            return true;
        });

    }


    public void setContent(String content){
        fileName = content;
        button.setText(content);
    }

}
