package edu.neu.madcourse.jotly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView createAcct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        createAcct = (TextView) findViewById(R.id.createAccount);
        createAcct.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.createAccount:
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
                break;
        }
    }
}