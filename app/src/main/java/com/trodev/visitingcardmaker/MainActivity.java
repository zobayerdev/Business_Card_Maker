package com.trodev.visitingcardmaker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Dashboard");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigation_view);

        // #######################
        // Drawer Layout implement
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.start, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // #################################################################
        // eikhane eituku hocche amader navigation layout er kaj korar jonno.
        navigationView.setNavigationItemSelectedListener(this::onOptionsItemSelected);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.nav_profile:
                Toast.makeText(this, "My Profile", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                break;

            case R.id.nav_developer:
                Toast.makeText(this, "Developer", Toast.LENGTH_SHORT).show();
                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.developer_bottomsheet_layout);

                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                break;

            case R.id.nav_notification:
                startActivity(new Intent(MainActivity.this, NotificationActivity.class));
                Toast.makeText(this, "Notification", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_tube:
                Toast.makeText(this, "Trodev Tube", Toast.LENGTH_SHORT).show();
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/@trodev"));
                try {
                    startActivity(webIntent);
                } catch (ActivityNotFoundException ex) {
                    startActivity(webIntent);
                }
                break;

            case R.id.nav_policy:
                Toast.makeText(this, "Privacy Policy", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
                break;

            case R.id.nav_share:
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Business Visiting Card Maker");
                    String shareMessage = "\nBusiness Visiting Card Maker App Download now\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                    Toast.makeText(this, "Share Apps", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    //e.toString();
                }
                break;

            case R.id.nav_rate:
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                    Toast.makeText(this, "Rate us", Toast.LENGTH_SHORT).show();
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                }
                break;

            case R.id.nav_apps:
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=6580660399707616800")));
                    Toast.makeText(this, "Our Apps", Toast.LENGTH_SHORT).show();
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=6580660399707616800")));
                }
                break;

            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Log out successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finishAffinity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // In this code, android lifecycle exit on 2 times back-pressed.
    @Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finishAffinity();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }
}
