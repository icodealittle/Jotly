package edu.neu.madcourse.jotly.journalIndex;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import edu.neu.madcourse.jotly.HomePageActivity;
import edu.neu.madcourse.jotly.OneEntryActivity;
import edu.neu.madcourse.jotly.OneJournalActivity;
import edu.neu.madcourse.jotly.R;
import edu.neu.madcourse.jotly.addingJournal.Journal;
import edu.neu.madcourse.jotly.addingJournal.JournalViewHolder;

public class EntryAdaptor extends RecyclerView.Adapter<EntryViewHolder> {

    private Map<String, Entry> entryList = new TreeMap<>();
    private final Context context;

    public EntryAdaptor(Map<String, Entry> entryList, Context context) {
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
        Map.Entry<String, Entry> keyEntryPair = (Map.Entry<String, Entry>)entryList.entrySet().toArray()[position];
        holder.bindThisData(keyEntryPair.getValue());
        holder.nameTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OneJournalActivity activity = (OneJournalActivity) context;
                Intent i = new Intent(activity, OneEntryActivity.class);
                i.putExtra("entry", keyEntryPair.getValue());
                i.putExtra("eKey", keyEntryPair.getKey());
                activity.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return entryList.size();
    }
}
