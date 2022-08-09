package edu.neu.madcourse.jotly;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.neu.madcourse.jotly.R;
import edu.neu.madcourse.jotly.addingJournal.Journal;
import edu.neu.madcourse.jotly.journalIndex.Entry;

public class OneEntryActivity extends AppCompatActivity {
    TextView titleTV, contentTV, timeTV, locationTV;
    Entry currentEntry;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_entry);
        //String content = "Test journal content";
        //Entry oneEntry = new Entry("2022-11-1", "12:23", "Test 1", content, "1233.22, 2123.22");
        currentEntry = (Entry) getIntent().getSerializableExtra("entry");
        titleTV = findViewById(R.id.titleTV);
        contentTV = findViewById(R.id.contentTV);
        timeTV = findViewById(R.id.timeTV);
        locationTV = findViewById(R.id.locationTV);

        titleTV.setText(currentEntry.getTitle());
        contentTV.setText(currentEntry.getContent());
        timeTV.setText(currentEntry.getDate()+ ' '+currentEntry.getTime());
        locationTV.setText(currentEntry.getLocation());
    }
}
