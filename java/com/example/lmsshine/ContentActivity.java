package com.example.lmsshine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ContentActivity extends AppCompatActivity {

  private RecyclerView mRecyclerView;
  private ContentAdapter adapter;

  private ProgressBar mProgressCircle;

  private FirebaseStorage storage;
  private StorageReference sRef;
  private DatabaseReference dRef;
  private ArrayList<Upload> uploads;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_content);

    mRecyclerView = findViewById(R.id.contentRecyclerView);
    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    uploads = new ArrayList<>();
    adapter = new ContentAdapter(mRecyclerView ,ContentActivity.this, uploads, new ArrayList<>());
    mRecyclerView.setAdapter(adapter);

    mProgressCircle = findViewById(R.id.progressCircle);

    // retrieving data from Firebase

    storage = FirebaseStorage.getInstance();
    sRef = storage.getReference();
    dRef = FirebaseDatabase.getInstance().getReference("Uploads");

    dRef.addValueEventListener(
        new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
              Upload upload = dataSnapshot.getValue(Upload.class);
              //upload.dataSnapshot.getKey();
              uploads.add(upload);
            }

            adapter.notifyDataSetChanged();
            /*adapter = new ContentAdapter(ContentActivity.this, uploads);
            mRecyclerView.setAdapter(adapter);*/
            mProgressCircle.setVisibility(View.INVISIBLE);
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(ContentActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            mProgressCircle.setVisibility(View.INVISIBLE);
          }
        });
  }
}
