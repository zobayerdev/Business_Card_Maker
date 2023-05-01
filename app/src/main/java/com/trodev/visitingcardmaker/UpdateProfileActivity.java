package com.trodev.visitingcardmaker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateProfileActivity extends AppCompatActivity {

    private EditText nameET, phoneET;
    private RadioGroup radioGroupUpdateGender;
    private RadioButton radioButtonUpdateGenderSelected;
    private FirebaseAuth authProfile;
    private String name, mobile, gender;
    private ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        getSupportActionBar().setTitle("Update Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /*init views*/
        nameET = findViewById(R.id.nameET);
        phoneET = findViewById(R.id.phoneET);
        progressBar = findViewById(R.id.progressBar);
        radioGroupUpdateGender = findViewById(R.id.genderRG);

        /*firebase init*/
        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        //show profile data
        showProfile(firebaseUser);
    }

    private void showProfile(FirebaseUser firebaseUser) {
        String userIDofRegistered = firebaseUser.getUid();

        /*extract user data*/
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
        progressBar.setVisibility(View.VISIBLE);

        referenceProfile.child(userIDofRegistered).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if (readUserDetails != null) {
                    name = firebaseUser.getDisplayName();
                    mobile = readUserDetails.mobile;
                    gender = readUserDetails.gender;

                    /*set all data in edittext*/
                    nameET.setText(name);
                    phoneET.setText(mobile);

                    /*show gender*/
                    if (gender.equals("Male")) {
                        radioButtonUpdateGenderSelected = findViewById(R.id.maleRb);
                    } else {
                        radioButtonUpdateGenderSelected = findViewById(R.id.femaleRb);
                    }
                    /*checked true*/
                    radioButtonUpdateGenderSelected.setChecked(true);
                } else {
                    Toast.makeText(UpdateProfileActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(UpdateProfileActivity.this, "Something wrong with you!!!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}