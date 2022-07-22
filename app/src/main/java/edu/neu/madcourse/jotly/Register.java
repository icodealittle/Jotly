package edu.neu.madcourse.jotly;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class Register extends AppCompatActivity implements View.OnClickListener {
    //    EditText rUserName, rUserEmail, rUserPass, rUserConfPass;
//    Button syncAccount;
//    TextView loginAct;
//    ProgressBar progressBar;
    private FirebaseAuth fAuth;
    private EditText fullName, email, password, confirmPassword;
    private ProgressBar progressBar;
    private TextView loginTV;
    private TextView registerUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        fAuth = FirebaseAuth.getInstance();

//        loginTV = (TextView) findViewById(R.id.login);
        registerUser = (Button) findViewById(R.id.createAccount);
//        loginTV.setOnClickListener(this);
        registerUser.setOnClickListener(this);

        fullName = (EditText) findViewById(R.id.userName);
        email = (EditText) findViewById(R.id.userEmail);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.passwordConfirm);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.createAccount:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String userName = fullName.getText().toString().trim();
        String userEmail = email.getText().toString().trim();
        String userPass = password.getText().toString().trim();
        String userPass1 = confirmPassword.getText().toString().trim();

        if (userName.isEmpty()) {
            fullName.setError("Field is required!");
            fullName.requestFocus();
            return;
        }

        if (userEmail.isEmpty()) {
            email.setError("Field is required!");
            email.requestFocus();
            return;
        }

        //Validity email address
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            email.setError("Please provide a valid email address!");
            email.requestFocus();
            return;
        }

        if (userPass.isEmpty()) {
            password.setError("Field is required!");
            password.requestFocus();
            return;
        }

        if (userPass1.isEmpty()) {
            confirmPassword.setError("Field is required!");
            confirmPassword.requestFocus();
            return;
        }

        //Validity password matches
        if (!userPass.equals(userPass1)) {
            confirmPassword.setError("Password is not match. Please try again!");
            confirmPassword.requestFocus();
            return;
        }

        //Validity password have to be at least 6 charaters per firebase requirment
        if ((userPass.length() < 6) || (userPass1.length() < 6)) {
            password.setError("Minimum password have to be at least 6 characters or more. Please try again!");
            password.requestFocus();
            return;
        }


    }
}

//        getSupportActionBar().setTitle("Connect to Jotly");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        rUserName = findViewById(R.id.userName);
//        rUserEmail = findViewById(R.id.userEmail);
//        rUserPass = findViewById(R.id.password);
//        rUserConfPass = findViewById(R.id.passwordConfirm);
//
//        syncAccount = findViewById(R.id.createAccount);
//        loginAct = findViewById(R.id.login);
//        progressBar = findViewById(R.id.progressBar4);
//
//        fAuth = FirebaseAuth.getInstance();
//
//        loginAct.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Login.class)));
//
//        syncAccount.setOnClickListener(v -> {
//            final String uUsername = rUserName.getText().toString();
//            String uUserEmail = rUserEmail.getText().toString();
//            String uUserPass = rUserPass.getText().toString();
//            String uConfPass = rUserConfPass.getText().toString();
//
//            if (uUserEmail.isEmpty() || uUsername.isEmpty() || uUserPass.isEmpty() || uConfPass.isEmpty()) {
//                Toast.makeText(Register.this, "All Fields Are Required.", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            if (!uUserPass.equals(uConfPass)) {
//                rUserConfPass.setError("Password Do not Match.");
//            }
//
//            progressBar.setVisibility(View.VISIBLE);
//
//            AuthCredential credential = EmailAuthProvider.getCredential(uUserEmail, uUserPass);
//            fAuth.getCurrentUser().linkWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                @Override
//                public void onSuccess(AuthResult authResult) {
//                    Toast.makeText(Register.this, "Notes are Synced.", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
//
//                    FirebaseUser usr = fAuth.getCurrentUser();
//                    UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
//                            .setDisplayName(uUsername)
//                            .build();
//                    usr.updateProfile(request);
//
//                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                    finish();
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(Register.this, "Failed to Connect. Try Again.", Toast.LENGTH_SHORT).show();
//                    progressBar.setVisibility(View.VISIBLE);
//                }
//            });
//
//        });

//@Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        startActivity(new Intent(this, MainActivity.class));
//        finish();
//        return super.onOptionsItemSelected(item);
//    }

//    private void storeDatabase(String name, String email, String password){
//        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()){
//
//                }
//            }
//        });
//    }