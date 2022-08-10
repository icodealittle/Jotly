package edu.neu.madcourse.jotly;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends AppCompatActivity {
    public static final int CAM_PER = 1;
    public static final int CAM_PIC_CODE = 2;
    Uri imageURI;
    ValueEventListener valueEventListener;
    private Button logout;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private String userID;
    //Method in replace of deprecated for startactivityforresult
    ActivityResultLauncher<String> getContent = registerForActivityResult(new
            ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            if (result != null) {
                userProfPic.setImageURI(result);
                imageURI = result;
            }
        }
    });
    private TextView changePic;
    private CircleImageView userProfPic;
    private FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        userID = user.getUid();

        final TextView nameDisplayTV = findViewById(R.id.fullnameTV_display);
        final TextView emailDisplayTV = findViewById(R.id.emailTV_display);
        final TextView usernameDisplay = findViewById(R.id.userName_display);

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

        userProfPic = findViewById(R.id.imageBtn);
        changePic = findViewById(R.id.changeProfilePic);

        userProfPic.setOnClickListener(view -> getContent.launch("image/*"));

        changePic.setOnClickListener(view -> uploadImage());
    }

    private void uploadImage() {
        if (imageURI != null) {
            StorageReference storageReference = firebaseStorage.getReference().child("images/" + UUID.randomUUID().toString());

            storageReference.putFile(imageURI).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(UserProfile.this, "New Profile is set", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserProfile.this, "Something Wrong. Please try again", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
