package edu.neu.madcourse.jotly;

import android.os.Bundle;
import android.view.View;

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

import edu.neu.madcourse.jotly.journalIndex.Entry;
import edu.neu.madcourse.jotly.journalIndex.EntryAdaptor;
import edu.neu.madcourse.jotly.journalIndex.FABEntryDialog;

public class OneJournalActivity extends AppCompatActivity implements FABEntryDialog.NoticeDialogListener {
        FloatingActionButton addingJournalFAB;
        RecyclerView linkListRecyclerView;
        List<Entry> entryList = new ArrayList<>();

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_journal);

        addingJournalFAB = findViewById(R.id.fabEntry);
        addingJournalFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addJournal();
                }
                });

                linkListRecyclerView = findViewById(R.id.recyclerViewEntry);
                linkListRecyclerView.setHasFixedSize(true);
                linkListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                linkListRecyclerView.setAdapter(new EntryAdaptor(entryList, this));
        }

public void addJournal() {
        DialogFragment newFragment = new FABEntryDialog();
        newFragment.show(getSupportFragmentManager(), "Enter link");
        }

        // TODO This Dialog is used as an example, It can be replaced by a entry activity page
@Override
public void onDialogPositiveClick(DialogFragment dialog, String name) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        String date = timeStamp.substring(0,7);
        String time = timeStamp.substring(9);
        Entry addOneJournal = new Entry(date, time, name);
        if (name.isEmpty() || name == null) {
        Snackbar.make(linkListRecyclerView,"Neither name or URL can be empty",Snackbar.LENGTH_SHORT).show();
        } else {
            entryList.add(addOneJournal);
            //TODO update database with new entry
        Snackbar.make(linkListRecyclerView,"A new journal created",Snackbar.LENGTH_SHORT).show();
        }
        }
}
