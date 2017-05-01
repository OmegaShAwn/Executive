package com.example.android.executive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewUsers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_users);

        final Intent intentNewUser = new Intent(ViewUsers.this,ViewSelectedUsers.class);

        Button doctors= (Button)findViewById(R.id.Doctors);
        Button ambulanceDrivers = (Button)findViewById(R.id.AmbulanceDrivers);
        Button otherusers = (Button)findViewById(R.id.otheruser);

        doctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentNewUser.putExtra("user","doctor");
                startActivity(intentNewUser);
            }
        });

        ambulanceDrivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentNewUser.putExtra("user","ambulance");
                startActivity(intentNewUser);
            }
        });
        otherusers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentNewUser.putExtra("user","other");
                startActivity(intentNewUser);
            }
        });
    }
    }

