package com.luxlunaris.studybuddy.view.help;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luxlunaris.studybuddy.R;
import com.luxlunaris.studybuddy.model.utils.Help;


public class HelpAdapter extends RecyclerView.Adapter<HelpHolder> {

    private final Context context;
    private final LayoutInflater inflater;

    public HelpAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public HelpHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = inflater.inflate(R.layout.row_help_holder, parent, false);
        return new HelpHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HelpHolder holder, int position) {
        // Bind the data to be displayed to the new row.
        holder.setContent(Help.getDocumentations(context).get(position));
    }

    @Override
    public int getItemCount() {
        return Help.numberOfCommands();
    }







}
