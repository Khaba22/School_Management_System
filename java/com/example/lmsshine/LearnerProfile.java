package com.example.lmsshine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LearnerProfile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    Button signOut, updateProfile;
    TextView fullNameTextView,schoolNameTextView, emailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learner_profile);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        fullNameTextView = (TextView) findViewById(R.id.fullName);
        fullNameTextView.setText(user.getDisplayName());
        
        emailTextView = (TextView) findViewById(R.id.email);
        emailTextView.setText(user.getEmail());

        updateProfile = (Button) findViewById(R.id.updatePfl);
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LearnerProfile.this, UpdateProfile.class));
            }
        });

        signOut = (Button) findViewById(R.id.signOut);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(LearnerProfile.this, LearnerLogin.class));
                finish();
            }
        });
    }
}