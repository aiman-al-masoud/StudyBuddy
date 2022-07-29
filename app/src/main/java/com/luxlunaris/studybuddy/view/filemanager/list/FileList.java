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

    public FileList(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
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

        try {
            return FileManager.lsRootDir().size();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }


    private String positionToName(int pos){

        try {
            List<String> ls = FileManager.lsRootDir();
            Log.d("FileList", "positionToName: "+ls);
            return ls.get(pos);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void onFileChanged(String title, String newBody) {
        Log.d("FileList", "onFileChanged: "+title);
        notifyItemInserted(getItemCount());
    }

    @Override
    public void onFileDeleted(String title, int oldPosition) {
        Log.d("FileList", "onFileDeleted: "+title+" "+oldPosition);


        notifyItemRemoved(oldPosition);
        notifyItemRangeChanged(oldPosition, getItemCount()+1);
    }

    @Override
    public void onFileSelected(String title) {

    }

    @Override
    public void onFileDeSelected(String title) {

    }


}
