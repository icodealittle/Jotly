package edu.neu.madcourse.jotly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.jotly.addingJournal.FABDialog;
import edu.neu.madcourse.jotly.addingJournal.Journal;
import edu.neu.madcourse.jotly.addingJournal.JournalAdaptor;

public class MainActivity extends AppCompatActivity
        implements FABDialog.NoticeDialogListener {
    FloatingActionButton addingJournalFAB;
    RecyclerView linkListRecyclerView;
    List<Journal> journalList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addingJournalFAB = findViewById(R.id.floatingActionButton);
        addingJournalFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addJournal();
            }
        });

        linkListRecyclerView = findViewById(R.id.recycleView);
        linkListRecyclerView.setHasFixedSize(true);
        linkListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        linkListRecyclerView.setAdapter(new JournalAdaptor(journalList, this));
    }

    public void addJournal() {
        DialogFragment newFragment = new FABDialog();
        newFragment.show(getSupportFragmentManager(), "Enter link");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String name) {
        Journal addOneJournal = new Journal(name);
        if (name.isEmpty() || name == null) {
            Snackbar.make(linkListRecyclerView,"Neither name or URL can be empty",Snackbar.LENGTH_SHORT).show();
        } else {
            journalList.add(addOneJournal);
            Snackbar.make(linkListRecyclerView,"A new journal created",Snackbar.LENGTH_SHORT).show();
        }
    }
}