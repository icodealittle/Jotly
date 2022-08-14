package edu.neu.madcourse.jotly;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

import java.util.Map;
import java.util.TreeMap;

import edu.neu.madcourse.jotly.addingJournal.FABDialog;
import edu.neu.madcourse.jotly.addingJournal.Journal;
import edu.neu.madcourse.jotly.addingJournal.JournalAdaptor;

public class HomePageActivity extends AppCompatActivity
        implements FABDialog.NoticeDialogListener {
    FloatingActionButton addingJournalFAB;
    RecyclerView journalListRecyclerView;
    JournalAdaptor journalAdaptor;
    Map<String, Journal> journalList = new TreeMap<String, Journal>();
    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebase;
    TextView helloTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_activity);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        helloTV = findViewById(R.id.helloTV);

        firebase = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid());
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    helloTV.setText("Hello, " + userProfile.userName + "!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

            helloTV = findViewById(R.id.helloTV);
        helloTV.setText("Hello, " + user.getDisplayName());

        // make a journal if not exist
        firebase = firebase.child("journals");

       // TODO read the journalList of the log-in user from database
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

        initJournalList();

        addingJournalFAB = findViewById(R.id.floatingActionButton);
        addingJournalFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addJournal();
            }
        });

        journalListRecyclerView = findViewById(R.id.recycleView);
        journalListRecyclerView.setHasFixedSize(true);
        journalListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        journalAdaptor = new JournalAdaptor(journalList, this);
        journalListRecyclerView.setAdapter(journalAdaptor);
    }

    public void addJournal() {
        DialogFragment newFragment = new FABDialog();
        newFragment.show(getSupportFragmentManager(), "Enter link");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String name) {
        Journal addOneJournal = new Journal(name);
        if (name.isEmpty() || name == null) {
            Snackbar.make(journalListRecyclerView, "Neither name or URL can be empty",
                    Snackbar.LENGTH_SHORT).show();
        } else {
            DatabaseReference firebaseForNewJ = firebase.push();
            Task t1 = firebaseForNewJ.setValue(addOneJournal).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), ("Unable to save the journal."),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(journalListRecyclerView, "A new journal created",
                                Snackbar.LENGTH_SHORT).show();
                        journalList.put(firebaseForNewJ.getKey(), addOneJournal);
                        journalListRecyclerView.getAdapter().notifyDataSetChanged();
                    }
                }
            });
        }
    }

    public void initJournalList() {
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getChildren().forEach(child -> {
                    journalList.put(child.getKey(), child.getValue(Journal.class));
                });

                journalAdaptor = new JournalAdaptor(journalList,
                        journalListRecyclerView.getContext());
                journalListRecyclerView.setAdapter(journalAdaptor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.userProfile) {
            Intent intent = new Intent(HomePageActivity.this, UserProfile.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.logouyBtn) {
            userSignOut();
            return true;
        } else if (id == R.id.homepageBtn) {
            Intent intent = new Intent(HomePageActivity.this, HomePageActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Logout Function
    private void userSignOut() {
        firebaseAuth.signOut();
        Intent intent = new Intent(HomePageActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(HomePageActivity.this, "Sign-out Successful",
                Toast.LENGTH_SHORT).show();
    }
}