package edu.neu.madcourse.jotly.journalIndex;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.neu.madcourse.jotly.R;
import edu.neu.madcourse.jotly.addingJournal.Journal;

public class EntryViewHolder extends RecyclerView.ViewHolder{
    public TextView nameTV;
    public RelativeLayout entryRL;

    public EntryViewHolder(@NonNull View itemView) {
        super(itemView);
        this.nameTV = itemView.findViewById(R.id.entryNameItem);
        this.entryRL = itemView.findViewById(R.id.entry_link);
    }

    public void bindThisData(Entry theEntryToBind) {
        // sets the name of the person to the name textview of the viewholder.
        nameTV.setText(theEntryToBind.getTitle());
    }
}
