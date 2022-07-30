package com.luxlunaris.studybuddy.view.help;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luxlunaris.studybuddy.R;
import com.luxlunaris.studybuddy.model.Help;

import java.util.ArrayList;
import java.util.List;


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

        TextView row = new TextView(parent.getContext());
        row.setText(Help.getDocumentations().get(viewType));

        return new HelpHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull HelpHolder holder, int position) {
        // Bind the data to be displayed to the new row.
//        holder.rowText.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
//        return items.size();
        return Help.numberOfCommands();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }



}
