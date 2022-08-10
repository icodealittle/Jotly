package edu.neu.madcourse.jotly.addingJournal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.neu.madcourse.jotly.MainActivity;
import edu.neu.madcourse.jotly.OneJournalActivity;
import edu.neu.madcourse.jotly.R;


public class JournalAdaptor extends RecyclerView.Adapter<JournalViewHolder>  {
    private final List<Journal> journalList;
    private final Context context;

    public JournalAdaptor(List<Journal> linkJournal, Context context) {
        this.journalList = linkJournal;
        this.context = context;
    }

    @NonNull
    @Override
    public JournalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new JournalViewHolder(LayoutInflater.from(context).inflate(R.layout.item_journal, null));
    }

    @Override
    public void onBindViewHolder(@NonNull JournalViewHolder holder, int position) {
        holder.bindThisData(journalList.get(position));
        holder.nameTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Click the name to open a journal in a new activity
                MainActivity activity = (MainActivity) context;
                Intent i = new Intent(activity, OneJournalActivity.class);
                activity.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return journalList.size();
    }
}
