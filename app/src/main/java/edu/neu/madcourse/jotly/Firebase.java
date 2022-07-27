/*
*  Firebase Storage Activity
*  Initializes, updates and maintains the database.
*  source: https://firebase.google.com/docs/storage/android/start?utm_campaign=Firebase_featureoverview_education_storage_en_10-31-16&utm_source=Firebase&utm_medium=yt-desc
* */
package edu.neu.madcourse.jotly;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Firebase {
    protected DatabaseReference jotlyDatabase;
    private User user;
    private Journal journal;
    //Tag for logging
    private final String TAG = "Firebase";

    // initialize database
    public void initializeDatabaseReference(FirebaseDatabase firebaseDatabase) {
        jotlyDatabase = firebaseDatabase.getReference();
    }

    private void addJournalEventListener(DatabaseReference databaseReference) {
        ValueEventListener addJournalListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Journal journal = snapshot.getValue(Journal.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to add Journal", error.toException());
            }
        };
        databaseReference.addValueEventListener(addJournalListener);
    }

    //create new journal
    //private void createJournal(){}

    // add journal - update journals
    public void addJournal(Journal journal) {
        String key = jotlyDatabase.child("journals").push().getKey();
        List<Journal> journals = user.getJournals();
        journals.add(journal);

        Map<String, Object> journalsUpdates = new HashMap<>();
        journalsUpdates.put("/journals" + key, journals);
        journalsUpdates.put("/user" + user.getUsername() + "/" + key, journal);

        jotlyDatabase.updateChildren(journalsUpdates);
    }

    private void addEntryEventListener(DatabaseReference databaseReference) {
        ValueEventListener addEntryListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Entry entry = snapshot.getValue(Entry.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to add Entry", error.toException());
            }
        };
        databaseReference.addValueEventListener(addEntryListener);
    }

    //create new entry
    public void createEntry(String title, String content, Date created) {
        Entry entry = new Entry(title, content, created);
        addEntry(entry.toMap());
        List<Entry> entries = journal.getEntries();
        entries.add(entry);
    }

    //update entry

    // add entry - update journal
    public void addEntry(Map<String, Object> entry) {
        String key = jotlyDatabase.child("entry").push().getKey();
        Map<String, Object> journalsUpdates = new HashMap<>();
        journalsUpdates.put("/entry" + key, entry);
        journalsUpdates.put("/user" + user.getUsername() + "/journal/" + key, entry);

        try {
            jotlyDatabase.updateChildren(journalsUpdates);
        } catch (DatabaseException e) {
            Log.d(TAG, "Failed to add new journal with error: " + e);
        }
    }


}

