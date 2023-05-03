package com.example.lmsshine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfile extends AppCompatActivity {

    Button update, signOut;
    TextView fullName, schoolName, email, password;
    String string1, string2, string3, string4, string5;
    DatabaseReference reference;

    // Global variable to hold user data inside

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        reference = FirebaseDatabase.getInstance().
                getReference("Users/Admin");

        // Hooks
        fullName = findViewById(R.id.fullName);
        schoolName = findViewById(R.id.schoolName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        update = findViewById(R.id.updatePfl);
        signOut = findViewById(R.id.signOut);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfile.this,
                        MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Show all data
        showAllUserData();
    }

    private void showAllUserData() {

        Intent intent = getIntent();
        String user_username = intent.getStringExtra("username");
        String user_name = intent.getStringExtra("name");
        String user_s_name = intent.getStringExtra("sName");
        String user_email = intent.getStringExtra("email");
        String user_password = intent.getStringExtra("password");

        fullName.setText(user_name);
        schoolName.setText(user_s_name);
        email.setText(user_email);
        password.setText(user_password);
    }

    public void goToUpdateProfile ( View view ) {
        Intent intent = new Intent(UserProfile.this,
                UpdateAdminProfile.class);
        string5 = getIntent().getExtras().getString("username");
        intent.putExtra("username", string5);
        string1 = getIntent().getExtras().getString("name");
        intent.putExtra("name", string1);
        string2 = getIntent().getExtras().getString("sName");
        intent.putExtra("sName", string2);
        string3 = getIntent().getExtras().getString("email");
        intent.putExtra("email", string3);
        string4 = getIntent().getExtras().getString("password");
        intent.putExtra("password", string4);
        startActivity(intent);
        finish();
    }

}
