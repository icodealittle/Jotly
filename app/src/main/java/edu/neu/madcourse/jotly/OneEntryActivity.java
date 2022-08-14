package edu.neu.madcourse.jotly;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.neu.madcourse.jotly.R;
import edu.neu.madcourse.jotly.addingJournal.Journal;
import edu.neu.madcourse.jotly.journalIndex.Entry;

public class OneEntryActivity extends AppCompatActivity {
    TextView timeTV, locationTV;
    EditText contentET, titleEV;
    Entry currentEntry;
    Journal currentJournal;
    String eKey, jKey;
    Button backBtn, saveBtn;
    String title, content;
    private DatabaseReference firebase;
    private FirebaseAuth firebaseAuth;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_entry);
        currentEntry = (Entry) getIntent().getSerializableExtra("entry");
        eKey = (String) getIntent().getSerializableExtra("eKey");
        currentJournal = (Journal) getIntent().getSerializableExtra("journal");
        jKey = (String) getIntent().getSerializableExtra("jKey");
        titleEV = findViewById(R.id.titleTV);
        contentET = findViewById(R.id.contentTV);
        timeTV = findViewById(R.id.timeTV);
        locationTV = findViewById(R.id.locationTV);
        backBtn = findViewById(R.id.backBtn);
        saveBtn = findViewById(R.id.saveBtn);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
        firebase = FirebaseDatabase.getInstance().getReference().child("User")
                .child(user.getUid()).child("journals").child(jKey).child("EntryList").child(eKey);

        titleEV.setText(currentEntry.getTitle());
        contentET.setText(currentEntry.getContent());
        timeTV.setText(currentEntry.getDate()+ ' '+currentEntry.getTime());
        locationTV.setText(currentEntry.getLocation());
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OneEntryActivity.this,
                        OneJournalActivity.class);
                i.putExtra("journal", currentJournal);
                i.putExtra("jKey", jKey);
                startActivity(i);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = titleEV.getText().toString();
                content = contentET.getText().toString();

                currentEntry.changeTitle(title);
                currentEntry.changeContent(content);
                if (title.isEmpty() || title == null) {
                    Snackbar.make(v, "The Entry name cannot be empty",
                            Snackbar.LENGTH_SHORT).show();
                } else {
                    firebase.setValue(currentEntry);
                    Snackbar.make(v,
                            "Saved all the changes",
                            Snackbar.LENGTH_SHORT).show();
                }

            }
        });
    }
}
