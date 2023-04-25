package com.trodev.visitingcardmaker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MakeDigitalVisitingCardActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private TextInputEditText nameET, companyET, designationET, phoneET, emailET, homeET,
                                              officeET, bioET, facebookET, instagramET, linkedinET, twitterET, githubET;

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
        emailET = findViewById(R.id.emailET);
        homeET = findViewById(R.id.homeET);
        officeET = findViewById(R.id.officeET);
        bioET = findViewById(R.id.bioET);
        facebookET = findViewById(R.id.facebookET);
        instagramET = findViewById(R.id.instagramET);
        linkedinET = findViewById(R.id.linkedinET);
        twitterET = findViewById(R.id.twitterET);
        githubET = findViewById(R.id.githubET);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageView.setImageBitmap(compressImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private Bitmap compressImage(Bitmap originalBitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        originalBitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
        byte[] byteArray = outputStream.toByteArray();
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }
}