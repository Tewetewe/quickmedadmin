package com.example.adminquickmed;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;
import java.util.Map;
import com.example.adminquickmed.data.DataProfile;

public class SignupChatActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore database;
    FirebaseUser user;
    EditText edtFirstName, edtLastName, edtUsername, edtEmail, edtpassword;
    Button btnSubmit;
    RadioGroup rdbGroup;
    ProgressDialog progressDialog;
    ActionBar actionBar;
    ImageView imgProfilePic;
    private Uri filePath = null;
    String userID;

    String firstName, lastName, username, email, gender, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_chat);
        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtpassword = findViewById(R.id.edtPassword);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        actionBar = getSupportActionBar();
        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance(); //Initialize cloud firebase authentication
        database = FirebaseFirestore.getInstance(); //Initialize cloud firestore
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSubmit:
                signupUser();
                break;
        }
    }

    private void signupUser() {
        username = edtUsername.getText().toString().trim();
        email = edtEmail.getText().toString().trim();
        password = edtpassword.getText().toString().trim();


        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this,"Enter username",Toast.LENGTH_LONG).show();
            return;}

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this,"Enter email",Toast.LENGTH_LONG).show();
            return;}

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this,"Enter password",Toast.LENGTH_LONG).show();
            return;}

        progressDialog.setMessage("Registering. Please Wait...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        /*
                        Get current user from firebase authentication, then set username as user display name
                         */
                        user = firebaseAuth.getCurrentUser();
                        userID = user.getUid(); //retrieve the user id so we can later use as key in the database
                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                            .setDisplayName(username)
                            .build();
                        user.updateProfile(profileUpdate);
                        addUserToDatabase();
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Registration successful.", Toast.LENGTH_SHORT).show();
                        grabImage();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Registration error. Check your details", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    public void addUserToDatabase(){
        HashMap<String, String> userData = new HashMap<>();
        userData.put("a3_username", username);
        userData.put("a4_email", email);
        userData.put("a6_imageUrl", "none");

        Map<String, Object> update = new HashMap<>();
        update.put(userID, userData);

        database.collection("users").document(userID).set(update); //update details to Firestore
    }

    public void grabImage(){
        /*
        Build a dialogView for user to set profile image
         */
        AlertDialog.Builder builder = new AlertDialog.Builder(SignupChatActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.profile_image, null);
        builder.setView(dialogView);

        final AlertDialog dialog= builder.create();
        dialog.setCancelable(false);
        dialog.show();

        Button btnClose = dialog.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                startActivity(new Intent(SignupChatActivity.this, KonsultasiActivity.class));
                finish();
            }
        });

        imgProfilePic = dialog.findViewById(R.id.imgProfilePic);
        imgProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Use android-image-cropper library to grab and crop image
                CropImage.activity()
                    .setFixAspectRatio(true)
                    .start(SignupChatActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                filePath = result.getUri();
                uploadImage();
                imgProfilePic.setImageURI(filePath);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void uploadImage(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        final StorageReference ref = FirebaseStorage.getInstance().getReference().child("profile_images").child(userID);
        ref.putFile(filePath)
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    taskSnapshot.getMetadata().getReference().getDownloadUrl()
                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String url = uri.toString();
                                Map<String, String> imageLocation = new HashMap<>();
                                imageLocation.put("a6_imageUrl", url);
                                database.collection("users").document(userID).set(imageLocation);
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Image saved", Toast.LENGTH_SHORT).show();
                                imgProfilePic.setImageURI(filePath);
                            }
                        });
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            })
            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                        .getTotalByteCount());
                    progressDialog.setMessage((int) progress + "%" + " completed" );
                }
            })
        ;
    }
}
