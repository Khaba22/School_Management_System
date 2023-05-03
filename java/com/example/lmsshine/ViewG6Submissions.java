package com.example.lmsshine;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewG6Submissions extends AppCompatActivity {

    ListView listView;
    DatabaseReference dRef;
    List<Upload> uploads;
    String user_s_name;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_g5_submissions);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait...");
        dialog.setCancelable(false);

        listView = (ListView) findViewById(R.id.ltView);
        uploads = new ArrayList<>();

        retrieveSubmissionsFiles();

        listView.setOnItemClickListener(
                (parent, view, position, id) -> {
                    Upload upload = uploads.get(position);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setType("application/pdf");
                    intent.setData(Uri.parse(upload.getImageUrl()));
                    startActivity(intent);
                });

        listView.setOnItemLongClickListener(
                (parent, view, position, id) -> {
                    final AlertDialog alertDialog;
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ViewG6Submissions.this);
                    alertDialogBuilder.setMessage("Are you sure you want to Delete this file??");
                    alertDialogBuilder
                            .setPositiveButton(
                                    "Yes",
                                    (arg0, arg1) ->
                                            dRef.orderByChild("name")
                                                    .equalTo((String) listView.getItemAtPosition(position))
                                                    .addListenerForSingleValueEvent(
                                                            new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    if (snapshot.hasChildren()) {
                                                                        DataSnapshot firstChild =
                                                                                snapshot.getChildren().iterator().next();
                                                                        firstChild.getRef().removeValue();
                                                                        recreate();
                                                                    }
                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {}
                                                            }))
                            .setNegativeButton("No", null);
                    alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    return true;
                });
    }

    private void retrieveSubmissionsFiles() {

        dialog.show();

        Intent intent1 = getIntent();
        user_s_name = intent1.getStringExtra("sName");

        dRef = FirebaseDatabase.getInstance().getReference(user_s_name + "Submissions");

        // Check if data from user's school exists in DB
        Query checkUser = dRef.orderByChild("grade").equalTo("6");

        checkUser.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()) {

                            for (DataSnapshot ds : snapshot.getChildren()) {

                                Upload upload = ds.getValue(Upload.class);
                                uploads.add(upload);
                            }

                            String[] subName = new String[uploads.size()];

                            for (int i = 0; i < subName.length; i++) {

                                subName[i] = uploads.get(i).getName();
                            }

                            ArrayAdapter<String> arrayAdapter =
                                    new ArrayAdapter<String>(
                                            getApplicationContext(), android.R.layout.simple_list_item_1, subName) {

                                        @NonNull
                                        @Override
                                        public View getView(
                                                int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                                            View view = super.getView(position, convertView, parent);
                                            TextView textView = (TextView) view.findViewById(android.R.id.text1);

                                            textView.setTextColor(Color.WHITE);
                                            textView.setTextSize(24);
                                            return view;
                                        }
                                    };

                            dialog.dismiss();
                            listView.setAdapter(arrayAdapter);

                        } else {

                            Toast.makeText(ViewG6Submissions.this, "NO SUBMISSIONS FOUND...", Toast.LENGTH_LONG)
                                    .show();
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
    }
}
