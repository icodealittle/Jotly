package edu.neu.madcourse.jotly;

import android.content.Intent;
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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView createAcct;
    private TextView resetPass;
    private Button userLogin;
    private EditText userEmail, userPassword;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

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
            case R.id.forgotPasword:
                Intent newPassword = new Intent(MainActivity.this, ResetPassword.class);
                startActivity(newPassword);
                break;
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
                // Verify User email in firebase
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user.isEmailVerified()) {
                    //Redirect to journal dashboard
                    startActivity(new Intent(MainActivity.this,
                            HomePageActivity.class));
                } else {
                    user.sendEmailVerification();
                    Toast.makeText(MainActivity.this, "Please verify your email " +
                            "before signing in", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }

            } else {
                Toast.makeText(MainActivity.this, "Failed to login",
                        Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

    }

}