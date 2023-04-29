package com.trodev.visitingcardmaker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class DigitalVisitingCardActivity extends AppCompatActivity {

    public final static int QRCodeWidth = 500;
    private TextView nameTv, companyTv, designationTv, phoneTv, emailTv, homeTv, officeTv, bioTv, facebookTv, instagramTv, linkedinTv, twitterTv;
    private TextView githubTv, dribbleTv, pinterestTv, behanceTv, fiverrTv, upworkTv;
    private ImageView githubIv, dribbleIv, pinterestIv, behanceIv, fiverrIv, upworkIv;
    private ImageView githubTwoIv, dribbleTwoIv, pinterestTwoIv, behanceTwoIv, fiverrTwoIv, upworkTwoIv;

    /*design two*/
    private TextView nameTwoTv, companyTwoTv, designationTwoTv, bioTwoTv, phoneTwoTv, emailTwoTv, homeTwoTv, officeTwoTv,
            upworkTwoTv, fiverrTwoTv, behanceTwoTv, pinterestTwoTv, dribbleTwoTv, githubTwoTv, twitterTwoTv, linkedinTwoTv, instagramTwoTv, facebookTwoTv;

    /*same all user this*/
    private MaterialCardView firstMc, secondMc;
    Bitmap bitmap;
    private ImageButton downloadBtn, downloadBtnTwo;
    private ImageView imageView, barcodeIv, qrTwoIv;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_visiting_card);

        getSupportActionBar().setTitle("Digital Visiting Card");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /*##########################################################*/
        /*part one*/
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

        /*user requirements*/
        githubTv = findViewById(R.id.githubTv);
        dribbleTv = findViewById(R.id.dribbleTv);
        pinterestTv = findViewById(R.id.pinterestTv);
        behanceTv = findViewById(R.id.behanceTv);
        fiverrTv = findViewById(R.id.fiverrTv);
        upworkTv = findViewById(R.id.upworkTv);

        /*user requirements image*/
        githubIv = findViewById(R.id.githubIv);
        dribbleIv = findViewById(R.id.dribbleIv);
        pinterestIv = findViewById(R.id.pinterestIv);
        behanceIv = findViewById(R.id.behanceIv);
        fiverrIv = findViewById(R.id.fiverrIv);
        upworkIv = findViewById(R.id.upworkIv);

        // init all id from views
        imageView = findViewById(R.id.personIv);
        barcodeIv = findViewById(R.id.barcodeIv);

        /*Material card view*/
        firstMc = findViewById(R.id.firstMc);

        /*Button init*/
        downloadBtn = findViewById(R.id.downloadBtn);

        /*##########################################################*/
        /*part two*/
/*    private TextView nameTwoTv, companyTwoTv, designationTwoTv, bioTwoTv, phoneTwoTv, emailTwoTv, homeTwoTv, officeTwoTv,
                                   upworkTwoTv, fiverrTwoTv, behanceTwoTv, pinterestTwoTv, dribbleTwoTv, githubTwoTv, twitterTwoTv, linkedinTwoTv
                                   instagramTwoTv, facebookTwoTv;*/

        nameTwoTv = findViewById(R.id.nameTwoTv);
        companyTwoTv = findViewById(R.id.companyTwoTv);
        designationTwoTv = findViewById(R.id.designationTwoTv);
        bioTwoTv = findViewById(R.id.bioTwoTv);
        phoneTwoTv = findViewById(R.id.phoneTwoTv);
        emailTwoTv = findViewById(R.id.emailTwoTv);
        homeTwoTv = findViewById(R.id.homeTwoTv);
        officeTwoTv = findViewById(R.id.officeTwoTv);
        facebookTwoTv = findViewById(R.id.facebookTwoTv);
        instagramTwoTv = findViewById(R.id.instagramTwoTv);
        twitterTwoTv = findViewById(R.id.twitterTwoTv);
        linkedinTwoTv = findViewById(R.id.linkedinTwoTv);
        githubTwoTv = findViewById(R.id.githubTwoTv);
        dribbleTwoTv = findViewById(R.id.dribbleTwoTv);
        pinterestTwoTv = findViewById(R.id.pinterestTwoTv);
        behanceTwoTv = findViewById(R.id.behanceTwoTv);
        fiverrTwoTv = findViewById(R.id.fiverrTwoTv);
        upworkTwoTv = findViewById(R.id.upworkTwoTv);

        /*second view icon*/
        githubTwoIv = findViewById(R.id.githubTwoIv);
        dribbleTwoIv = findViewById(R.id.dribbleTwoIv);
        pinterestTwoIv = findViewById(R.id.pinterestTwoIv);
        behanceTwoIv = findViewById(R.id.behanceTwoIv);
        fiverrTwoIv = findViewById(R.id.fiverrTwoIv);
        upworkTwoIv = findViewById(R.id.upworkTwoIv);

        /*Image view init*/
        qrTwoIv = findViewById(R.id.qrTwoIv);

        /*Button*/
        downloadBtnTwo = findViewById(R.id.downloadBtnTwo);

        /*init second card view*/
        secondMc = findViewById(R.id.secondMc);

        /*load user details information*/
        loadDetails();

        /*generate qr code*/
        generateQR();
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

        /*user requirements*/
        String github = getIntent().getStringExtra("github");
        String dribble = getIntent().getStringExtra("dribble");
        String pinterest = getIntent().getStringExtra("pinterest");
        String behance = getIntent().getStringExtra("behance");
        String fiverr = getIntent().getStringExtra("fiverr");
        String upwork = getIntent().getStringExtra("upwork");


        /*set all text in textview*/
        nameTv.setText(name);
        companyTv.setText(company);
        designationTv.setText(designation);
        phoneTv.setText(phone);
        emailTv.setText(email);
        homeTv.setText(home);
        officeTv.setText(office);
        bioTv.setText(bio);
        facebookTv.setText(facebook);
        instagramTv.setText(instagram);
        linkedinTv.setText(linkedin);
        twitterTv.setText(twitter);

        /*Material card view init*/
        firstMc.setVisibility(View.GONE);


        /*when user give the input then it will show on this text views otherwise it hide of user*/
        if (github.isEmpty()) {
            githubTv.setVisibility(View.INVISIBLE);
            githubIv.setVisibility(View.INVISIBLE);
        } else {
            firstMc.setVisibility(View.VISIBLE);
            githubTv.setVisibility(View.VISIBLE);
            githubTv.setText(github);
        }
        if (dribble.isEmpty()) {
            dribbleIv.setVisibility(View.INVISIBLE);
            dribbleTv.setVisibility(View.INVISIBLE);
        } else {
            firstMc.setVisibility(View.VISIBLE);
            dribbleTv.setVisibility(View.VISIBLE);
            dribbleTv.setText(dribble);
        }
        if (pinterest.isEmpty()) {
            pinterestIv.setVisibility(View.INVISIBLE);
            pinterestTv.setVisibility(View.INVISIBLE);
        } else {
            firstMc.setVisibility(View.VISIBLE);
            pinterestTv.setVisibility(View.VISIBLE);
            pinterestTv.setText(pinterest);
        }
        if (behance.isEmpty()) {
            behanceIv.setVisibility(View.INVISIBLE);
            behanceTv.setVisibility(View.INVISIBLE);
        } else {
            firstMc.setVisibility(View.VISIBLE);
            behanceTv.setVisibility(View.VISIBLE);
            behanceTv.setText(behance);
        }
        if (fiverr.isEmpty()) {
            fiverrIv.setVisibility(View.INVISIBLE);
            fiverrTv.setVisibility(View.INVISIBLE);
        } else {
            firstMc.setVisibility(View.VISIBLE);
            fiverrTv.setVisibility(View.VISIBLE);
            fiverrTv.setText(fiverr);
        }
        if (upwork.isEmpty()) {
            upworkIv.setVisibility(View.INVISIBLE);
            upworkTv.setVisibility(View.INVISIBLE);
        } else {
            firstMc.setVisibility(View.VISIBLE);
            upworkTv.setVisibility(View.VISIBLE);
            upworkTv.setText(upwork);
        }

/*        ############################################
        Part two*/
        nameTwoTv.setText(name);
        companyTwoTv.setText(company);
        designationTwoTv.setText(designation);
        phoneTwoTv.setText(phone);
        emailTwoTv.setText(email);
        homeTwoTv.setText(home);
        officeTwoTv.setText(office);
        bioTwoTv.setText(bio);
        facebookTwoTv.setText(facebook);
        instagramTwoTv.setText(instagram);
        linkedinTwoTv.setText(linkedin);
        twitterTwoTv.setText(twitter);

        /*Material card view init*/
        secondMc.setVisibility(View.GONE);

        /*       when user give the input then it will show on this text views otherwise it hide of user*/
        if (github.isEmpty()) {
            githubTwoTv.setVisibility(View.INVISIBLE);
            githubTwoIv.setVisibility(View.INVISIBLE);
        } else {
            secondMc.setVisibility(View.VISIBLE);
            githubTwoIv.setVisibility(View.VISIBLE);
            githubTwoTv.setText(github);
        }
        if (dribble.isEmpty()) {
            dribbleTwoTv.setVisibility(View.INVISIBLE);
            dribbleTwoIv.setVisibility(View.INVISIBLE);
        } else {
            secondMc.setVisibility(View.VISIBLE);
            dribbleTwoIv.setVisibility(View.VISIBLE);
            dribbleTwoTv.setText(dribble);
        }
        if (pinterest.isEmpty()) {
            pinterestTwoTv.setVisibility(View.INVISIBLE);
            pinterestTwoIv.setVisibility(View.INVISIBLE);
        } else {
            secondMc.setVisibility(View.VISIBLE);
            pinterestTwoIv.setVisibility(View.VISIBLE);
            pinterestTwoTv.setText(pinterest);
        }
        if (behance.isEmpty()) {
            behanceTwoTv.setVisibility(View.INVISIBLE);
            behanceTwoIv.setVisibility(View.INVISIBLE);
        } else {
            secondMc.setVisibility(View.VISIBLE);
            behanceTwoIv.setVisibility(View.VISIBLE);
            behanceTwoTv.setText(behance);
        }
        if (fiverr.isEmpty()) {
            fiverrTwoTv.setVisibility(View.INVISIBLE);
            fiverrTwoIv.setVisibility(View.INVISIBLE);
        } else {
            secondMc.setVisibility(View.VISIBLE);
            fiverrTwoIv.setVisibility(View.VISIBLE);
            fiverrTwoTv.setText(fiverr);
        }
        if (upwork.isEmpty()) {
            upworkTwoTv.setVisibility(View.INVISIBLE);
            upworkTwoIv.setVisibility(View.INVISIBLE);
        } else {
            secondMc.setVisibility(View.VISIBLE);
            upworkTwoIv.setVisibility(View.VISIBLE);
            upworkTwoTv.setText(upwork);
        }
    }

    private void generateQR() {
        if (nameTv.getText().toString().length() + companyTv.getText().toString().length() + designationTv.getText().toString().length() == 0) {
            Toast.makeText(DigitalVisitingCardActivity.this, "Make sure your given Text..!", Toast.LENGTH_SHORT).show();
        } else {
            try {
                bitmap = textToImageEncode("Name :  " + nameTv.getText().toString().trim()
                        + "\nCompany :  " + companyTv.getText().toString().trim()
                        + "\nDesignation :  " + designationTv.getText().toString().trim()
                        + "\nPhone :  " + phoneTv.getText().toString().trim()
                        + "\nE-mail :  " + emailTv.getText().toString().trim()
                        + "\nHome Address :  " + homeTv.getText().toString().trim()
                        + "\nOffice or Institute Address :  " + officeTv.getText().toString().trim()
                        + "\nBio-data :  " + bioTv.getText().toString().trim()
                        + "\nFacebook : " + "https://www.facebook.com/" + facebookTv.getText().toString().trim()
                        + "\nInstagram :  " + "https://www.instagram.com/" + instagramTv.getText().toString().trim() + "/"
                        + "\nLinkedIn :  " + "https://www.linkedin.com/in/" + linkedinTv.getText().toString().trim() + "/"
                        + "\nTwitter :  " + "https://www.twitter.com/" + twitterTv.getText().toString().trim()
                        + "\nGithub :  " + "https://www.github.com/" + githubTv.getText().toString().trim()
                        + "\nDribble:  " + "https://www.dribble.com/" + dribbleTv.getText().toString().trim()
                        + "\nPinterest :  " + "https://www.pinterest.com/" + pinterestTv.getText().toString().trim()
                        + "\nBehance :  " + "https://www.behance.com/" + behanceTv.getText().toString().trim()
                        + "\nFiverr :  " + "https://www.fiverr.com/" + fiverrTv.getText().toString().trim()
                        + "\nUpwork :  " + "https://www.upwork.com/" + upworkTv.getText().toString().trim()
                );
                barcodeIv.setImageBitmap(bitmap);
                downloadBtn.setVisibility(View.VISIBLE);
                downloadBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Profile_Identity", null);
                        Toast.makeText(DigitalVisitingCardActivity.this, "Download Complete", Toast.LENGTH_SHORT).show();
                    }
                });

                /*second part button*/
                qrTwoIv.setImageBitmap(bitmap);
                downloadBtnTwo.setVisibility(View.VISIBLE);
                downloadBtnTwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Profile_Identity", null);
                        Toast.makeText(DigitalVisitingCardActivity.this, "Second card download complete", Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap textToImageEncode(String value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(value, BarcodeFormat.DATA_MATRIX.QR_CODE, QRCodeWidth, QRCodeWidth, null);

        } catch (IllegalArgumentException e) {
            return null;
        }

        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];
        for (int y = 0; y < bitMatrixHeight; y++) {
            int offSet = y * bitMatrixWidth;
            for (int x = 0; x < bitMatrixWidth; x++) {
                pixels[offSet + x] = bitMatrix.get(x, y) ? getResources().getColor(R.color.black) : getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

}