package com.trodev.visitingcardmaker.fragment;

import static android.app.Activity.RESULT_OK;

import static java.lang.String.valueOf;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import com.trodev.visitingcardmaker.R;


public class ScanFragment extends Fragment {
    //UI Views
    private MaterialButton inputImageBtn;
    private MaterialButton recognizeTextBtn;
    private ImageView imageIv;
    private TextInputEditText recognizedTextEt;
    //TAG
    private static final String TAG = "MAIN_TAG";
    //Uri of the image that we will take from Camera/Gallery
    private Uri image_uri = null;

    //to handle the result of Camera/Gallery intent
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;

    // image pick constant
    private static final int IMAGE_PICK_GALLERY_CODE = 400;
    private static final int IMAGE_PICK_CAMERA_CODE = 500;

    //arrays of permission required to pick image from Camera, Gallery
    private String[] cameraPermissions;
    private String[] storagePermissions;
    private ImageButton shareBtn, copyBtn;
    ProgressDialog progressDialog;
    private TextRecognizer textRecognizer;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_scan, container, false);

        //init UI Views
        inputImageBtn = view.findViewById(R.id.inputImageBtn);
        recognizeTextBtn = view.findViewById(R.id.recognizeTextBtn);
        imageIv = view.findViewById(R.id.imageIv);
        recognizedTextEt = view.findViewById(R.id.recognizedTextEt);
        shareBtn = view.findViewById(R.id.shareBtn);
        copyBtn = view.findViewById(R.id.generateBtn);

        // set visibility button and text views
        recognizedTextEt.setVisibility(View.INVISIBLE);
        shareBtn.setVisibility(View.INVISIBLE);
        copyBtn.setVisibility(View.INVISIBLE);

        //init arrays of permissions required for camera, gallery
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        //init textRecognizer
        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);


        inputImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickDialog();
            }
        });

        recognizeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recognizeTextFromImage();
            }
        });

        return view;
    }

    //Image picking dialog
    private void showImagePickDialog() {
        //options to display
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String[] options = {"Camera", "Gallery"};
        builder.setTitle("Choose options")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        if (which == 0) {
                            if (checkCameraPermission()) {
                                pickFromCamera();
                            } else {
                                requestCameraPermission();
                            }
                        } else {
                            if (checkStoragePermission()) {
                                pickFromGallery();
                            } else {
                                requestStoragePermission();
                            }

                        }
                    }
                })
                .show();
    }

    // #############################################################
    //pick image from camera
    private void pickFromCamera() {

        //using media store to pick high/original quality image
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "Temp_Image Title");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Temp_Image Description");

        image_uri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);
    }

    // #############################################################
    //pick image from gallery
    private void pickFromGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }


    // #############################################################
    //check storage permission
    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                (PackageManager.PERMISSION_GRANTED);

        return result;
    }


    // #############################################################
    //request storage permission
    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(getActivity(), storagePermissions, STORAGE_REQUEST_CODE);
    }


    // #############################################################
    //Check camera permission
    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CAMERA) ==
                (PackageManager.PERMISSION_GRANTED);

        boolean result1 = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                (PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }


    // #############################################################
    //request camera permission
    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(getActivity(), cameraPermissions, CAMERA_REQUEST_CODE);
    }


    // #############################################################
    //handle permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && storageAccepted) {
                        //both permission required
                        pickFromCamera();
                    } else {
                        // both or one of permissions denied
                        Toast.makeText(getActivity(), "Camera permission is needed....", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;

            case STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted) {
                        // permission granted
                        pickFromGallery();
                    } else {
                        //permission denied
                        Toast.makeText(getActivity(), "Storage permission is needed....", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    // #############################################################
    // Handle Image Pick Results
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                image_uri = data.getData();
                imageIv.setImageURI(image_uri);
            } else if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                imageIv.setImageURI(image_uri);
            } else {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void recognizeTextFromImage() {

        //show progress dialog.....
        progressDialog.setTitle("Recognizing  Text......");
        progressDialog.show();


        try {
            InputImage inputImage = InputImage.fromFilePath(requireContext(), image_uri);
            Task<Text> textTaskResult = textRecognizer.process(inputImage).addOnSuccessListener(new OnSuccessListener<Text>() {
                @Override
                public void onSuccess(Text text) {
                    //progress dialog dismiss
                    progressDialog.dismiss();

                    String recognizedText = text.getText();
                    recognizedTextEt.setVisibility(View.VISIBLE);
                    copyBtn.setVisibility(View.VISIBLE);
                    shareBtn.setVisibility(View.VISIBLE);
                    recognizedTextEt.setText(recognizedText);
                    copyBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(getActivity().CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("TextView", recognizedTextEt.getText().toString());
                            clipboard.setPrimaryClip(clip);
                            Toast.makeText(getActivity(), "Copy successful", Toast.LENGTH_SHORT).show();
                        }
                    });
                    shareBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String s = recognizedTextEt.getText().toString();
                            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                            sharingIntent.setType("text/plain");
                            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                            sharingIntent.putExtra(Intent.EXTRA_TEXT, s);
                            startActivity(Intent.createChooser(sharingIntent, "Share text via"));
                            Toast.makeText(getActivity(), "Share Scanning Text", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Failed to scanning " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            progressDialog.dismiss();
            Toast.makeText(getActivity(), "Failed to scanning " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}