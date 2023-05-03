package com.example.lmsshine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
//import android.view.View;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminProfile extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;

    private String userID;

    Button signOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        signOut = (Button) findViewById(R.id.signOut);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AdminProfile.this, AdminLogin.class));
                finish();
            }
        });


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users/Admin");
        userID = user.getUid();

        final TextView fullNameTextView = (TextView) findViewById(R.id.fullName);
        final TextView schoolNameTextView = (TextView) findViewById(R.id.schoolName);
        final TextView emailTextView = (TextView) findViewById(R.id.email);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User adminProfile = snapshot.getValue(User.class);

                if (adminProfile != null) {
                  String fullName = adminProfile.fullName;
                  String schoolName = adminProfile.schoolName;
                  String email = adminProfile.email;

                  fullNameTextView.setText(fullName);
                  schoolNameTextView.setText(schoolName);
                  emailTextView.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminProfile.this, "Something went wrong!!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}