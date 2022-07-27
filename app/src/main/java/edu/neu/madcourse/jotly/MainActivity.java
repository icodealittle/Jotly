package edu.neu.madcourse.jotly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private Firebase jotlyFirebase;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jotlyFirebase.initializeDatabaseReference(firebaseDatabase);
    }

    /*public void createJournal(String title) {
        Journal journal = new Journal(title);
        user.getJournals().add(journal);
    } */

    public void createEntry(){ //for button + textview entry
        //jotlyFirebase.createEntry();
    }



}