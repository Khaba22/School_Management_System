package com.example.lmsshine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class LearnerActivities extends AppCompatActivity {

  private static final int PICK_IMAGE_REQUEST = 1;

  private Button mButtonChooseImage;
  private TextView mEditTextFileName, gradeET;
  private ImageView mImageView;
  private ProgressBar mProgressBar;
  private Uri filePath;
  String user_s_name;
  private FirebaseStorage storage;
  private StorageReference storageReference;
  private DatabaseReference reference;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_l_activities);

    mButtonChooseImage = findViewById(R.id.btnChoose);
    mEditTextFileName = findViewById(R.id.editText);
    gradeET = findViewById(R.id.editText1);
    mImageView = findViewById(R.id.imageView);
    mProgressBar = findViewById(R.id.progressBar);
    storage = FirebaseStorage.getInstance();
    storageReference = storage.getReference();

    mButtonChooseImage.setOnClickListener(
        view -> {
          openFileChooser();
        });

    mProgressBar.setVisibility(View.INVISIBLE);
  }

  private void openImagesActivity() {
    Intent intent = new Intent(this, ContentActivity.class);
    startActivity(intent);
  }

  public String getFileExtension(Uri uri) {
    ContentResolver cR = getContentResolver();
    MimeTypeMap mime = MimeTypeMap.getSingleton();
    return mime.getExtensionFromMimeType(cR.getType(uri));
  }

  private void openFileChooser() {
    Intent intent = new Intent();
    intent.setType("application/pdf");
    String[] mimeTypes = {"application/pdf"};
    intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
    intent.setAction(Intent.ACTION_GET_CONTENT);
    startActivityForResult(intent, PICK_IMAGE_REQUEST);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == PICK_IMAGE_REQUEST
        && resultCode == RESULT_OK
        && data != null
        && data.getData() != null) {
      filePath = data.getData();
      Glide.with(this).load(filePath).placeholder(R.drawable.pdf).fitCenter().into(mImageView);
    }
  }

  private Boolean validateName() {
    String val = mEditTextFileName.getText().toString().trim();

    if (val.isEmpty()) {
      mEditTextFileName.setError("Field can not be empty");
      return false;
    } else if (val.length() > 15) {
      mEditTextFileName.setError("Field Too Long");
      return false;
    } else {
      mEditTextFileName.setError(null);
      return true;
    }
  }

  private Boolean validateGrade() {
    String val = gradeET.getText().toString().toUpperCase().trim();
    String gradePattern = "[5-7]";

    if (val.isEmpty()) {
      gradeET.setError("Field can not be empty");
      return false;
    } else if (val.length() > 15) {
      gradeET.setError("Field Too Long");
      return false;
    } else if (!val.matches(gradePattern)) {
      gradeET.setError("Enter Only Grade Number \n i.e. 5, 6 or 7");
      return false;
    } else {
      gradeET.setError(null);
      return true;
    }
  }

  private Boolean checkFile() {
    if (filePath == null) {
      Toast.makeText(this, "No File Selected!", Toast.LENGTH_SHORT).show();
      return false;
    } else {
      return true;
    }
  }

  public void uploadOnClick(View view) {
    if (!checkFile() | !validateName() | !validateGrade()) {
    } else {

      // displaying progress dialog while image is uploading
      final ProgressDialog progressDialog = new ProgressDialog(LearnerActivities.this);
      progressDialog.setTitle("Submitting file...");
      progressDialog.show();

      Intent intent1 = getIntent();
      user_s_name = intent1.getStringExtra("sName");

      reference = FirebaseDatabase.getInstance().getReference(user_s_name + "/Submissions");

      // getting the storage reference
      final StorageReference sRef =
          storageReference.child(
              "Submissions/" + System.currentTimeMillis() + "." + getFileExtension(filePath));

      // adding the file to reference
      sRef.putFile(filePath)
          .addOnSuccessListener(
              new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                  // dismissing the progress dialog
                  progressDialog.dismiss();

                  // displaying success toast
                  Toast.makeText(
                          getApplicationContext(), "Submitted successful!", Toast.LENGTH_SHORT)
                      .show();

                  // creating the upload object to store uploaded image details
                  sRef.getDownloadUrl()
                      .addOnSuccessListener(
                          new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                              Intent intent1 = getIntent();
                              String user_s_name = intent1.getStringExtra("sName");

                              Upload upload =
                                  new Upload(
                                      mEditTextFileName.getText().toString().trim(),
                                      uri.toString(),
                                      user_s_name,
                                          gradeET.getText().toString().trim());
                              String uploadId = reference.push().getKey();
                              reference.child(uploadId).setValue(upload);
                              recreate();
                              mProgressBar.setVisibility(View.INVISIBLE);
                            }
                          });
                }
              })
          .addOnFailureListener(
              new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                  progressDialog.dismiss();
                  Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG)
                      .show();
                }
              })
          .addOnProgressListener(
              new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                  // displaying the upload progress
                  double progress =
                      (100.0 * taskSnapshot.getBytesTransferred())
                          / taskSnapshot.getTotalByteCount();
                  progressDialog.setMessage("Submitted " + ((int) progress) + "%...");
                }
              });
    } /*else {
        Toast.makeText(this, "No File Selected", Toast.LENGTH_SHORT).show();
      }*/
  }
}
