package com.trodev.visitingcardmaker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private ImageView loginIv;
    private TextView signupTv, forgetpassTv;
    private TextView emailET, passET;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //  actionbar hide
        getSupportActionBar().hide();

        loginIv = findViewById(R.id.nextIv);
        signupTv = findViewById(R.id.signupTv);
        forgetpassTv = findViewById(R.id.forgetpassTv);
        progressBar = findViewById(R.id.progressBar);

        /*Init all edittext from login ui*/
        emailET = findViewById(R.id.emailET);
        passET = findViewById(R.id.passET);

        /*authentication user*/
        auth = FirebaseAuth.getInstance();

        loginIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailET.getText().toString().trim();
                String password = passET.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(LoginActivity.this, "Check Email", Toast.LENGTH_SHORT).show();
                    emailET.requestFocus();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Check Password", Toast.LENGTH_SHORT).show();
                    passET.requestFocus();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    loginUser(email, password);
                }
            }
        });

        signupTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                Toast.makeText(LoginActivity.this, "Complete sign up process", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        emailET.setError("User does not exists or is no longer valid. Please register again");
                        emailET.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        passET.setError("Invalid credentials. Kindly re-check again");
                        passET.requestFocus();
                    } catch (Exception e) {
                        throw new RuntimeException(e);

                    }
                }
                progressBar.setVisibility(View.GONE);
            }
        });

    }
}