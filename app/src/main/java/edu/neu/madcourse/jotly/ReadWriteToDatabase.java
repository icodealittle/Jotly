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
    FirebaseDatabase jotlyDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference jotlyDatabaseReference;
    private final String TAG = "Firebase";


    public void initializeDatabaseReference() {
        jotlyDatabaseReference = jotlyDatabase.getReference();
    }

    //listener for writing/deleting a journal
    private void addJournalEventListener(DatabaseReference databaseReference) {
        ValueEventListener addEntryListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Journal journal = snapshot.getValue(Journal.class);
                //TODO use values to update UI
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to write journal", error.toException());
            }
        };
        databaseReference.addValueEventListener(addEntryListener);
    }

    // add/update entry - update journal
    public void writeJournal(int userID, Journal journal) {
        String key = jotlyDatabaseReference.child("journal").push().getKey();
        Map<String, Object> journalValues = journal.toMap();

        Map<String, Object> journalsUpdates = new HashMap<>();
        journalsUpdates.put("/journal" + key, journalValues);
        journalsUpdates.put("/user" + userID + "/journals/" + key, journalValues);

        try {
            jotlyDatabaseReference.updateChildren(journalsUpdates);
        } catch (DatabaseException e) {
            Log.d(TAG, "Failed to write journal with error: " + e);
        }
    }

    public void deleteJournal(Journal journal) {
        String path = journal.getName();
        try {
            jotlyDatabase.getReference(path).setValue(null);
        } catch (DatabaseException e) {
            Log.d(TAG, "Failed to delete journal with error: " + e);
        }
    }

    //listener for writing/deleting an entry
    private void writeEntryEventListener(DatabaseReference databaseReference) {
        ValueEventListener addEntryListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Entry entry = snapshot.getValue(Entry.class);
                //TODO use values to update UI
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to write entry", error.toException());
            }
        };
        databaseReference.addValueEventListener(addEntryListener);
    }

    // add/update entry - update journal
    public void writeEntry(int userID, Entry entry) {
        String key = jotlyDatabaseReference.child("entry").push().getKey();
        Map<String, Object> entryValues = entry.toMap();

        Map<String, Object> journalUpdates = new HashMap<>();
        journalUpdates.put("/entry" + key, entryValues);
        journalUpdates.put("/user" + userID + "/journal/" + key, entryValues);

        try {
            jotlyDatabaseReference.updateChildren(journalUpdates);
        } catch (DatabaseException e) {
            Log.d(TAG, "Failed to write journal entry with error: " + e);
        }
    }

    public void deleteEntry(Entry entry) {
        String path = entry.getTitle();
        try {
            jotlyDatabase.getReference(path).setValue(null);
        } catch (DatabaseException e) {
            Log.d(TAG, "Failed to delete journal entry with error: " + e);
        }
    }

}
