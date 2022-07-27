package com.luxlunaris.studybuddy.view.chatui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luxlunaris.studybuddy.R;


public class RowAdapter extends RecyclerView.Adapter<RowHolder> {

    private final Context context;
    private final LayoutInflater inflater;

    public RowAdapter(Context context){
        this.context =  context;
        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = inflater.inflate(R.layout.row, parent, false);
        return new RowHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {
        // Bind the data to be displayed to the new row.
        //
        //
    }

    @Override
    public int getItemCount() {
        return 0;
    }




}
