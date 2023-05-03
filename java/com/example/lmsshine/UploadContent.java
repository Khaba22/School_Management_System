package com.example.lmsshine;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class UploadContent extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button mButtonChooseImage;
    private TextView mTextViewShowUploads, mEditTextFileName;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private Uri filePath;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_content);

        mButtonChooseImage = findViewById(R.id.btnChoose);
        mTextViewShowUploads = findViewById(R.id.tvShowUploads);
        mEditTextFileName = findViewById(R.id.editText);
        mImageView = findViewById(R.id.imageView);
        mProgressBar = findViewById(R.id.progressBar);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        reference = FirebaseDatabase.getInstance().getReference("Uploads");

        mButtonChooseImage.setOnClickListener(view -> {
            openFileChooser();
        });

        mProgressBar.setVisibility(View.INVISIBLE);

        mTextViewShowUploads.setOnClickListener(view -> openImagesActivity());
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
        intent.setType("*/*");
        String[] mimeTypes = {"*/*"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            Glide.with(this).load(filePath).into(mImageView);
        }
    }

    public void uploadOnClick(View view) {
        if (filePath != null) {

            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(UploadContent.this);
            progressDialog.setTitle("Uploading product");
            progressDialog.show();

            //getting the storage reference
            final StorageReference sRef = storageReference.child("Uploads/"+System.currentTimeMillis() + "." + getFileExtension(filePath));

            //adding the file to reference
            sRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //dismissing the progress dialog
                            progressDialog.dismiss();

                            //displaying success toast
                            Toast.makeText(getApplicationContext(), "Upload successful!", Toast.LENGTH_SHORT).show();

                            //creating the upload object to store uploaded image details
                            sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    Upload upload = new Upload(mEditTextFileName.getText().toString().trim(), uri.toString());
                                    String uploadId = reference.push().getKey();
                                    reference.child(uploadId).setValue(upload);
                                    mProgressBar.setVisibility(View.INVISIBLE);

                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //displaying the upload progress
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });

        }
        else {
            Toast.makeText(this, "No File Selected", Toast.LENGTH_SHORT).show();
        }
    }
}