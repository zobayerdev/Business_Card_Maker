package com.trodev.visitingcardmaker;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class VisitingCardActivity extends AppCompatActivity {

    private MaterialCardView infoLl;
    private Button saveBtn;
    private TextView nameTv, companyTv, designationTv, phoneTv, gmailTv, officeTv;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visiting_card);

        infoLl = findViewById(R.id.infoLl);
        saveBtn = findViewById(R.id.saveBtn);

        /*init all textviews*/
        nameTv = findViewById(R.id.nameTv);
        companyTv = findViewById(R.id.companyTv);
        designationTv = findViewById(R.id.designationTv);
        phoneTv = findViewById(R.id.phoneTv);
        gmailTv = findViewById(R.id.gmailTv);
        officeTv = findViewById(R.id.officeTv);

        loadInfo();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = null;
                bitmap = getBitmapFromUiView(infoLl);
                saveBitmapImage(bitmap);

                /*this code is represent to solid size card size*/
                // bitmap = saveBitmapAsBusinessCard(infoLl);
            }
        });
    }

    private void loadInfo() {

        String name = getIntent().getStringExtra("name");
        String company = getIntent().getStringExtra("company");
        String designation = getIntent().getStringExtra("designation");
        String phone = getIntent().getStringExtra("phone");
        String gmail = getIntent().getStringExtra("gmail");
        String office = getIntent().getStringExtra("office");


        nameTv.setText(name);
        companyTv.setText(company);
        designationTv.setText(designation);
        phoneTv.setText(phone);
        gmailTv.setText(gmail);
        officeTv.setText(office);
    }
/*
    private Bitmap getBitmapFromUiView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        } else {
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);

        //return the bitmap
        return returnedBitmap;
    }

    private Bitmap saveBitmapAsBusinessCard(View view) throws IOException {
        // Define the dimensions of the business card in pixels (assuming 300 DPI)
        int widthPx = (int) (3.5 * 300);
        int heightPx = (int) (2 * 300);

        // Create a scaled Bitmap with the same aspect ratio as the original view
        Bitmap originalBitmap = getBitmapFromUiView(view);
        float scaleX = (float) widthPx / originalBitmap.getWidth();
        float scaleY = (float) heightPx / originalBitmap.getHeight();
        float scale = Math.min(scaleX, scaleY);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap,
                (int) (originalBitmap.getWidth() * scale),
                (int) (originalBitmap.getHeight() * scale), true);

        // Create a new Bitmap with the exact business card dimensions (cropping if necessary)
        Bitmap cardBitmap = Bitmap.createBitmap(widthPx, heightPx, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(cardBitmap);
        canvas.drawColor(Color.WHITE);
        int left = (cardBitmap.getWidth() - scaledBitmap.getWidth()) / 2;
        int top = (cardBitmap.getHeight() - scaledBitmap.getHeight()) / 2;
        canvas.drawBitmap(scaledBitmap, left, top, null);

        // Save the Bitmap as a PNG file
*//*        FileOutputStream fos = new FileOutputStream(file);
        cardBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        fos.close();*//*

        return cardBitmap ;
    }

    /// @param folderName can be your app's name
    private void saveBitmapImage(Bitmap bitmap) {
        long timestamp = System.currentTimeMillis();

        //Tell the media scanner about the new file so that it is immediately available to the user.
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        values.put(MediaStore.Images.Media.DATE_ADDED, timestamp);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.DATE_TAKEN, timestamp);
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + getString(R.string.app_name));
            values.put(MediaStore.Images.Media.IS_PENDING, true);
            Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (uri != null) {
                try {
                    OutputStream outputStream = getContentResolver().openOutputStream(uri);
                    if (outputStream != null) {
                        try {
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                            outputStream.close();
                        } catch (Exception e) {
                            Log.e(TAG, "saveToGallery: ", e);
                        }
                    }
                    values.put(MediaStore.Images.Media.IS_PENDING, false);
                    getContentResolver().update(uri, values, null, null);

                    Toast.makeText(this, "Saved...", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e(TAG, "saveToGallery: ", e);
                }
            }
        } else {
            File imageFileFolder = new File(Environment.getExternalStorageDirectory().toString() + '/' + getString(R.string.app_name));
            if (!imageFileFolder.exists()) {
                imageFileFolder.mkdirs();
            }
            String mImageName = "" + timestamp + ".png";

            File imageFile = new File(imageFileFolder, mImageName);
            try {
                OutputStream outputStream = new FileOutputStream(imageFile);
                try {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    outputStream.close();
                } catch (Exception e) {
                    Log.e(TAG, "saveToGallery: ", e);
                }
                values.put(MediaStore.Images.Media.DATA, imageFile.getAbsolutePath());
                getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                Toast.makeText(this, "Saved...", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e(TAG, "saveToGallery: ", e);
            }
        }
    }*/


    private Bitmap getBitmapFromUiView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        } else {
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);

        //return the bitmap
        return returnedBitmap;
    }

    /// @param folderName can be your app's name
    private void saveBitmapImage(Bitmap bitmap) {
        long timestamp = System.currentTimeMillis();

        //Tell the media scanner about the new file so that it is immediately available to the user.
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        values.put(MediaStore.Images.Media.DATE_ADDED, timestamp);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.DATE_TAKEN, timestamp);
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + getString(R.string.app_name));
            values.put(MediaStore.Images.Media.IS_PENDING, true);
            Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (uri != null) {
                try {
                    OutputStream outputStream = getContentResolver().openOutputStream(uri);
                    if (outputStream != null) {
                        try {
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                            outputStream.close();
                        } catch (Exception e) {
                            Log.e(TAG, "saveToGallery: ", e);
                        }
                    }
                    values.put(MediaStore.Images.Media.IS_PENDING, false);
                    getContentResolver().update(uri, values, null, null);

                    Toast.makeText(this, "Saved...", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e(TAG, "saveToGallery: ", e);
                }
            }
        } else {
            File imageFileFolder = new File(Environment.getExternalStorageDirectory().toString() + '/' + getString(R.string.app_name));
            if (!imageFileFolder.exists()) {
                imageFileFolder.mkdirs();
            }
            String mImageName = "" + timestamp + ".png";

            File imageFile = new File(imageFileFolder, mImageName);
            try {
                OutputStream outputStream = new FileOutputStream(imageFile);
                try {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    outputStream.close();
                } catch (Exception e) {
                    Log.e(TAG, "saveToGallery: ", e);
                }
                values.put(MediaStore.Images.Media.DATA, imageFile.getAbsolutePath());
                getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                Toast.makeText(this, "Saved...", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e(TAG, "saveToGallery: ", e);
            }
        }
    }

}
