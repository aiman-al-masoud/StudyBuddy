package com.luxlunaris.studybuddy.view.filemanager.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luxlunaris.studybuddy.R;

public class FileList extends RecyclerView.Adapter<FileHolder> {

    Context context;
    private final LayoutInflater inflater;


    public FileList(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public FileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = inflater.inflate(R.layout.file_holder_row, parent, false);
        return new FileHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull FileHolder holder, int position) {
        holder.textView.setText("file"+position);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

}
