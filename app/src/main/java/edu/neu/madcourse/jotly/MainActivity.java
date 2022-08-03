package edu.neu.madcourse.jotly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.neu.madcourse.jotly.addingJournal.Journal;
import edu.neu.madcourse.jotly.journalIndex.Entry;

public class MainActivity extends AppCompatActivity {
    private TextView createAcct;
    private TextView resetPass;
    private Button userLogin;
    private EditText userEmail, userPassword;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createAcct = findViewById(R.id.createAccount);
        createAcct.setOnClickListener(this);
        userLogin = findViewById(R.id.loginBtn);
        userLogin.setOnClickListener(this);
        resetPass = findViewById(R.id.forgotPasword);
        resetPass.setOnClickListener(this);

        userEmail = findViewById(R.id.login_userEmail);
        userPassword = findViewById(R.id.userPassword);
        progressBar = findViewById(R.id.progressBar2);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.createAccount:
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
                break;
            case R.id.loginBtn:
                loginActvity();
                break;
            /*case R.id.forgotPasword:
                Intent newPassword = new Intent(MainActivity.this, ResetPassword.class);
                startActivity(newPassword);
                break;*/
        }
    }

    private void loginActvity() {

        String uEmail = userEmail.getText().toString().trim();
        String uPassword = userPassword.getText().toString().trim();

        //Validity check for input email
        if (uEmail.isEmpty()) {
            userEmail.setError("Field is required");
            userEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(uEmail).matches()) {
            userEmail.setError("Enter a valid email");
            userEmail.requestFocus();
            return;
        }

        //Valididty check for password
        if (uPassword.isEmpty()) {
            userPassword.setError("Invalid password. Please try again");
            userPassword.requestFocus();
            return;
        }

        if (uPassword.length() < 6) {
            userPassword.setError("Invalid password. Please try again");
            userPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.signInWithEmailAndPassword(uEmail, uPassword).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                //Redirect to journal dashboard
                startActivity(new Intent(MainActivity.this, JournalDashboard.class));
            } else {
                Toast.makeText(MainActivity.this, "Failed to login",
                        Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

    }




    }






}