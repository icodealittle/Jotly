package edu.neu.madcourse.jotly.addingJournal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

import edu.neu.madcourse.jotly.HomePageActivity;
import edu.neu.madcourse.jotly.OneJournalActivity;
import edu.neu.madcourse.jotly.R;

/**
 * This class is used to set each journal instance into the recycle view
 */
public class JournalAdaptor extends RecyclerView.Adapter<JournalViewHolder> {

    private final Map<String, Journal> journalList;
    private final Context context;

    public JournalAdaptor(Map<String, Journal> linkJournal, Context context) {
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
        Map.Entry<String, Journal> keyJourPair = (Map.Entry<String, Journal>)journalList.entrySet().toArray()[position];
        holder.bindThisData(keyJourPair.getValue());
        holder.journalRL.setBackgroundResource(R.drawable.round_rec);
        if (position%2 == 0) {
            holder.journalRL.setBackgroundColor(Color.parseColor("#806C464F"));
        }
        else
        {
            holder.journalRL.setBackgroundColor(Color.parseColor("#F9EBE0"));
            holder.nameTV.setTextColor(Color.parseColor("#000000"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Click the name to open a journal in a new activity
                HomePageActivity activity = (HomePageActivity) context;
                Intent i = new Intent(activity, OneJournalActivity.class);
                i.putExtra("journal", keyJourPair.getValue());
                i.putExtra("jKey", keyJourPair.getKey());
                activity.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return journalList.size();
    }
}
