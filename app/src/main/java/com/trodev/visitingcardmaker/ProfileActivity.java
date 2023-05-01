package com.trodev.visitingcardmaker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    private TextView nameTv, phoneTv, emailTv, passwordTv, genderTv;
    private ProgressBar progressBar;
    private String name, phone, email, password, gender;
    private ImageView userIv;
    private FirebaseAuth authProfile;
    private ImageView copyBtn;
    private FirebaseUser firebaseUser;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setTitle("My Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /*init this text views*/
        nameTv = findViewById(R.id.nameTv);
        phoneTv = findViewById(R.id.phoneTv);
        emailTv = findViewById(R.id.emailTv);
        passwordTv = findViewById(R.id.passwordTv);
        genderTv = findViewById(R.id.genderTv);
        userIv = findViewById(R.id.userIv);
        progressBar = findViewById(R.id.progressBar);
        copyBtn = findViewById(R.id.copyBtn);

        /*auth firebase*/
        authProfile = FirebaseAuth.getInstance();
        firebaseUser = authProfile.getCurrentUser();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();


        if (firebaseUser == null) {
            Toast.makeText(this, "Not found user data", Toast.LENGTH_SHORT).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            showUserProfile(firebaseUser);
        }

        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Text Label", passwordTv.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(ProfileActivity.this, "Copied successful", Toast.LENGTH_SHORT).show();
            }
        });

        userIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, UploadImageActivity.class));
            }
        });

    }

    private void showUserProfile(FirebaseUser firebaseUser) {

        String userID = firebaseUser.getUid();

        /*Extract data*/
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if (readUserDetails != null) {
                    name = firebaseUser.getDisplayName();
                    phone = readUserDetails.mobile;
                    email = firebaseUser.getEmail();
                    gender = readUserDetails.gender;
                    password = readUserDetails.password;

                    nameTv.setText(name);
                    phoneTv.setText(phone);
                    emailTv.setText(email);
                    genderTv.setText(gender);
                    passwordTv.setText(password);

                    /*set the user profile picture*/
                    Uri uri = firebaseUser.getPhotoUrl();

                    /*set picasso*/
                    Picasso.get().load(uri).into(userIv);

                } else {
                    Toast.makeText(ProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ProfileActivity.this, "Something wrong with you! ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /*creating action menu*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*Inflate*/
        getMenuInflater().inflate(R.menu.common_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.menu_refresh) {
            /*refresh activity*/
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        }
        if (id == R.id.menu_update_profile) {
            startActivity(new Intent(ProfileActivity.this, UpdateProfileActivity.class));
            Toast.makeText(this, "Update your profile", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.menu_update_email) {
            Toast.makeText(this, "Update your email", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.menu_update_password) {
            Toast.makeText(this, "Update your password", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.menu_setting) {
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.menu_delete_profile) {
            Toast.makeText(this, "Delete your profile", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}