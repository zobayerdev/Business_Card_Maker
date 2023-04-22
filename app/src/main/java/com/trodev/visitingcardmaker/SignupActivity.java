package com.trodev.visitingcardmaker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private ImageView signupIv;
    private TextView backTv;
    private EditText nameET, mobileET, emailET, passET;
    private ProgressBar progressBar;
    private RadioButton femaleRb, maleRb, radioButtonRegisterGenderSelected;
    private RadioGroup genderRG;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //  actionbar hide
        getSupportActionBar().hide();

        progressBar = findViewById(R.id.progressBar);

        // init imageview button
        signupIv = findViewById(R.id.signupIv);

        backTv = findViewById(R.id.backTv);

        /*init all edittext from signup ui*/
        nameET = findViewById(R.id.nameET);
        mobileET = findViewById(R.id.mobileET);
        emailET = findViewById(R.id.emailET);
        passET = findViewById(R.id.passET);

        /*Init radio button*/
        genderRG = findViewById(R.id.genderRG);
        femaleRb = findViewById(R.id.femaleRb);
        maleRb = findViewById(R.id.maleRb);
        genderRG.clearCheck();

        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backLoginPage();
            }
        });

        signupIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedGenderId = genderRG.getCheckedRadioButtonId();
                radioButtonRegisterGenderSelected = findViewById(selectedGenderId);

                String name = nameET.getText().toString().trim();
                String email = emailET.getText().toString().trim();
                String mobile = mobileET.getText().toString().trim();
                String password = passET.getText().toString().trim();

                String textGender;

                if (TextUtils.isEmpty(name)) {
                    nameET.setError("Check your name");
                    nameET.requestFocus();
                }
                if (TextUtils.isEmpty(mobile)) {
                    mobileET.setError("Check your mobile number");
                    mobileET.requestFocus();
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailET.setError("Check your email");
                    emailET.requestFocus();
                }
                if (TextUtils.isEmpty(password)) {
                    passET.setError("Check your password");
                    passET.requestFocus();
                }
                if (genderRG.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(SignupActivity.this, "Please select your gender", Toast.LENGTH_SHORT).show();
                    radioButtonRegisterGenderSelected.setError("Gender is required");
                    radioButtonRegisterGenderSelected.requestFocus();
                } else {
                    textGender = radioButtonRegisterGenderSelected.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(name, mobile, email, textGender, password);
                }

            }
        });

    }

    private void backLoginPage() {
        //open user profile after successful registration
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); //to close register activity
    }

    private void registerUser(String name, String mobile, String email, String textGender, String password) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(SignupActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();

                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    //Update Display Name of user
                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                    firebaseUser.updateProfile(profileChangeRequest);

                    //User data store into the Firebase Realtime Database
                    ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(mobile, email, password, textGender);

                    //Extracting user reference from Database for "Registered Users"
                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
                    /*when a user register here and save his data on database then firebase send his a verification link on his email */
                    referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                //send verification email
                               // firebaseUser.sendEmailVerification();

                                /*Toast for email verification*/
                                Toast.makeText(SignupActivity.this, "Please check your E-mail, Send verification link on your email.....", Toast.LENGTH_LONG).show();

                                //open user profile after successful registration
                                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish(); //to close register activity
                            } else {
                                Toast.makeText(SignupActivity.this, "Registration unsuccessful !!! Please try again", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }


                        }
                    });
                } else {
                    Toast.makeText(SignupActivity.this, "wait please....", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}