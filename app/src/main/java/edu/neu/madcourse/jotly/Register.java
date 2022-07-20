package edu.neu.madcourse.jotly;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class Register extends AppCompatActivity {
    EditText register_user;
    EditText register_email;
    EditText register_pass;
    EditText register_pass1;
    Button syncAccount;
    TextView loginAccount;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        getSupportActionBar().setTitle("Connect to Jotly");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        register_user = findViewById(R.id.userName);
        register_email = findViewById(R.id.userEmail);
        register_pass = findViewById(R.id.password);
        register_pass1 = findViewById(R.id.passwordConfirm);

        syncAccount = findViewById(R.id.new_acct);
        loginAccount = findViewById(R.id.login);
        progressBar = findViewById(R.id.progressBar);

        firebaseAuth = FirebaseAuth.getInstance();

        loginAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

        syncAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String uName = register_user.getText().toString();
                String uEmail = register_email.getText().toString();
                String uPass = register_pass.getText().toString();
                String uPass1 = register_pass1.getText().toString();

                if (uEmail.isEmpty() || uName.isEmpty() || uPass.isEmpty() || uPass1.isEmpty()) {
                    Toast.makeText(Register.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!uPass.equals(uPass1)) {
                    register_pass1.setError("Pass Do Not Match");
                }

                progressBar.setVisibility(View.VISIBLE);

                AuthCredential authCredential = EmailAuthProvider.getCredential(uEmail, uPass);
                firebaseAuth.getCurrentUser().linkWithCredential(authCredential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(Register.this, "blah blah", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(uName).build();
                        firebaseUser.updateProfile(userProfileChangeRequest);

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, "Failed to Connect. Try Again.", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
    }


}
