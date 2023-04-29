package com.trodev.visitingcardmaker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class DigitalVisitingCardActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextInputEditText nameET, companyET, designationET, phoneET, emailET, homeET,
            officeET, bioET, facebookET, instagramET, linkedinET, twitterET, githubET;

    private MaterialButton uploadBtn;

    private TextView nameTv;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_visiting_card);


        getSupportActionBar().setTitle("Digital Visiting Card");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        nameTv = findViewById(R.id.nameTv);

        // init all id from views
        imageView = findViewById(R.id.personIv);

        //init all textview
        nameET = findViewById(R.id.nameET);
        companyET = findViewById(R.id.companyET);
        designationET = findViewById(R.id.designationET);
        phoneET = findViewById(R.id.phoneET);
        emailET = findViewById(R.id.emailET);
        homeET = findViewById(R.id.homeET);
        officeET = findViewById(R.id.officeET);
        bioET = findViewById(R.id.bioET);
        facebookET = findViewById(R.id.facebookET);
        instagramET = findViewById(R.id.instagramET);
        linkedinET = findViewById(R.id.linkedinET);
        twitterET = findViewById(R.id.twitterET);
        githubET = findViewById(R.id.githubET);

        /*init button*/
        uploadBtn = findViewById(R.id.uploadBtn);

        /*load user details information*/
        loadDetails();

    }

    private void loadDetails() {
        // get all data from another activity
        String name = getIntent().getStringExtra("name");
        nameTv.setText(name);
    }
}