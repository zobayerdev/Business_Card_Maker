package com.trodev.visitingcardmaker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class DigitalVisitingCardActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView nameTv, companyTv, designationTv, phoneTv, emailTv, homeTv, officeTv, bioTv, facebookTv, instagramTv, linkedinTv, twitterTv, githubTv;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_visiting_card);

        getSupportActionBar().setTitle("Digital Visiting Card");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /*init all text views*/
        nameTv = findViewById(R.id.nameTv);
        companyTv = findViewById(R.id.companyTv);
        designationTv = findViewById(R.id.designationTv);
        phoneTv = findViewById(R.id.phoneTv);
        emailTv = findViewById(R.id.emailTv);
        homeTv = findViewById(R.id.homeTv);
        officeTv = findViewById(R.id.officeTv);
        bioTv = findViewById(R.id.bioTv);
        facebookTv = findViewById(R.id.facebookTv);
        instagramTv = findViewById(R.id.instagramTv);
        linkedinTv = findViewById(R.id.linkedinTv);
        twitterTv = findViewById(R.id.twitterTv);
        githubTv = findViewById(R.id.githubTv);

        // init all id from views
        imageView = findViewById(R.id.personIv);

        /*load user details information*/
        loadDetails();
    }

    private void loadDetails() {

        /*get all data from another activity*/
        String name = getIntent().getStringExtra("name");
        String company = getIntent().getStringExtra("company");
        String designation = getIntent().getStringExtra("designation");
        String phone = getIntent().getStringExtra("phone");
        String email = getIntent().getStringExtra("email");
        String home = getIntent().getStringExtra("home");
        String office = getIntent().getStringExtra("office");
        String bio = getIntent().getStringExtra("bio");
        String facebook = getIntent().getStringExtra("facebook");
        String instagram = getIntent().getStringExtra("instagram");
        String linkedin = getIntent().getStringExtra("linkedin");
        String twitter = getIntent().getStringExtra("twitter");
        String github = getIntent().getStringExtra("github");


        /*set all text in textview*/
        nameTv.setText("Name: "+name);
        companyTv.setText("Company: "+company);
        designationTv.setText("Designation: "+designation);
        phoneTv.setText("Phone: "+phone);
        emailTv.setText("E-mail: "+email);
        homeTv.setText("Home Address"+home);
        officeTv.setText("Office: "+office);
        bioTv.setText("Bio-data: "+bio);
        facebookTv.setText("Facebook: "+facebook);
        instagramTv.setText("Instagram: "+instagram);
        linkedinTv.setText("Linkedin: "+linkedin);
        twitterTv.setText("Twitter: "+twitter);

        if(github.isEmpty())
        {
            githubTv.setVisibility(View.INVISIBLE);
        }
        else {
            githubTv.setVisibility(View.VISIBLE);
            githubTv.setText("Github: "+github);
        }

    }

}