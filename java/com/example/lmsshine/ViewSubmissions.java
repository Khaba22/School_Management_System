package com.example.lmsshine;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewSubmissions extends AppCompatActivity {

    ListView listView;
    DatabaseReference dRef;
    List<Upload> uploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_submissions);

        listView = (ListView) findViewById(R.id.ltView);
        uploads = new ArrayList<>();

        retrieveSubmissionsFiles();

        listView.setOnItemClickListener (new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Upload upload = uploads.get(position);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setType("application/pdf");
                intent.setData(Uri.parse(upload.getImageUrl()));
                startActivity(intent);
            }
        });
    }

    private void retrieveSubmissionsFiles() {

        dRef = FirebaseDatabase.getInstance().getReference("Submissions");
    dRef.addValueEventListener(
        new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {

            for (DataSnapshot ds : snapshot.getChildren()) {

              Upload upload = ds.getValue(Upload.class);
              uploads.add(upload);
            }

            String[] subName = new String[uploads.size()];

            for (int i = 0; i < subName.length; i++) {

              subName[i] = uploads.get(i).getName();
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    getApplicationContext(), android.R.layout.simple_list_item_1, subName) {

                  @NonNull
                  @Override
                  public View getView( int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                    View view = super.getView(position, convertView, parent);
                    TextView textView = (TextView) view.findViewById(android.R.id.text1);

                    textView.setTextColor(Color.WHITE);
                    textView.setTextSize(24);
                    return view;
                  }
            };

            listView.setAdapter(arrayAdapter);
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}