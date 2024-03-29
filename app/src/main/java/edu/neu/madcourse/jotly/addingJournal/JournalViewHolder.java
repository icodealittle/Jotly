package edu.neu.madcourse.jotly.addingJournal;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.neu.madcourse.jotly.R;

/**
 * This class is used to set up view for each journal instance
 */
public class JournalViewHolder extends RecyclerView.ViewHolder {
    public TextView nameTV;
    public RelativeLayout journalRL;

    public JournalViewHolder(@NonNull View itemView) {
        super(itemView);
        this.nameTV = itemView.findViewById(R.id.journalNameItem);
        this.journalRL = itemView.findViewById(R.id.item_link);
    }

    public void bindThisData(Journal theJournalToBind) {
        // sets the name of the person to the name textview of the viewholder.
        nameTV.setText(theJournalToBind.getName());
    }
}
