package edu.neu.madcourse.jotly;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LogOut extends AppCompatActivity {

    private Button logout;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_dash);

        logout = (Button) findViewById(R.id.logout);
        firebaseAuth = FirebaseAuth.getInstance();

        logout.setOnClickListener(view -> logout());
    }

    private void logout() {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(LogOut.this, MainActivity.class));
    }
}
