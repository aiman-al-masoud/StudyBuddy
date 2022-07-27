package com.luxlunaris.studybuddy.view.chatui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luxlunaris.studybuddy.R;

import java.util.ArrayList;
import java.util.List;


public class RowAdapter extends RecyclerView.Adapter<RowHolder> {

    private final int OUTGOING = 0;
    private final int INCOMING = 1;


    private final Context context;
    private final LayoutInflater inflater;
    private final List<String> items;

    public RowAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        items = new ArrayList<String>();
    }


    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View row;

        if(viewType==OUTGOING){
            row = inflater.inflate(R.layout.row_outgoing_bubble, parent, false);
        }else{
            row = inflater.inflate(R.layout.row_incoming_bubble, parent, false);
        }

        return new RowHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {
        // Bind the data to be displayed to the new row.
        holder.rowText.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void addRow(String text) {
        items.add(text);
        notifyItemInserted(getItemCount() - 1);
    }

//    public void addOutgoingBubbleRow(String text) {
//        items.add(text);
//        notifyItemInserted(getItemCount() - 1);
//    }

    /**
     * Assumptions:
     * 1. strict alternation of messages.
     * 2. Initial message by user (outgoing).
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return position%2==0 ? OUTGOING : INCOMING;
    }




}
