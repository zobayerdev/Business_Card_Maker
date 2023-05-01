package com.trodev.visitingcardmaker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdateEmailActivity extends AppCompatActivity {

    private MaterialButton authBtn, validateBtn;
    private FirebaseUser firebaseUser;
    private FirebaseAuth authProfile;
    private EditText newEmailET, passwordET, oldEmailET;
    private ProgressBar progressBar;
    private String userOldEmail, userNewEmail, userPassword;
    private TextView greatingTV;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email);

        getSupportActionBar().setTitle("Update E-mail");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /*init views*/
        newEmailET = findViewById(R.id.newEmailET);
        passwordET = findViewById(R.id.passwordET);
        oldEmailET = findViewById(R.id.emailET);
        progressBar = findViewById(R.id.progressBar);
        authBtn = findViewById(R.id.authBtn);
        validateBtn = findViewById(R.id.validateBtn);
        greatingTV = findViewById(R.id.greatingTV);

        /*disable this button and edittext because if user not validate his identity*/
        validateBtn.setEnabled(false);
        newEmailET.setEnabled(false);


        /*firebase*/
        authProfile = FirebaseAuth.getInstance();
        firebaseUser = authProfile.getCurrentUser();

        /*set old email on email textview*/
        userOldEmail = firebaseUser.getEmail();
        oldEmailET.setText(userOldEmail);

        /*set progressbar visibility*/
        progressBar.setVisibility(View.GONE);

        /*if user is not null*/
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

                /*get data from user*/
                userPassword = passwordET.getText().toString().trim();

                /*verify user data*/
                if (TextUtils.isEmpty(userPassword)) {
                    Toast.makeText(UpdateEmailActivity.this, "Password is needed to verify user", Toast.LENGTH_SHORT).show();
                    passwordET.setError("Password needed must");
                    passwordET.requestFocus();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    AuthCredential credential = EmailAuthProvider.getCredential(userOldEmail, userPassword);


                    firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                Toast.makeText(UpdateEmailActivity.this, "User credential is verified", Toast.LENGTH_SHORT).show();

                                /*enabled this edittext and button and disable also authenticate button */
                                validateBtn.setEnabled(true);
                                newEmailET.setEnabled(true);
                                authBtn.setEnabled(false);
                                greatingTV.setVisibility(View.VISIBLE);

                                /*change color on change email button*/
                                validateBtn.setBackgroundTintList(ContextCompat.getColorStateList(UpdateEmailActivity.this, R.color.green));

                                validateBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        /*get new email on user*/
                                        userNewEmail = newEmailET.getText().toString().trim();

                                        /*verify email address*/
                                        if (TextUtils.isEmpty(userNewEmail)) {
                                            Toast.makeText(UpdateEmailActivity.this, "Email is required", Toast.LENGTH_SHORT).show();
                                            newEmailET.setError("New email is needed");
                                            newEmailET.requestFocus();

                                        } else if (!Patterns.EMAIL_ADDRESS.matcher(userNewEmail).matches()) {
                                            Toast.makeText(UpdateEmailActivity.this, "Valid e-mail is required", Toast.LENGTH_SHORT).show();
                                            newEmailET.setError("Valid e-mail needed");
                                            newEmailET.requestFocus();
                                        } else if (userOldEmail.matches(userNewEmail)) {
                                            Toast.makeText(UpdateEmailActivity.this, "Same e-mail are not verified", Toast.LENGTH_SHORT).show();
                                            newEmailET.setError("Entered new E-mail");
                                            newEmailET.requestFocus();
                                        } else {
                                            progressBar.setVisibility(View.VISIBLE);
                                            updateEmail(firebaseUser);
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(UpdateEmailActivity.this, "Something wrong with you", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }

            }
        });

    }

    private void updateEmail(FirebaseUser firebaseUser) {

        firebaseUser.updateEmail(userNewEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) {
                    Toast.makeText(UpdateEmailActivity.this, "E-mail has been verified", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateEmailActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }
}