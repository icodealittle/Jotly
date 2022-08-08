package edu.neu.madcourse.jotly;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {
    private Button logout;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private String userID;

    public static final int CAM_PER = 1;
    public static final int CAM_PIC_CODE = 2;
    private ImageView userProfPic;
    private TextView changePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        userProfPic = (ImageView) findViewById(R.id.imageBtn);
        changePic = (TextView) findViewById(R.id.changeProfilePic);


        logout = (Button) findViewById(R.id.signout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(UserProfile.this, MainActivity.class));
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        userID = user.getUid();

        final TextView nameDisplayTV = (TextView) findViewById(R.id.fullnameTV_display);
        final TextView emailDisplayTV = (TextView) findViewById(R.id.emailTV_display);
        final TextView usernameDisplay = (TextView) findViewById(R.id.userName_display);

        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    String fullname = userProfile.userFullName;
                    String email = userProfile.email;
                    String username = userProfile.userName;

                    nameDisplayTV.setText(username);
                    emailDisplayTV.setText(email);
                    usernameDisplay.setText(fullname);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfile.this, "Something went wrong." +
                        " Please try again!", Toast.LENGTH_LONG).show();
            }
        });

        changePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    String[] permissions = {Manifest.permission.CAMERA};
                    requestPermissions(permissions, CAM_PER);
                } else {
                    Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(camIntent, CAM_PIC_CODE);
                }
            }
        });

    }


}
