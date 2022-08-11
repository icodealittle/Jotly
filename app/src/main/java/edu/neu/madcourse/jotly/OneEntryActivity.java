package edu.neu.madcourse.jotly;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import edu.neu.madcourse.jotly.R;
import edu.neu.madcourse.jotly.addingJournal.Journal;
import edu.neu.madcourse.jotly.journalIndex.Entry;

public class OneEntryActivity extends AppCompatActivity {
    TextView timeTV, locationTV;
    EditText contentET, titleEV;
    Entry currentEntry;
    Button backBtn, saveBtn;
    String title, content;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_entry);
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

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = titleEV.getText().toString();
                content = contentET.getText().toString();

                currentEntry.changeTitle(title);
                currentEntry.changeContent(content);
                // TODO update the current entry
                Snackbar.make(v,
                        "Saved all the changes",
                        Snackbar.LENGTH_SHORT).show();

            }
        });
    }
}
