package edu.neu.madcourse.jotly;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class Register extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth fAuth;
    private EditText registerU, email, password, confirmPassword, uFullname;
    private ProgressBar progressBar;
    private TextView loginTV;
    private TextView registerUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        fAuth = FirebaseAuth.getInstance();
        registerUser = (Button) findViewById(R.id.createAccount);
        registerUser.setOnClickListener(this);
        registerU = (EditText) findViewById(R.id.userName);
        email = (EditText) findViewById(R.id.userEmail);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.passwordConfirm);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        uFullname = (EditText) findViewById(R.id.fullname);
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
        String userName = registerU.getText().toString().trim();
        String userEmail = email.getText().toString().trim();
        String userPass = password.getText().toString().trim();
        String userPass1 = confirmPassword.getText().toString().trim();
        String userFullname = uFullname.getText().toString().trim();

        if (userName.isEmpty()) {
            registerU.setError("Field is required");
            registerU.requestFocus();
            return;
        }

        if (userFullname.isEmpty()) {
            uFullname.setError("Field is required");
            uFullname.requestFocus();
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
        if (!userPass1.equals(userPass)) {
            confirmPassword.setError("Password is not match. Please try again!");
            confirmPassword.requestFocus();
            return;
        }

        //Validity password have to be at least 6 charaters per firebase requirment
        if ((userPass.length() < 6) || (userPass1.length() < 6)) {
            password.setError("Minimum password have to be at least 6 characters or more. " +
                    "Please try again!");
            password.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        fAuth.createUserWithEmailAndPassword(userEmail, userPass).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                User user = new User(userName, userEmail, userPass, userPass1, userFullname);
                FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth
                                .getInstance().getCurrentUser().getUid()).setValue(user)
                        .addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                Toast.makeText(Register.this,
                                        "User has successful registered",
                                        Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(Register.this, "Fail to registered",
                                        Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        });
            } else {
                Toast.makeText(Register.this, "Fail to registered",
                        Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });

    }
}