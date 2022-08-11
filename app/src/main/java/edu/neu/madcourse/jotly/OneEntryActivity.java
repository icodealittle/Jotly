package edu.neu.madcourse.jotly;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.neu.madcourse.jotly.R;
import edu.neu.madcourse.jotly.addingJournal.Journal;
import edu.neu.madcourse.jotly.journalIndex.Entry;

public class OneEntryActivity extends AppCompatActivity {
    TextView timeTV, locationTV;
    EditText contentET, titleEV;
    Entry currentEntry;
    Button backBtn, saveBtn;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_entry);
        //String content = "Test journal content";
        //Entry oneEntry = new Entry("2022-11-1", "12:23", "Test 1", content, "1233.22, 2123.22");
        currentEntry = (Entry) getIntent().getSerializableExtra("entry");
        titleEV = findViewById(R.id.titleTV);
        contentET = findViewById(R.id.contentTV);
        timeTV = findViewById(R.id.timeTV);
        locationTV = findViewById(R.id.locationTV);
        backBtn = findViewById(R.id.backBtn);
        saveBtn = findViewById(R.id.saveBtn);

        titleEV.setText(currentEntry.getTitle());
        contentET.setText(currentEntry.getContent());
        timeTV.setText(currentEntry.getDate()+ ' '+currentEntry.getTime());
        locationTV.setText(currentEntry.getLocation());
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OneEntryActivity.this,
                        OneJournalActivity.class));
            }
        });
    }
}
