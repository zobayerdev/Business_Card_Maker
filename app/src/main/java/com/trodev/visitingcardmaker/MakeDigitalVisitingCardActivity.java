package com.trodev.visitingcardmaker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MakeDigitalVisitingCardActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private TextInputEditText nameET, companyET, designationET, phoneET, gmailET, homeET, officeET, bioET, facebookET, instagramET, linkedinET, twitterET, githubET;
    private MaterialButton uploadBtn;
    private String name, company, designation, phone, email, home, office, bio, facebook, instagram, linkedin, twitter, github;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_digital_visiting_card);

        getSupportActionBar().setTitle("Digital Visiting Card");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // init all id from views
        imageView = findViewById(R.id.personIv);

        //init all textview
        nameET = findViewById(R.id.nameET);
        companyET = findViewById(R.id.companyET);
        designationET = findViewById(R.id.designationET);
        phoneET = findViewById(R.id.phoneET);
        gmailET = findViewById(R.id.gmailET);
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

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });

    }

    /*Image picker from gallery*/
    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    /*Image set on Bitmap and send it Bitmap media*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {

                Bitmap selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageView.setImageBitmap(compressImage(selectedImage));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*compress image from any file*/
    private Bitmap compressImage(Bitmap originalBitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        originalBitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
        byte[] byteArray = outputStream.toByteArray();
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.digital_top_menubar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == R.id.uploadBtn) {
            sendData();
        }
        return super.onOptionsItemSelected(item);
    }


    /*private String name, company, designation, phone, email, home, office, bio, facebook, instagram, linkedin, twitter, github;*/
    private void sendData() {
        name = nameET.getText().toString().trim();
        company = companyET.getText().toString().trim();
        designation = designationET.getText().toString().trim();
        phone = phoneET.getText().toString().trim();
        email = gmailET.getText().toString().trim();
        home = homeET.getText().toString().trim();
        office = officeET.getText().toString().trim();
        bio = bioET.getText().toString().trim();
        facebook = facebookET.getText().toString().trim();
        instagram = instagramET.getText().toString().trim();
        linkedin = linkedinET.getText().toString().trim();
        twitter = twitterET.getText().toString().trim();
        github = githubET.getText().toString().trim();

        if (name.isEmpty()) {
            nameET.setError("Name is required");
            nameET.requestFocus();
        } else if (company.isEmpty()) {
            companyET.setError("Company is required");
            companyET.requestFocus();
        } else if (designation.isEmpty()) {
            designationET.setError("Designation is required");
            designationET.requestFocus();
        } else if (phone.isEmpty()) {
            phoneET.setError("Phone number is required");
            phoneET.requestFocus();
        } else if (email.isEmpty()) {
            gmailET.setError("Email is required");
            gmailET.requestFocus();
        } else if (home.isEmpty()) {
            homeET.setError("Home address is required");
            homeET.requestFocus();
        } else if (office.isEmpty()) {
            officeET.setError("Office or Institute address is required");
            officeET.requestFocus();
        } else if (bio.isEmpty()) {
            bioET.setError("Short Bio-data is required");
            bioET.requestFocus();
        } else if (facebook.isEmpty()) {
            facebookET.setError("Facebook username is required");
            facebookET.requestFocus();
        } else if (instagram.isEmpty()) {
            instagramET.setError("Instagram username is required");
            instagramET.requestFocus();
        } else if (linkedin.isEmpty()) {
            linkedinET.setError("LinkedIn username is required");
            linkedinET.requestFocus();
        } else if (twitter.isEmpty()) {
            twitterET.setError("Twitter username is required");
            twitterET.requestFocus();
        } else {

            Intent intent = new Intent(MakeDigitalVisitingCardActivity.this, DigitalVisitingCardActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("company", company);
            intent.putExtra("designation", designation);
            intent.putExtra("phone", phone);
            intent.putExtra("email", email);
            intent.putExtra("home", home);
            intent.putExtra("office", office);
            intent.putExtra("bio", bio);
            intent.putExtra("facebook", facebook);
            intent.putExtra("instagram", instagram);
            intent.putExtra("linkedin", linkedin);
            intent.putExtra("twitter", twitter);
            intent.putExtra("github", github);

            startActivity(intent);
            Toast.makeText(this, "Generate File", Toast.LENGTH_SHORT).show();
        }
    }
}