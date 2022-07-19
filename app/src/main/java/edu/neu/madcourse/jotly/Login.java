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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    EditText email;
    EditText password;
    Button login;
    TextView forgetPass;
    TextView createAcc;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser user;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Test Interface");

        progressBar = findViewById(R.id.spinnerBar);
        email = findViewById(R.id.email_id);
        password = findViewById(R.id.password_id);
        login = findViewById(R.id.login_btn);
        forgetPass = findViewById(R.id.forgetPass_btn);
        createAcc = findViewById(R.id.new_acct);
        user = FirebaseAuth.getInstance().getCurrentUser();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_email = email.getText().toString();
                String user_password = password.getText().toString();
                if (user_email.isEmpty() || user_password.isEmpty()) {
                    Toast.makeText(Login.this, "Fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                if (firebaseAuth.getCurrentUser().isAnonymous()) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();

                    firebaseFirestore.collection("notes").document(user.getUid()).delete().addOnSuccessListener(unused -> Toast.makeText(Login.this, "All Temp journals are Deleted", Toast.LENGTH_SHORT).show());

                    user.delete().addOnSuccessListener(aVoid -> Toast.makeText(Login.this, "All Temp users Deleted.", Toast.LENGTH_SHORT).show());
                }

                firebaseAuth.signInWithEmailAndPassword(user_email, user_password).addOnSuccessListener(authResult -> {
                    Toast.makeText(Login.this, "Success !", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, "Login Failed. " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });

            }
        });

        createAcc.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), Register.class)));
    }

}
