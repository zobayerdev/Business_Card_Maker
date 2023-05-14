package com.trodev.visitingcardmaker;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MakeVisitingCardActivity extends AppCompatActivity {


    private EditText nameET, companyET, designationET, phoneET, gmailET, officeET;
    private String name, company, designation, phone, gmail, office;
    private MaterialButton uploadBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_visiting_card);

        /*init buttons*/
        nameET = findViewById(R.id.nameET);
        companyET = findViewById(R.id.companyET);
        designationET = findViewById(R.id.designationET);
        phoneET = findViewById(R.id.phoneET);
        gmailET = findViewById(R.id.gmailET);
        officeET = findViewById(R.id.officeET);
        uploadBtn = findViewById(R.id.uploadBtn);

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

    }

    private void validateData() {

        name = nameET.getText().toString().trim();
        company = companyET.getText().toString().trim();
        designation = designationET.getText().toString().trim();
        phone = phoneET.getText().toString().trim();
        gmail = gmailET.getText().toString().trim();
        office = officeET.getText().toString().trim();

        if (name.isEmpty())
        {
            nameET.setError("Check Name");
            nameET.requestFocus();
        } else if (company.isEmpty()) {
            companyET.setError("Check Company");
            companyET.requestFocus();
        } else if (designation.isEmpty()) {
            designationET.setError("Check Designation");
            designationET.requestFocus();
        } else if (phone.isEmpty()) {
            phoneET.setError("Check Phone Number");
            phoneET.requestFocus();
        } else if (gmail.isEmpty()) {
            gmailET.setError("Check Gmail");
            gmailET.requestFocus();
        }
        else if (office.isEmpty())
        {
            officeET.setError("Check Office Location");
            officeET.requestFocus();
        }
        else
        {
            sendData();
        }

    }

    private void sendData() {

        Intent intent = new Intent(MakeVisitingCardActivity.this, VisitingCardActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("company", company);
        intent.putExtra("designation", designation);
        intent.putExtra("phone", phone);
        intent.putExtra("gmail", gmail);
        intent.putExtra("office", office);
        startActivity(intent);

    }

}