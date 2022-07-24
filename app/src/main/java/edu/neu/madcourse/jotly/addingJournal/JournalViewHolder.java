package edu.neu.madcourse.jotly.addingJournal;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.neu.madcourse.jotly.R;

public class JournalViewHolder extends RecyclerView.ViewHolder {
    public TextView nameTV;

    public JournalViewHolder(@NonNull View itemView) {
        super(itemView);
        this.nameTV = itemView.findViewById(R.id.journalNameItem);
    }

    public void bindThisData(Journal theJournalToBind) {
        // sets the name of the person to the name textview of the viewholder.
        nameTV.setText(theJournalToBind.getName());
    }
}
