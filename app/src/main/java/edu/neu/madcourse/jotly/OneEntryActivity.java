package edu.neu.madcourse.jotly;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.neu.madcourse.jotly.R;
import edu.neu.madcourse.jotly.journalIndex.Entry;

public class OneEntryActivity extends AppCompatActivity {
    TextView titleTV, contentTV, locaTV;
    ImageView moodIV;
    Entry oneEntry;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_entry);


        oneEntry = (Entry)getIntent().getSerializableExtra("oneEntry");
        titleTV.setText(oneEntry.getTitle());
        contentTV.setText(oneEntry.getContent());
        locaTV.setText(oneEntry.getLocation());
        //moodIV.setImage
    }
}
