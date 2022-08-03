package edu.neu.madcourse.jotly;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.neu.madcourse.jotly.addingJournal.Journal;
import edu.neu.madcourse.jotly.journalIndex.Entry;

public class ReadWriteToDatabase {
    private User user;
    private Journal journal;
    private Entry entry;
    private DatabaseReference jotlyDatabase;
    private final String TAG = "Firebase";


    public void initializeDatabaseReference(DatabaseReference database) {
        jotlyDatabase = database;
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
    public void createEntry(String title, String date, String time) {
        Entry entry = new Entry(title, date, time);
        addEntry(user.getUserId(), entry.toMap());
        List<Entry> entries = journal.getEntryList();
        entries.add(entry);
    }

    //update entry
    public void updateEntry(String title, String date, String time, String content) {
        Entry entry = new Entry(title, date, time, content);
        addEntry(user.getUserId(), entry.toMap());
        List<Entry> entries = journal.getEntryList();
        entries.add(entry);
    }

    // add entry - update journal
    public void addEntry(int userId, Map<String, Object> entry) {
        String key = jotlyDatabase.child("entry").push().getKey();
        Map<String, Object> journalsUpdates = new HashMap<>();
        journalsUpdates.put("/entry" + key, entry);
        journalsUpdates.put("/user" + userId + "/journal/" + key, entry);

        try {
            jotlyDatabase.updateChildren(journalsUpdates);
        } catch (DatabaseException e) {
            Log.d(TAG, "Failed to add new journal with error: " + e);
        }
    }

}
