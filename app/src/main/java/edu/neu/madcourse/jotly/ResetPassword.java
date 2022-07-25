package edu.neu.madcourse.jotly;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    private EditText resetUserEmail;
    private Button resetPasswordbtn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);

        resetUserEmail = (EditText) findViewById(R.id.resetEmail);
        resetPasswordbtn = (Button) findViewById(R.id.resetBtn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);

        firebaseAuth = FirebaseAuth.getInstance();

        resetPasswordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String reset_email = resetUserEmail.getText().toString().trim();

        if ((reset_email.isEmpty()) || (!Patterns.EMAIL_ADDRESS.matcher(reset_email).matches())) {
            resetUserEmail.setError("Required field. Invalid Email");
            resetUserEmail.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.sendPasswordResetEmail(reset_email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ResetPassword.this, "Please check your indox/spam mail to reset your email.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ResetPassword.this, "Your email is not in our system. Please try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
