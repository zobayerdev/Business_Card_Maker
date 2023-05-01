package com.trodev.visitingcardmaker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePasswordActivity extends AppCompatActivity {

    private MaterialButton authBtn, validateBtn;
    private FirebaseUser firebaseUser;
    private FirebaseAuth authProfile;
    private EditText newPasswordET, oldPasswordET;
    private ProgressBar progressBar;
    private String userNewPassword, userOldPassword;
    private TextView greatingTV;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        getSupportActionBar().setTitle("Update E-mail");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /*init views*/
        newPasswordET = findViewById(R.id.newPasswordET);
        oldPasswordET = findViewById(R.id.oldPasswordET);
        progressBar = findViewById(R.id.progressBar);
        authBtn = findViewById(R.id.authBtn);
        validateBtn = findViewById(R.id.validateBtn);
        greatingTV = findViewById(R.id.greatingTV);

        /*invisible this progressbar*/
        progressBar.setVisibility(View.GONE);

        /*disable this button and edittext because if user not validate his identity*/
        validateBtn.setEnabled(false);
        newPasswordET.setEnabled(false);

        /*firebase*/
        authProfile = FirebaseAuth.getInstance();
        firebaseUser = authProfile.getCurrentUser();

        /*user validation*/
        if (firebaseUser.equals("")) {
            Toast.makeText(this, "Something wrong with you", Toast.LENGTH_SHORT).show();
        } else {
            reAuthenticate(firebaseUser);
        }

    }

    private void reAuthenticate(FirebaseUser firebaseUser) {
        authBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userOldPassword = oldPasswordET.getText().toString();
                if (TextUtils.isEmpty(userOldPassword)) {
                    oldPasswordET.setError("Current password is required");
                    oldPasswordET.requestFocus();
                } else {
                    progressBar.setVisibility(View.VISIBLE);

                    /*re authenticate*/
                    AuthCredential credential = EmailAuthProvider.getCredential(firebaseUser.getEmail(), userOldPassword);
                    firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            progressBar.setVisibility(View.GONE);

                            if (task.isSuccessful()) {
                                /*enable new password and button*/
                                validateBtn.setEnabled(true);
                                newPasswordET.setEnabled(true);
                                greatingTV.setVisibility(View.VISIBLE);

                                /*disable old password and button*/
                                authBtn.setEnabled(false);
                                oldPasswordET.setEnabled(false);

                                /*get user input from user*/
                                /*change color on change email button*/
                                validateBtn.setBackgroundTintList(ContextCompat.getColorStateList(UpdatePasswordActivity.this, R.color.green));

                                /*click to validate*/
                                validateBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        progressBar.setVisibility(View.VISIBLE);
                                        changePassword(firebaseUser);
                                    }
                                });
                            } else {
                                Toast.makeText(UpdatePasswordActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    private void changePassword(FirebaseUser firebaseUser) {
        String userNewPass = newPasswordET.getText().toString().trim();

        if (TextUtils.isEmpty(userNewPass)) {
            Toast.makeText(this, "New password is needed", Toast.LENGTH_SHORT).show();
            newPasswordET.setError("New password is required");
            newPasswordET.requestFocus();
        } else if (userNewPass.matches(userOldPassword)) {
            Toast.makeText(this, "Old password and New password is same", Toast.LENGTH_SHORT).show();
            newPasswordET.setError("New password is required");
            newPasswordET.requestFocus();
        } else {
            progressBar.setVisibility(View.VISIBLE);

            /*firebase password*/
            firebaseUser.updatePassword(userNewPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isComplete()) {
                        Intent intent = new Intent(UpdatePasswordActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        Toast.makeText(UpdatePasswordActivity.this, "Password has been changed", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }

                }
            });
        }
    }
}