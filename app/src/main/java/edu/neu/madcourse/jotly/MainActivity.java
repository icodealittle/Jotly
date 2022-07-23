package edu.neu.madcourse.jotly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView createAcct;
    private TextView resetPass;
    private Button userLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        createAcct = (TextView) findViewById(R.id.createAccount);
        createAcct.setOnClickListener(this);
        userLogin = (Button) findViewById(R.id.loginBtn);
        userLogin.setOnClickListener(this);
        resetPass = (TextView) findViewById(R.id.forgotPasword);
        resetPass.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.createAccount:
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
                break;
            case R.id.loginBtn:
                Intent dashboard = new Intent(MainActivity.this, JournalDashboard.class);
                startActivity(dashboard);
                break;
            case R.id.forgotPasword:
                Intent newPassword = new Intent(MainActivity.this, ResetPassword.class);
                startActivity(newPassword);
                break;
        }
    }
}