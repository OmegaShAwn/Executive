package com.example.android.executive;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class Managers_main_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, 0);
        //Get "hasLoggedIn" value. If the value doesn't exist yet false is returned
        boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);

        final String username;

        if (!hasLoggedIn) {
            Intent intent=new Intent(Managers_main_activity.this,MainActivity.class);
            startActivity(intent);
        }

        username=settings.getString("lusername","nil");

        Toast.makeText(Managers_main_activity.this,username,Toast.LENGTH_LONG).show();




        Button addUsers = (Button)findViewById(R.id.addusers);
        addUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddUsers = new Intent(Managers_main_activity.this,AddUsersActivity.class);
                startActivity(intentAddUsers);
            }
        });

        Button viewUser = (Button)findViewById(R.id.viewusers);
        viewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentViewUsers = new Intent(Managers_main_activity.this,ViewUsers.class);
                startActivity(intentViewUsers);

           }
        });
        Button emrgencies =(Button)findViewById(R.id.emergencies);
        emrgencies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEmer = new Intent(Managers_main_activity.this,EmergencyActivity.class);
                startActivity(intentEmer);
            }
        });
    }

    int k=0;

    @Override
    public void onBackPressed()
    {
        if(k==0) {
            Toast.makeText(getApplicationContext(), "Press Back again to log out", Toast.LENGTH_LONG).show();
            k++;
        }
        else {
            Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                k=0;
            }
        }, 2000);
        // code here to show dialog
//        super.onBackPressed();  // optional depending on your needs
    }
}
