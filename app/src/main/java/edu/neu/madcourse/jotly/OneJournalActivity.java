package edu.neu.madcourse.jotly;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.neu.madcourse.jotly.addingJournal.Journal;
import edu.neu.madcourse.jotly.journalIndex.Entry;
import edu.neu.madcourse.jotly.journalIndex.EntryAdaptor;
import edu.neu.madcourse.jotly.journalIndex.FABEntryDialog;

public class OneJournalActivity extends AppCompatActivity implements FABEntryDialog.NoticeDialogListener {
        FloatingActionButton addingJournalFAB;
        RecyclerView linkListRecyclerView;
        List<Entry> entryList = new ArrayList<>();
        Journal currentJournal;
        TextView nameTV;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_journal);

        currentJournal = (Journal) getIntent().getSerializableExtra("journal");
        nameTV = findViewById(R.id.journalNmaeTV);
        nameTV.setText(currentJournal.getName());

        entryList = currentJournal.getEntryList();

        addingJournalFAB = findViewById(R.id.fabEntry);
        addingJournalFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEntry();
                }
                });

        linkListRecyclerView = findViewById(R.id.recyclerViewEntry);
        linkListRecyclerView.setHasFixedSize(true);
        linkListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        linkListRecyclerView.setAdapter(new EntryAdaptor(entryList, this));
        }

public void addEntry() {
        DialogFragment newFragment = new FABEntryDialog(this);
        newFragment.show(getSupportFragmentManager(), "Enter link");
        }

public void onDialogPositiveClick(DialogFragment dialog, String name, String content, String location) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        String date = timeStamp.substring(0,4) + "-" +timeStamp.substring(4,6) +"-" +timeStamp.substring(6,8);
        String time = timeStamp.substring(9,11) + ":" + timeStamp.substring(11,13);
        Entry addOneEntry = new Entry(date, time, name, content, location);
        if (name.isEmpty() || name == null) {
        Snackbar.make(linkListRecyclerView,"Neither name or URL can be empty",Snackbar.LENGTH_SHORT).show();
        } else {
            entryList.add(addOneEntry);
            linkListRecyclerView.getAdapter().notifyDataSetChanged();
            //TODO update database with new entry
            Snackbar.make(linkListRecyclerView,"A new journal created",Snackbar.LENGTH_SHORT).show();
        }
    }
}
