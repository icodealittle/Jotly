package edu.neu.madcourse.jotly;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends AppCompatActivity {
    Uri imageURI;
    private Button logout;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private String userID;
    private TextView changePic;
    private CircleImageView userProfPic;
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
    private FirebaseAuth firebaseAuth;
    ActivityResultLauncher<Intent> activityResultLauncher;
    private TextView cam_take;

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

        firebaseAuth = FirebaseAuth.getInstance();
        userProfPic = findViewById(R.id.imageBtn);
        changePic = findViewById(R.id.changeProfilePic);
        cam_take = findViewById(R.id.takePhoto);

        userProfPic.setOnClickListener(view -> getContent.launch("image/*"));

        changePic.setOnClickListener(view -> uploadImage());

        //Setting the profile pic for that specific user based on their registeration
        // user ID via Realtime Database
        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.getPhotoUrl() != null) {
            Glide.with(UserProfile.this).load(user.getPhotoUrl()).into(userProfPic);
        }

        //Camera
        activityResultLauncher = registerForActivityResult(new
                ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Bundle bundle = result.getData().getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                userProfPic.setImageBitmap(bitmap);
            }
        });

        cam_take.setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                activityResultLauncher.launch(intent);
            } else {
                Toast.makeText(UserProfile.this, "No app supporting this action",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadImage() {
        if (imageURI != null) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                    .child("Profile").child(userID + ".jpeg");

            storageReference.putFile(imageURI).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    getImageURL(storageReference);
                    Toast.makeText(UserProfile.this, "New Profile is set",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserProfile.this, "Something Wrong. " +
                            "Please try again", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void getImageURL(StorageReference storageReference) {
        storageReference.getDownloadUrl().addOnSuccessListener(uri -> setUserProfileUrl(uri));
    }

    private void setUserProfileUrl(Uri uri) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();

        user.updateProfile(request)
                .addOnSuccessListener(aVoid -> Toast.makeText(UserProfile.this,
                        "Updated succesfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(UserProfile.this,
                        "Profile image failed...", Toast.LENGTH_SHORT).show());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.userProfile) {
            Intent intent = new Intent(UserProfile.this, UserProfile.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.logouyBtn) {
            userSignOut();
            return true;
        } else if (id == R.id.homepageBtn) {
            Intent intent = new Intent(UserProfile.this, HomePageActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void userSignOut() {
        firebaseAuth.signOut();
        Intent intent = new Intent(UserProfile.this, MainActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(UserProfile.this, "Sign-out Successful",
                Toast.LENGTH_SHORT).show();
    }
}
