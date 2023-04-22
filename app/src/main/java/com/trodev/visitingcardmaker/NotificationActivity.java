package com.trodev.visitingcardmaker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        getSupportActionBar().setTitle("Notification");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }
}