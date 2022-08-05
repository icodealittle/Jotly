package edu.neu.madcourse.jotly.journalIndex;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.neu.madcourse.jotly.HomePageActivity;
import edu.neu.madcourse.jotly.OneJournalActivity;
import edu.neu.madcourse.jotly.R;
import edu.neu.madcourse.jotly.addingJournal.Journal;
import edu.neu.madcourse.jotly.addingJournal.JournalViewHolder;

public class EntryAdaptor extends RecyclerView.Adapter<EntryViewHolder> {

    private final List<Entry> entryList;
    private final Context context;

    public EntryAdaptor(List<Entry> entryList, Context context) {
        this.entryList = entryList;
        this.context = context;
    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EntryViewHolder(LayoutInflater.from(context).inflate(R.layout.item_entry, null));
    }

    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
        holder.bindThisData(entryList.get(position));
        holder.nameTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Click the name to open a entry in a new activity

            }
        });
    }

    @Override
    public int getItemCount() {
        return entryList.size();
    }
}
