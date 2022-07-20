/*
*  Firebase Storage Activity
*  Initializes, updates and maintains the database.
*  source: https://firebase.google.com/docs/storage/android/start?utm_campaign=Firebase_featureoverview_education_storage_en_10-31-16&utm_source=Firebase&utm_medium=yt-desc
* */
package edu.neu.madcourse.jotly;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Firebase extends AppCompatActivity {
    //Initialize Global Storage Instance
    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private StorageReference storageReference = firebaseStorage.getReference();
    private StorageReference journal;
    private StorageReference entry;
    //Setting newEntry variable
    String newJournalEntry;
    //Tag for logging
    private final String TAG = "Firebase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firebase);

        createReference();

    }

    private void createReference() {
        //Initialize child Reference for Journal
        journal = storageReference.child("journal");

        //Initialize child Reference for Journal Entry
        String journalEntry = "journal/" + newJournalEntry;
        entry = storageReference.child(journalEntry);
        //Reference Data
        //File path
        String path = entry.getPath();
        //File name
        String name = entry.getName();
        //Navigate to "journal" from "entry"
        //journal = entry.getParent();
    }

    public void uploadEntry(String newEntry) {
        newJournalEntry = newEntry;
        File file = new File(entry.getPath());

        try {
            //Change file to inputStream that will be uploaded to database
            InputStream inputStream = new FileInputStream(file);
            inputStream.close();
            //Upload files with metadata
            StorageMetadata metadata = new StorageMetadata();
            //Upload file inputStream
            UploadTask uploadTask = journal.putStream(inputStream, metadata);
            uploadTask.addOnFailureListener( new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "Could not upload journal entry");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d(TAG, String.valueOf(taskSnapshot.getMetadata()));
                }
            });

            //Monitor Upload Progress
            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    Log.d(TAG, "Upload is in progress");
                }
            }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onPaused(@NonNull UploadTask.TaskSnapshot snapshot) {
                    Log.d(TAG, "The upload has been paused.");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    String uploadFailed = "The Upload Failed because " + e.toString();
                    Log.d(TAG, uploadFailed);
                }
            });

        } catch (IOException e) {
            Log.d(TAG, e.toString());
        }
    }
}
