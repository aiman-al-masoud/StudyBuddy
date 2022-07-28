package com.luxlunaris.studybuddy.view.filemanager.list;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luxlunaris.studybuddy.R;
import com.luxlunaris.studybuddy.model.utils.FileManager;
import com.luxlunaris.studybuddy.model.utils.FileManagerListener;

import java.io.IOException;
import java.util.List;

public class FileList extends RecyclerView.Adapter<FileHolder> implements FileManagerListener {

    private Context context;
    private final LayoutInflater inflater;

    private List<String> fileNames;

    public FileList(Context context){
        this.context = context;

        inflater = LayoutInflater.from(context);

        try {
            fileNames = FileManager.lsRootDir();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileManager.addListener(this);

    }

    @NonNull
    @Override
    public FileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = inflater.inflate(R.layout.file_holder_row, parent, false);
        return new FileHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull FileHolder holder, int position) {
        holder.textView.setText(positionToName(position));
    }

    @Override
    public int getItemCount() {
        return fileNames.size();
    }


    private String positionToName(int pos){
        return fileNames.get(pos);
    }


    @Override
    public void onFileChanged(String title, String newBody) {
        Log.d("FileList", "onFileChanged: "+title);

        if(fileNames.contains(title+".txt")){
            return;
        }

        fileNames.add(title+".txt");
        notifyItemInserted(fileNames.size()-1);


    }


}
