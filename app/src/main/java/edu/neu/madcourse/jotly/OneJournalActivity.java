package edu.neu.madcourse.jotly;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.utilities.Tree;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import edu.neu.madcourse.jotly.addingJournal.Journal;
import edu.neu.madcourse.jotly.addingJournal.JournalAdaptor;
import edu.neu.madcourse.jotly.journalIndex.Entry;
import edu.neu.madcourse.jotly.journalIndex.EntryAdaptor;
import edu.neu.madcourse.jotly.journalIndex.FABEntryDialog;

public class OneJournalActivity extends AppCompatActivity implements FABEntryDialog.NoticeDialogListener {
        FloatingActionButton addingJournalFAB;
        RecyclerView entryListRecyclerView;
        Map<String, Entry> entryList = new TreeMap<>();
        Journal currentJournal;
        EntryAdaptor entryAdaptor;
        String jKey;
        TextView nameTV;
        private DatabaseReference firebase;
        private FirebaseAuth firebaseAuth;
        Button backToHomeBtn;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_journal);
        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
        currentJournal = (Journal) getIntent().getSerializableExtra("journal");
        jKey = (String) getIntent().getSerializableExtra("jKey");

    firebase = FirebaseDatabase.getInstance().getReference().child("User")
            .child(user.getUid()).child("journals").child(jKey).child("EntryList");

    firebase.addChildEventListener(
            new ChildEventListener() {

                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            }
    );

    initEntryList();

    nameTV = findViewById(R.id.journalNmaeTV);
        nameTV.setText(currentJournal.getName());

    addingJournalFAB = findViewById(R.id.fabEntry);
        addingJournalFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEntry();
                }
        });

    backToHomeBtn = findViewById(R.id.backToHomeBtn);
    backToHomeBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(OneJournalActivity.this, HomePageActivity.class);
            startActivity(i);
        }
    });

    entryListRecyclerView = findViewById(R.id.recyclerViewEntry);
    entryListRecyclerView.setHasFixedSize(true);
    entryListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    entryListRecyclerView.setAdapter(new EntryAdaptor(entryList, jKey, currentJournal, this));
        }

public void addEntry() {
        DialogFragment newFragment = new FABEntryDialog(this);
        newFragment.show(getSupportFragmentManager(), "Enter link");
        }

public void onDialogPositiveClick(DialogFragment dialog, String name, String content, String location) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        String date = timeStamp.substring(0,4) + "-" +timeStamp.substring(4,6) +"-" +timeStamp.substring(6,8);
        String time = timeStamp.substring(9,11) + ":" + timeStamp.substring(11,13);
        if (name == null ||name.isEmpty()) {
            Snackbar.make(entryListRecyclerView,"Please do not leave the name empty",
                    Snackbar.LENGTH_SHORT).show();
        } else {
            Entry addOneEntry = new Entry(date, time, name, content, location);
            DatabaseReference firebaseForNewE = firebase.push();
            Task t1 = firebaseForNewE.setValue(addOneEntry).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), ("Unable to save the journal."), Toast.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(entryListRecyclerView,"A new entry created",Snackbar.LENGTH_SHORT).show();
                        entryList.put(firebaseForNewE.getKey(), addOneEntry);
                        entryListRecyclerView.getAdapter().notifyDataSetChanged();
                    }
                }
            });

            Snackbar.make(entryListRecyclerView,"A new entry created",Snackbar.LENGTH_SHORT).show();
        }
    }

    public void initEntryList() {
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getChildren().forEach(child->{
                    entryList.put(child.getKey(), child.getValue(Entry.class));
                });

                entryAdaptor = new EntryAdaptor(entryList, jKey, currentJournal, entryListRecyclerView.getContext());
                entryListRecyclerView.setAdapter(entryAdaptor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
