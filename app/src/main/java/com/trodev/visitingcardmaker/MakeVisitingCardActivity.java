package com.trodev.visitingcardmaker;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
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
    private LinearLayout infoLl;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_visiting_card);


        infoLl = findViewById(R.id.infoLl);
        saveBtn = findViewById(R.id.saveBtn);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = null;
                try {
                    bitmap = saveBitmapAsBusinessCard(infoLl);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                saveBitmapImage(bitmap);
            }
        });
    }

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
/*        FileOutputStream fos = new FileOutputStream(file);
        cardBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        fos.close();*/

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
    }
}