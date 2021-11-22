package com.miniproject.projectc2s;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class helpline extends AppCompatActivity {
    private static final int REQUEST_CALL = 1;
    private TextView callText;
    private Button callBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpline);

        callText = findViewById(R.id.callTxt);
        callBtn= findViewById(R.id.callButton);

       callBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               CallButton();
           }
       });


        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.helpline);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),
                                NavigationActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.law:

                        startActivity(new Intent(getApplicationContext(),
                                laws.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.map:
                        startActivity(new Intent(getApplicationContext(),
                                maps.class));
                        overridePendingTransition(0,0);

                        return true;


                    case R.id.video:
                        startActivity(new Intent(getApplicationContext(),
                                video.class));
                        overridePendingTransition(0,0);

                        return true;


                    case R.id.helpline:

                        return true;

                }
                return false;
            }
        });


    }

    private void CallButton() {

        String number = callText.getText().toString();
        if (number.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(helpline.this,
                    Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(helpline.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            }else{
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult( int requestCode, @NonNull String[] permissions,
                                            @NonNull int[] grantResults ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                CallButton();

            } else {
                Toast.makeText(this, "permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
    }
