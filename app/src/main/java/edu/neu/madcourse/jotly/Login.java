package edu.neu.madcourse.jotly;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText email;
    EditText password;
    Button login;
    TextView forgetPass;
    TextView createAcc;
    FirebaseAuth firebaseAuth;

}
